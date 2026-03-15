package org.example
        import com.zaxxer.hikari.HikariConfig
        import com.zaxxer.hikari.HikariDataSource

class Db {
    companion object {
        val dataSource = HikariDataSource(HikariConfig().apply {
            jdbcUrl = "jdbc:postgresql://localhost:5432/mydatabase"
            username = "root"
            password = "postgres"
        })
    }
}