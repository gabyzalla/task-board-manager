package com.taskboard.domain.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Card {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime creationDate;
    private boolean blocked;
    private String blockReason;
    private LocalDateTime blockDate;
    private String unblockReason;
    private LocalDateTime unblockDate;
    private Long currentColumnId;
    private List<CardMovement> movements;

    public Card() {
        this.creationDate = LocalDateTime.now();
        this.blocked = false;
        this.movements = new ArrayList<>();
    }

    public Card(String title, String description) {
        this();
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public String getBlockReason() {
        return blockReason;
    }

    public void setBlockReason(String blockReason) {
        this.blockReason = blockReason;
    }

    public LocalDateTime getBlockDate() {
        return blockDate;
    }

    public void setBlockDate(LocalDateTime blockDate) {
        this.blockDate = blockDate;
    }

    public String getUnblockReason() {
        return unblockReason;
    }

    public void setUnblockReason(String unblockReason) {
        this.unblockReason = unblockReason;
    }

    public LocalDateTime getUnblockDate() {
        return unblockDate;
    }

    public void setUnblockDate(LocalDateTime unblockDate) {
        this.unblockDate = unblockDate;
    }

    public Long getCurrentColumnId() {
        return currentColumnId;
    }

    public void setCurrentColumnId(Long currentColumnId) {
        this.currentColumnId = currentColumnId;
    }

    public List<CardMovement> getMovements() {
        return movements;
    }

    public void setMovements(List<CardMovement> movements) {
        this.movements = movements;
    }

    public void addMovement(CardMovement movement) {
        this.movements.add(movement);
    }

    public void block(String reason) {
        this.blocked = true;
        this.blockReason = reason;
        this.blockDate = LocalDateTime.now();
    }

    public void unblock(String reason) {
        this.blocked = false;
        this.unblockReason = reason;
        this.unblockDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                ", blocked=" + blocked +
                ", currentColumnId=" + currentColumnId +
                '}';
    }
} 