package com.example.todo.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarUI(
    selectionMode: Boolean,
    selectedCount: Int,
    totalCount: Int,
    onDeleteSelected: () -> Unit,
    onCancelSelection: () -> Unit,
    onSelectAll: () -> Unit
) {
    val theme = LocalTheme.current

    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AnimatedContent(targetState = selectionMode, label = "TopBarTitle") { mode ->
                    if (mode) {
                        Text(
                            text = "$selectedCount selected",
                            color = theme.buttonColor,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Text(
                            text = "ToDO",
                            color = theme.buttonColor,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        },
        actions = {
            if (selectionMode) {
                if (selectedCount < totalCount) {
                    IconButton(onClick = onSelectAll) {
                        Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Select All")
                    }
                }
                IconButton(onClick = onDeleteSelected) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete selected")
                }
                IconButton(onClick = onCancelSelection) {
                    Icon(Icons.Default.Close, contentDescription = "Cancel selection")
                }
            }
            // No actions shown when not in selection mode
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = theme.containerColor
        )
    )
}
