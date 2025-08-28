package com.example.todo.presentation


import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType.Companion.Uri
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.todo.R
import com.example.todo.domain.Todo
import com.example.todo.navigation.Screens
import com.example.todo.viewModel.TodoViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


enum class SortOrder { ASCENDING, DESCENDING }


@Composable
fun MainScreen(
    viewModel: TodoViewModel = koinViewModel(),
    navController: NavHostController
) {

    val notes by viewModel.notesList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val theme = LocalTheme.current
    val iconM3 = painterResource(id = R.drawable.icons8_nothing_found_96)
    var noteToDelete by remember { mutableStateOf<Todo?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var isSearchActive by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }
    val filteredNotes = if (query.isBlank()) {
        notes
    } else {
        notes.filter {
            it.title.contains(query, ignoreCase = true) ||
                    it.body.contains(query, ignoreCase = true)
        }
    }
    var selectionMode by remember { mutableStateOf(false) }
    val selectedItems = remember { mutableStateListOf<Int>() }
    var sortOrder by remember { mutableStateOf(SortOrder.DESCENDING) }





    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarUI(
                selectionMode = selectionMode,
                selectedCount = selectedItems.size,
                totalCount = filteredNotes.size,
                onDeleteSelected = {
                    selectedItems.forEach { id ->
                        notes.find { it.id == id }?.let { viewModel.deleteData(it) }
                    }
                    selectedItems.clear()
                    selectionMode = false
                },
                onCancelSelection = {
                    selectedItems.clear()
                    selectionMode = false
                },
                onSelectAll = {
                    selectedItems.clear()
                    selectedItems.addAll(filteredNotes.map { it.id })
                }
            )



        },
        floatingActionButton = {
            if (!selectionMode) {
                FloatingActionButton(onClick = {
                    navController.navigate(Screens.AddScreen.route)
                },
                    containerColor = theme.buttonColor
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add-icon")
                }
            }

        }
    ) {paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(theme.containerColor)
            ) {

                PlayStoreSearchBar(
                    query = query,
                    onQueryChange = { query = it },
                    onActiveChange = { isSearchActive = it }
                )



                Spacer(modifier = Modifier.height(10.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {

                    when {
                        isLoading -> {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = 100.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(color = theme.buttonColor)
                                }
                            }
                        }
                        filteredNotes.isEmpty() -> {
                            item {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = 100.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        Icons.Default.Search,
                                        contentDescription = "Search-icon",
                                        tint = theme.buttonColor
                                    )
                                    Text(
                                        text = "No matching tasks found.",
                                        color = Color.Gray,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                        else -> {
                            items(filteredNotes) { item ->
                                CardM3(
                                    note = item,
                                    selectionMode = selectionMode,
                                    isSelected = selectedItems.contains(item.id),
                                    onTapClick = {
                                        navController.navigate("edit/${item.title}/${item.body}/${item.id}")
                                    },
                                    onLongClick = {
                                        selectionMode = true
                                        selectedItems.add(item.id)
                                    },
                                    onSelectToggle = {
                                        if (selectedItems.contains(item.id)) {
                                            selectedItems.remove(item.id)
                                            if (selectedItems.isEmpty()) selectionMode = false
                                        } else {
                                            selectedItems.add(item.id)
                                        }
                                    }
                                )
                            }
                        }
                    }


                }
            }



            if (showDialog && noteToDelete != null) {
                AlertDialog(
                    onDismissRequest = {
                        showDialog = false
                        noteToDelete = null
                    },
                    title = {Text("Delete Note")},
                    text = {Text("Are you sure you want to delete '${noteToDelete?.title}' ?")},
                    confirmButton = {
                        TextButton(onClick = {
                            noteToDelete?.let { viewModel.deleteData(it) }
                            showDialog = false
                            noteToDelete = null
                        }) {
                            Text("Yes")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showDialog = false
                            noteToDelete = null
                        }) {
                            Text("No")
                        }
                    }
                )
            }




    }



}



@Composable
fun CardM3(
    note: Todo,
    selectionMode: Boolean,
    isSelected: Boolean,
    onTapClick: () -> Unit,
    onLongClick: () -> Unit,
    onSelectToggle: () -> Unit
) {
    val theme = LocalTheme.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .combinedClickable(
                onClick = {
                    if (selectionMode) onSelectToggle() else onTapClick()
                },
                onLongClick = onLongClick
            ),
        colors = CardDefaults.cardColors(
            containerColor = theme.cardBackground.copy(0.5f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = note.title,
                    color = theme.titleColor,
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = note.body,
                    color = theme.bodyColor,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

            }

            if (selectionMode) {
                Checkbox(
                    checked = isSelected,
                    onCheckedChange = { onSelectToggle() }
                )
            }
        }
    }
}

fun formatDate(date: Date): String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)