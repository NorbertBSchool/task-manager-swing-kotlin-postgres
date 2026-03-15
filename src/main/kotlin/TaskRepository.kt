package org.example

import com.zaxxer.hikari.HikariDataSource
import net.samyn.kapper.query
import net.samyn.kapper.execute

class TaskRepository(private val dataSource: HikariDataSource) {

    suspend fun getAllTasks(): List<Task> {
        return dataSource.connection.use { connection ->
            connection.query<Task>("SELECT id, title, description, is_done AS \"isDone\" FROM tasks")
        }
    }

    suspend fun addTask(task: Task): Boolean {
        return dataSource.connection.use { connection ->
            val rows = connection.execute(
                "INSERT INTO tasks (title, description, is_done) VALUES (:title, :description, :isDone)",
                "title" to task.title,
                "description" to task.description,
                "isDone" to task.isDone
            )
            rows > 0
        }
    }

    suspend fun deleteTask(id: Int): Boolean {
        return dataSource.connection.use { connection ->
            val rows = connection.execute(
                "DELETE FROM tasks WHERE id = :id",
                "id" to id
            )
            rows > 0
        }
    }
}