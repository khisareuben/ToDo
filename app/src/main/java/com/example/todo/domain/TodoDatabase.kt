package com.example.todo.domain

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase: RoomDatabase() {

    abstract fun getDao(): TodoDao



}