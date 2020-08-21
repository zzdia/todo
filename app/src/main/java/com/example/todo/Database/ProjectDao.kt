package com.example.todo.Database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(project: Project)

    @Update
    fun update(project: Project)

    @Transaction
    @Query("SELECT * FROM Project")
    fun getProjectWithTasks(): LiveData<List<ProjectWithTasks>>

    @Transaction
    @Query("SELECT * FROM Project")
    fun getProjectWithSections(): LiveData<List<ProjectWithSections>>

    @Transaction
    @Query("SELECT * FROM Project")
    fun getAllProjects(): LiveData<List<Project>>
}