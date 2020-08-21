package com.example.todo.Database

import androidx.room.*


@Entity
data class Section(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var name: String,
    var projectId: Int,
    var index: Int
)


data class SectionWithTasks(
    @Embedded val project: Project,
    @Relation(
        parentColumn = "id",
        entityColumn = "projectId"
    )

    val tasks: List<Task>
)
