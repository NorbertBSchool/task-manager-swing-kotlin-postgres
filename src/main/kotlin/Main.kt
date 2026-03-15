package org.example

import com.formdev.flatlaf.FlatDarkLaf
import javax.swing.JFrame
import javax.swing.SwingUtilities

fun main() {
    FlatDarkLaf.setup()
    
    SwingUtilities.invokeLater {
        val frame = JFrame("Task Manager")
        val repository = TaskRepository(Db.dataSource)
        val panel = Panel(repository)
        frame.add(panel)
        frame.setSize(600, 600)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setLocationRelativeTo(null)
        frame.isVisible = true
    }
}