package com.example.todo.Database

import androidx.room.*


@Entity
data class Project(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var name: String,
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


data class ProjectWithSections(
    @Embedded val project: Project,
    @Relation(
        parentColumn = "id",
        entityColumn = "projectId"
    )

    val sections: List<Section>
)

