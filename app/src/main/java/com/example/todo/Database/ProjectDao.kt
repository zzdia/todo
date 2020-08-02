package com.example.todo.Database

import androidx.room.*

@Dao
interface ProjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(project: Project)

    @Update
    fun update(project: Project)

    @Transaction
    @Query("SELECT * FROM Project")
    fun getProjectWithTasks(): List<ProjectWithTasks>
}