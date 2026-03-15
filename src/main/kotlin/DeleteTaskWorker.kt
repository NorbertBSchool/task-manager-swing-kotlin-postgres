package org.example

import kotlinx.coroutines.runBlocking
import javax.swing.SwingWorker

class DeleteTaskWorker(
    private val taskId: Int,
    private val repository: TaskRepository,
    private val panel: Panel
) : SwingWorker<Boolean, Void>() {

    override fun doInBackground(): Boolean {
        return runBlocking {
            repository.deleteTask(taskId)
        }
    }

    override fun done() {
        try {
            val success = get()
            if (success) {
                panel.statusLabel.text = "Zadanie usunięte pomyślnie."
                val loadWorker = LoadTasksWorker(repository, panel)
                loadWorker.execute()
            } else {
                panel.statusLabel.text = "Nie udało się usunąć zadania."
            }
        } catch (ex: Exception) {
            panel.statusLabel.text = "Błąd: ${ex.message}"
            ex.printStackTrace()
        } finally {
            panel.progressBar.isVisible = false
            panel.deleteButton.isEnabled = true
        }
    }
}