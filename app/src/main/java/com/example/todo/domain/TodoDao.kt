package com.example.todo.domain

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Query("select * from todos")
    fun getAllData(): Flow<List<Todo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(todo: Todo)

    @Update
    suspend fun updateData(todo: Todo)

    @Delete
    suspend fun deleteData(todo: Todo)

}