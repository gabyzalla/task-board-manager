package com.taskboard.infrastructure.repositories;

import com.taskboard.domain.entities.Column;
import com.taskboard.domain.enums.ColumnType;
import com.taskboard.domain.repositories.ColumnRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLColumnRepository implements ColumnRepository {
    private final Connection connection;

    public MySQLColumnRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Column save(Column column) {
        String sql = column.getId() == null 
            ? "INSERT INTO columns (name, `order`, type, board_id) VALUES (?, ?, ?, ?)"
            : "UPDATE columns SET name = ?, `order` = ?, type = ?, board_id = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, column.getName());
            stmt.setInt(2, column.getOrder());
            stmt.setString(3, column.getType().name());
            stmt.setLong(4, column.getBoardId());
            
            if (column.getId() != null) {
                stmt.setLong(5, column.getId());
            }
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Falha ao salvar coluna");
            }
            
            if (column.getId() == null) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        column.setId(generatedKeys.getLong(1));
                    } else {
                        throw new SQLException("Falha ao obter ID gerado");
                    }
                }
            }
            
            return column;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar coluna", e);
        }
    }

    @Override
    public List<Column> findByBoardId(Long boardId) {
        String sql = "SELECT id, name, `order`, type, board_id FROM columns WHERE board_id = ? ORDER BY `order`";
        List<Column> columns = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, boardId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Column column = new Column();
                    column.setId(rs.getLong("id"));
                    column.setName(rs.getString("name"));
                    column.setOrder(rs.getInt("order"));
                    column.setType(ColumnType.valueOf(rs.getString("type")));
                    column.setBoardId(rs.getLong("board_id"));
                    columns.add(column);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar colunas por board ID", e);
        }
        
        return columns;
    }

    @Override
    public void deleteByBoardId(Long boardId) {
        String sql = "DELETE FROM columns WHERE board_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, boardId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar colunas por board ID", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM columns WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new IllegalArgumentException("Coluna não encontrada");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar coluna", e);
        }
    }
} 