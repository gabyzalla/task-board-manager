package com.taskboard.domain.entities;

import com.taskboard.domain.enums.ColumnType;
import java.util.ArrayList;
import java.util.List;

public class Column {
    private Long id;
    private String name;
    private int order;
    private ColumnType type;
    private Long boardId;
    private List<Card> cards;

    public Column() {
        this.cards = new ArrayList<>();
    }

    public Column(String name, int order, ColumnType type, Long boardId) {
        this();
        this.name = name;
        this.order = order;
        this.type = type;
        this.boardId = boardId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public ColumnType getType() {
        return type;
    }

    public void setType(ColumnType type) {
        this.type = type;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void addCard(Card card) {
        this.cards.add(card);
        card.setCurrentColumnId(this.id);
    }

    public void removeCard(Card card) {
        this.cards.remove(card);
    }

    public Card findCardById(Long cardId) {
        return cards.stream()
                .filter(card -> card.getId().equals(cardId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return "Column{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", order=" + order +
                ", type=" + type +
                ", boardId=" + boardId +
                ", cardsCount=" + cards.size() +
                '}';
    }
} 