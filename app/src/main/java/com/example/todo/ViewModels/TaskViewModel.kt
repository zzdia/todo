package com.example.todo.ViewModels

import android.app.Application
import androidx.lifecycle.*
import com.example.todo.Database.Task
import com.example.todo.Database.TaskRepository
import com.example.todo.Database.TodoDatabase
import kotlinx.coroutines.launch

class TaskViewModel(application: Application): AndroidViewModel(application) {
    private lateinit var repository: TaskRepository
    lateinit var taskId: String
    lateinit var description: String
    val allTasks: LiveData<List<Task>>
    private val selected = MutableLiveData<Task>()

    init {
        val taskDao = TodoDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        allTasks = repository.inboxTasks
    }

    fun insert(task: Task) {
        viewModelScope.launch { repository.insert(task) }
    }

    fun update(task: Task) {
        viewModelScope.launch { repository.update(task) }
    }

    fun select(task: Task?) {
        selected.value = task
    }

    fun getSelected(): MutableLiveData<Task> {
        return selected
    }
}