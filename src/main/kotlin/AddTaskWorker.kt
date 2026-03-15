package org.example

import kotlinx.coroutines.runBlocking
import javax.swing.SwingWorker

class AddTaskWorker(
    private val task: Task,
    private val repository: TaskRepository,
    private val panel: Panel
) : SwingWorker<Boolean, Void>() {

    override fun doInBackground(): Boolean {
        return runBlocking {
            repository.addTask(task)
        }
    }

    override fun done() {
        try {
            val success = get()
            if (success) {
                panel.statusLabel.text = "Zadanie dodane pomyślnie."
                val loadWorker = LoadTasksWorker(repository, panel)
                loadWorker.execute()
            } else {
                panel.statusLabel.text = "Nie udało się dodać zadania."
            }
        } catch (ex: Exception) {
            panel.statusLabel.text = "Błąd: ${ex.message}"
            ex.printStackTrace()
        } finally {
            panel.progressBar.isVisible = false
            panel.addButton.isEnabled = true
        }
    }
}