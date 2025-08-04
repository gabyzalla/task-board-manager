package com.taskboard.infrastructure.repositories;

import com.taskboard.domain.entities.Card;
import com.taskboard.domain.repositories.CardRepository;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySQLCardRepository implements CardRepository {
    private final Connection connection;

    public MySQLCardRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Card save(Card card) {
        String sql = card.getId() == null 
            ? "INSERT INTO cards (title, description, creation_date, blocked, block_reason, block_date, unblock_reason, unblock_date, current_column_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
            : "UPDATE cards SET title = ?, description = ?, creation_date = ?, blocked = ?, block_reason = ?, block_date = ?, unblock_reason = ?, unblock_date = ?, current_column_id = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, card.getTitle());
            stmt.setString(2, card.getDescription());
            stmt.setTimestamp(3, Timestamp.valueOf(card.getCreationDate()));
            stmt.setBoolean(4, card.isBlocked());
            stmt.setString(5, card.getBlockReason());
            stmt.setTimestamp(6, card.getBlockDate() != null ? Timestamp.valueOf(card.getBlockDate()) : null);
            stmt.setString(7, card.getUnblockReason());
            stmt.setTimestamp(8, card.getUnblockDate() != null ? Timestamp.valueOf(card.getUnblockDate()) : null);
            stmt.setLong(9, card.getCurrentColumnId());
            
            if (card.getId() != null) {
                stmt.setLong(10, card.getId());
            }
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Falha ao salvar card");
            }
            
            if (card.getId() == null) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        card.setId(generatedKeys.getLong(1));
                    } else {
                        throw new SQLException("Falha ao obter ID gerado");
                    }
                }
            }
            
            return card;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar card", e);
        }
    }

    @Override
    public Optional<Card> findById(Long id) {
        String sql = "SELECT id, title, description, creation_date, blocked, block_reason, block_date, unblock_reason, unblock_date, current_column_id FROM cards WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Card card = mapResultSetToCard(rs);
                    return Optional.of(card);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar card por ID", e);
        }
        
        return Optional.empty();
    }

    @Override
    public List<Card> findByColumnId(Long columnId) {
        String sql = "SELECT id, title, description, creation_date, blocked, block_reason, block_date, unblock_reason, unblock_date, current_column_id FROM cards WHERE current_column_id = ? ORDER BY creation_date";
        List<Card> cards = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, columnId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Card card = mapResultSetToCard(rs);
                    cards.add(card);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar cards por column ID", e);
        }
        
        return cards;
    }

    @Override
    public List<Card> findByBoardId(Long boardId) {
        String sql = "SELECT c.id, c.title, c.description, c.creation_date, c.blocked, c.block_reason, c.block_date, c.unblock_reason, c.unblock_date, c.current_column_id " +
                     "FROM cards c " +
                     "JOIN columns col ON c.current_column_id = col.id " +
                     "WHERE col.board_id = ? " +
                     "ORDER BY c.creation_date";
        List<Card> cards = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, boardId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Card card = mapResultSetToCard(rs);
                    cards.add(card);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar cards por board ID", e);
        }
        
        return cards;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM cards WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new IllegalArgumentException("Card não encontrado");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar card", e);
        }
    }

    @Override
    public void deleteByColumnId(Long columnId) {
        String sql = "DELETE FROM cards WHERE current_column_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, columnId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar cards por column ID", e);
        }
    }

    private Card mapResultSetToCard(ResultSet rs) throws SQLException {
        Card card = new Card();
        card.setId(rs.getLong("id"));
        card.setTitle(rs.getString("title"));
        card.setDescription(rs.getString("description"));
        card.setCreationDate(rs.getTimestamp("creation_date").toLocalDateTime());
        card.setBlocked(rs.getBoolean("blocked"));
        card.setBlockReason(rs.getString("block_reason"));
        
        Timestamp blockDate = rs.getTimestamp("block_date");
        if (blockDate != null) {
            card.setBlockDate(blockDate.toLocalDateTime());
        }
        
        card.setUnblockReason(rs.getString("unblock_reason"));
        
        Timestamp unblockDate = rs.getTimestamp("unblock_date");
        if (unblockDate != null) {
            card.setUnblockDate(unblockDate.toLocalDateTime());
        }
        
        card.setCurrentColumnId(rs.getLong("current_column_id"));
        return card;
    }
} 