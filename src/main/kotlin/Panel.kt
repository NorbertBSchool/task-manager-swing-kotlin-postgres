package org.example

import java.awt.BorderLayout
import java.awt.FlowLayout
import javax.swing.*
import javax.swing.table.DefaultTableModel
import java.util.Vector

class Panel(private val repository: TaskRepository) : JPanel() {

    var table: JTable
    var executeButton: JButton
    var addButton: JButton
    var deleteButton: JButton
    var statusLabel: JLabel
    var progressBar: JProgressBar

    private val tableModel = object : DefaultTableModel(Vector(listOf("ID", "Tytuł", "Opis", "Zrobione")), 0) {
        override fun isCellEditable(row: Int, column: Int): Boolean = false
    }

    init {
        table = JTable(tableModel)
        executeButton = JButton("Wczytaj Zadania")
        addButton = JButton("Dodaj Zadanie")
        deleteButton = JButton("Usuń Zadanie")
        statusLabel = JLabel("Status: Oczekuje...")
        progressBar = JProgressBar()
        progressBar.isIndeterminate = true
        progressBar.isVisible = false

        layout = BorderLayout()

        val bottomPanel = JPanel(FlowLayout())
        bottomPanel.add(executeButton)
        bottomPanel.add(addButton)
        bottomPanel.add(deleteButton)

        val topPanel = JPanel().apply {
            layout = BoxLayout(this, BoxLayout.Y_AXIS)
            add(progressBar)
            add(statusLabel)
        }

        add(topPanel, BorderLayout.NORTH)
        add(JScrollPane(table), BorderLayout.CENTER)
        add(bottomPanel, BorderLayout.SOUTH)

        executeButton.addActionListener {
            statusLabel.text = "Ładowanie danych... Proszę czekać."
            statusLabel.foreground = java.awt.Color.BLUE
            executeButton.isEnabled = false
            progressBar.isVisible = true
            val worker = LoadTasksWorker(repository, this)
            worker.execute()
        }

        addButton.addActionListener {
            val title = JOptionPane.showInputDialog(this, "Podaj tytuł zadania:")
            if (!title.isNullOrBlank()) {
                val description = JOptionPane.showInputDialog(this, "Podaj opis (opcjonalnie):")
                val task = Task(title = title, description = description)
                statusLabel.text = "Dodawanie zadania..."
                statusLabel.foreground = java.awt.Color.BLUE
                addButton.isEnabled = false
                progressBar.isVisible = true
                val worker = AddTaskWorker(task, repository, this)
                worker.execute()
            }
        }

        deleteButton.addActionListener {
            val selectedRow = table.selectedRow
            if (selectedRow >= 0) {
                val idValue = tableModel.getValueAt(selectedRow, 0).toString()
                val id = idValue.toIntOrNull()
                if (id == null) {
                    JOptionPane.showMessageDialog(this, "Nieprawidłowe ID zadania.")
                    return@addActionListener
                }
                val confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Czy na pewno chcesz usunąć zadanie o ID $id?",
                    "Potwierdzenie usunięcia",
                    JOptionPane.YES_NO_OPTION
                )
                if (confirm == JOptionPane.YES_OPTION) {
                    statusLabel.text = "Usuwanie zadania..."
                    statusLabel.foreground = java.awt.Color.BLUE
                    deleteButton.isEnabled = false
                    progressBar.isVisible = true
                    val worker = DeleteTaskWorker(id, repository, this)
                    worker.execute()
                }
            } else {
                JOptionPane.showMessageDialog(this, "Wybierz zadanie do usunięcia.")
            }
        }
    }

    fun updateTable(tasks: List<Task>) {
        tableModel.rowCount = 0
        tasks.forEach { task ->
            tableModel.addRow(arrayOf<Any>(task.id ?: 0, task.title, task.description ?: "", if (task.isDone) "Tak" else "Nie"))
        }
    }
}