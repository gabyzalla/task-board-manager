package com.taskboard.infrastructure.repositories;

import com.taskboard.domain.entities.Board;
import com.taskboard.domain.repositories.BoardRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySQLBoardRepository implements BoardRepository {
    private final Connection connection;

    public MySQLBoardRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Board save(Board board) {
        String sql = board.getId() == null 
            ? "INSERT INTO boards (name) VALUES (?)"
            : "UPDATE boards SET name = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, board.getName());
            
            if (board.getId() != null) {
                stmt.setLong(2, board.getId());
            }
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Falha ao salvar board");
            }
            
            if (board.getId() == null) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        board.setId(generatedKeys.getLong(1));
                    } else {
                        throw new SQLException("Falha ao obter ID gerado");
                    }
                }
            }
            
            return board;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar board", e);
        }
    }

    @Override
    public Optional<Board> findById(Long id) {
        String sql = "SELECT id, name FROM boards WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Board board = new Board();
                    board.setId(rs.getLong("id"));
                    board.setName(rs.getString("name"));
                    return Optional.of(board);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar board por ID", e);
        }
        
        return Optional.empty();
    }

    @Override
    public List<Board> findAll() {
        String sql = "SELECT id, name FROM boards ORDER BY name";
        List<Board> boards = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Board board = new Board();
                board.setId(rs.getLong("id"));
                board.setName(rs.getString("name"));
                boards.add(board);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todos os boards", e);
        }
        
        return boards;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM boards WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new IllegalArgumentException("Board não encontrado");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar board", e);
        }
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM boards WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar existência do board", e);
        }
        
        return false;
    }

    @Override
    public Optional<Board> findByName(String name) {
        String sql = "SELECT id, name FROM boards WHERE name = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Board board = new Board();
                    board.setId(rs.getLong("id"));
                    board.setName(rs.getString("name"));
                    return Optional.of(board);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar board por nome", e);
        }
        
        return Optional.empty();
    }
} 