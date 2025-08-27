package com.example.todo.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "todos")
data class Todo(
    val title: String,
    val body: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
