package com.taskboard.infrastructure.database;

import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/taskboard";
    private static final String USER = "root";
    private static final String PASSWORD = "password";
    
    private static DatabaseManager instance;
    private Connection connection;

    private DatabaseManager() {
        initializeDatabase();
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar com o banco de dados", e);
        }
        return connection;
    }

    private void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            createTables(conn);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inicializar banco de dados", e);
        }
    }

    private void createTables(Connection conn) throws SQLException {
        createBoardsTable(conn);
        createColumnsTable(conn);
        createCardsTable(conn);
        createCardMovementsTable(conn);
    }

    private void createBoardsTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS boards (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(255) NOT NULL UNIQUE,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
        """;
        
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    private void createColumnsTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS columns (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                `order` INT NOT NULL,
                type VARCHAR(50) NOT NULL,
                board_id BIGINT NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (board_id) REFERENCES boards(id) ON DELETE CASCADE,
                UNIQUE KEY unique_board_order (board_id, `order`)
            )
        """;
        
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    private void createCardsTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS cards (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                title VARCHAR(255) NOT NULL,
                description TEXT,
                creation_date TIMESTAMP NOT NULL,
                blocked BOOLEAN DEFAULT FALSE,
                block_reason TEXT,
                block_date TIMESTAMP NULL,
                unblock_reason TEXT,
                unblock_date TIMESTAMP NULL,
                current_column_id BIGINT NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (current_column_id) REFERENCES columns(id) ON DELETE CASCADE
            )
        """;
        
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    private void createCardMovementsTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS card_movements (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                card_id BIGINT NOT NULL,
                from_column_id BIGINT NOT NULL,
                to_column_id BIGINT NOT NULL,
                movement_date TIMESTAMP NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (card_id) REFERENCES cards(id) ON DELETE CASCADE,
                FOREIGN KEY (from_column_id) REFERENCES columns(id) ON DELETE CASCADE,
                FOREIGN KEY (to_column_id) REFERENCES columns(id) ON DELETE CASCADE
            )
        """;
        
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao fechar conexão", e);
        }
    }
} 