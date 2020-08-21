package com.example.todo.Database

import androidx.room.*
import java.util.*


@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var title: String,
    var description: String?,
    val created: Date,
    var due: Date?,
    var priority: Int, // integer, 1 (higher prio) to 4 (lower prio)
    var done: Boolean,
    var parentTaskId: Int?,
    var projectId: Int?,
    var sectionId: Int?,
    var index: Int
)

data class TaskWithTasks(
    @Embedded val task: Task,
    @Relation(
        parentColumn = "id",
        entityColumn = "parentTaskId"
    )

    val tasks: List<Task>
)