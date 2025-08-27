package com.example.todo.viewModel

import com.example.todo.domain.Todo
import com.example.todo.domain.TodoDao
import kotlinx.coroutines.flow.Flow

class TodoRepository(private val dao: TodoDao) {

    val getAllData: Flow<List<Todo>> = dao.getAllData()
    suspend fun insertData(todo: Todo) = dao.insertData(todo)
    suspend fun updateData(todo: Todo) = dao.updateData(todo)
    suspend fun deleteData(todo: Todo) = dao.deleteData(todo)

}