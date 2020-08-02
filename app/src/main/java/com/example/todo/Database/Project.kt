package com.example.todo.Database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity
data class Project(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var title: String,
    var index: Int
)

@Entity
data class Section(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var projectId: Int,
    var index: Int
)

data class ProjectWithTasks(
    @Embedded val project: Project,
    @Relation(
        parentColumn = "id",
        entityColumn = "projectId"
    )

    val tasks: List<Task>
)

