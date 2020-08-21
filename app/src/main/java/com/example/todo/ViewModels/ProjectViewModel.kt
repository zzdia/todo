package com.example.todo.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todo.Database.Project
import com.example.todo.Database.ProjectRepository
import com.example.todo.Database.TodoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProjectViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ProjectRepository

    val allProjects: LiveData<List<Project>>

    init {
        val projectDao = TodoDatabase.getDatabase(application).projectDao()
        repository = ProjectRepository(projectDao)
        allProjects = repository.allProjects
    }

    fun insert(project: Project) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(project)
    }
}
