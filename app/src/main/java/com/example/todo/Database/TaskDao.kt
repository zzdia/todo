package com.example.todo.Database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Query("SELECT * FROM task WHERE projectId IS NULL")
    fun getInboxTasks(): LiveData<List<Task>>
}