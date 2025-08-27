package com.example.todo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.domain.Todo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository): ViewModel() {

    /*val notesList: StateFlow<List<Todo>> = repository
        .getAllData.map { it.sortedByDescending { todo -> todo.id } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())*/

    private val _notesList = MutableStateFlow<List<Todo>>(emptyList())
    val notesList: StateFlow<List<Todo>> = _notesList

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading


    init {
        viewModelScope.launch {
            repository.getAllData.collect { todos ->
                _notesList.value = todos.sortedByDescending { it.id }
                _isLoading.value = false
            }
        }
    }


    fun insertData(todo: Todo){
        viewModelScope.launch {
            repository.insertData(todo)
        }
    }

    fun updateData(todo: Todo) {
        viewModelScope.launch {
            repository.updateData(todo)
        }
    }

    fun deleteData(todo: Todo){
        viewModelScope.launch {
            repository.deleteData(todo)
        }
    }

}