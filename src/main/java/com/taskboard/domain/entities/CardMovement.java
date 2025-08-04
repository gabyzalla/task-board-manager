package com.taskboard.domain.entities;

import java.time.LocalDateTime;

public class CardMovement {
    private Long id;
    private Long cardId;
    private Long fromColumnId;
    private Long toColumnId;
    private LocalDateTime movementDate;

    public CardMovement() {
        this.movementDate = LocalDateTime.now();
    }

    public CardMovement(Long cardId, Long fromColumnId, Long toColumnId) {
        this();
        this.cardId = cardId;
        this.fromColumnId = fromColumnId;
        this.toColumnId = toColumnId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Long getFromColumnId() {
        return fromColumnId;
    }

    public void setFromColumnId(Long fromColumnId) {
        this.fromColumnId = fromColumnId;
    }

    public Long getToColumnId() {
        return toColumnId;
    }

    public void setToColumnId(Long toColumnId) {
        this.toColumnId = toColumnId;
    }

    public LocalDateTime getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(LocalDateTime movementDate) {
        this.movementDate = movementDate;
    }

    @Override
    public String toString() {
        return "CardMovement{" +
                "id=" + id +
                ", cardId=" + cardId +
                ", fromColumnId=" + fromColumnId +
                ", toColumnId=" + toColumnId +
                ", movementDate=" + movementDate +
                '}';
    }
} 