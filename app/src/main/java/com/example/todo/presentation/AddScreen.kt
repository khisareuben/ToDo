package com.example.todo.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.todo.domain.Todo
import com.example.todo.navigation.Screens
import com.example.todo.viewModel.TodoViewModel
import org.koin.androidx.compose.koinViewModel
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    viewModel: TodoViewModel = koinViewModel(),
    navController: NavHostController,
) {

    val theme = LocalTheme.current
    var showDialog by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var noteToDelete by remember { mutableStateOf<Todo?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "Add Note",
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                modifier = Modifier,

                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },

                actions = {

                    TextButton(onClick = {
                        val todo = Todo(title = title, body = body)
                        viewModel.insertData(todo)
                        navController.navigate(Screens.HomeScreen.route)

                    }) {
                        Text(
                            text = "Save",
                            color = theme.buttonColor,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = theme.containerColor
                ),

                )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier.fillMaxSize().background(theme.containerColor).padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                value = title,
                onValueChange = {title = it},
                label = { Text("Title", color = Color.Gray) },
                maxLines = 1,
                colors = TextFieldDefaults.colors(
                    cursorColor = theme.buttonColor,
                    focusedContainerColor = theme.containerColor,
                    unfocusedContainerColor = theme.containerColor,
                    focusedIndicatorColor = theme.buttonColor

                )

            )
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(55.dp)
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(4.dp)
                ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "${body.length} characters")
            }
            OutlinedTextField(
                modifier = Modifier.fillMaxHeight().fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                value = body,
                onValueChange = {body = it},
                placeholder = {Text("Start typing", color = Color.Gray)},
                colors = TextFieldDefaults.colors(
                    cursorColor = theme.buttonColor,
                    focusedContainerColor = theme.containerColor,
                    unfocusedContainerColor = theme.containerColor,
                    focusedIndicatorColor = theme.buttonColor

                    )

            )
        }
    }



}