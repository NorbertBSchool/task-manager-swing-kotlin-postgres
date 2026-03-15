package org.example

import kotlinx.coroutines.runBlocking
import java.awt.Color
import javax.swing.SwingWorker

class LoadTasksWorker(
    private val repository: TaskRepository,
    private val panel: Panel
) : SwingWorker<List<Task>, Void>() {

    override fun doInBackground(): List<Task> {
        Thread.sleep(4000)
        
        return runBlocking {
            repository.getAllTasks()
        }
    }

    override fun done() {
        try {
            val tasks: List<Task> = get()
            panel.updateTable(tasks)
            panel.statusLabel.text = "Gotowe. Wczytano ${tasks.size} zadania"
            panel.statusLabel.foreground = Color.GREEN
        } catch (ex: Exception) {
            panel.statusLabel.text = "Błąd: ${ex.message}"
            panel.statusLabel.foreground = Color.RED
            ex.printStackTrace()
        } finally {
            panel.progressBar.isVisible = false
            panel.executeButton.isEnabled = true
        }
    }
}