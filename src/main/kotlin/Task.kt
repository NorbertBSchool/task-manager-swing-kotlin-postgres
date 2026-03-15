package org.example

data class Task(
    val id: Int? = null,
    val title: String,
    val description: String? = null,
    val isDone: Boolean = false
)