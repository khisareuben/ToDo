package com.example.todo.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

@Composable
fun PlayStoreSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onActiveChange: (Boolean) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    // Update isActive based on actual focus state
    LaunchedEffect(isFocused) {
        onActiveChange(isFocused)
    }

    val focusManager = LocalFocusManager.current

    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 16.dp)
            .focusRequester(focusRequester),
        placeholder = { Text(
            text = "Search...",
            fontSize = (10.sp),
            //modifier = Modifier.padding(bottom = 0.dp)
        )
          },
        leadingIcon = {
            if (isFocused) {
                IconButton(onClick = {
                    onQueryChange("")
                    onActiveChange(false)
                    focusManager.clearFocus()
                }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back-icon")
                }
            } else {
                Icon(Icons.Default.Search, contentDescription = "Search-icon")
            }
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(Icons.Default.Close, contentDescription = "Close-icon")
                }
            }
        },
        textStyle = TextStyle(fontSize = 14.sp),
        singleLine = true,
        interactionSource = interactionSource,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.LightGray.copy(0.3f),
            focusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.LightGray
        ),
        shape = RoundedCornerShape(24.dp)
    )
}

@Composable
fun SectionBelowSearchBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Text("Pinned Section", fontSize = 16.sp)
    }
}


