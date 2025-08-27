package com.example.todo.di

import androidx.room.Room
import com.example.todo.domain.TodoDatabase
import com.example.todo.viewModel.TodoRepository
import com.example.todo.viewModel.TodoViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        Room.databaseBuilder(
            get(),
            TodoDatabase::class.java,
            "todoDatabase.db"
        ).build()
    }
    single { get<TodoDatabase>().getDao() }
    single { TodoRepository(get()) }
    viewModel { TodoViewModel(get()) }

}