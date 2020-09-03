package com.example.todo.Database

import androidx.lifecycle.LiveData


class TaskRepository(private val taskDao: TaskDao) {
    val inboxTasks: LiveData<List<Task>> = taskDao.getInboxTasks()

    suspend fun insert(task: Task) {
        taskDao.insert(task)
    }

    suspend fun update(task: Task) {
        taskDao.update(task)
    }
}
