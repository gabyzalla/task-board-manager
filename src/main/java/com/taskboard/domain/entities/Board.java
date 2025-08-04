package com.taskboard.domain.entities;

import com.taskboard.domain.enums.ColumnType;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Board {
    private Long id;
    private String name;
    private List<Column> columns;

    public Board() {
        this.columns = new ArrayList<>();
    }

    public Board(String name) {
        this();
        this.name = name;
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

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
        sortColumns();
    }

    public void addColumn(Column column) {
        column.setBoardId(this.id);
        this.columns.add(column);
        sortColumns();
    }

    public void removeColumn(Column column) {
        this.columns.remove(column);
        sortColumns();
    }

    public Column getColumnById(Long columnId) {
        return columns.stream()
                .filter(column -> column.getId().equals(columnId))
                .findFirst()
                .orElse(null);
    }

    public Column getInitialColumn() {
        return columns.stream()
                .filter(column -> column.getType() == ColumnType.INITIAL)
                .findFirst()
                .orElse(null);
    }

    public Column getFinalColumn() {
        return columns.stream()
                .filter(column -> column.getType() == ColumnType.FINAL)
                .findFirst()
                .orElse(null);
    }

    public Column getCancellationColumn() {
        return columns.stream()
                .filter(column -> column.getType() == ColumnType.CANCELLATION)
                .findFirst()
                .orElse(null);
    }

    public List<Column> getPendingColumns() {
        return columns.stream()
                .filter(column -> column.getType() == ColumnType.PENDING)
                .toList();
    }

    public Column getNextColumn(Column currentColumn) {
        int currentIndex = columns.indexOf(currentColumn);
        if (currentIndex >= 0 && currentIndex < columns.size() - 1) {
            return columns.get(currentIndex + 1);
        }
        return null;
    }

    public Column getPreviousColumn(Column currentColumn) {
        int currentIndex = columns.indexOf(currentColumn);
        if (currentIndex > 0) {
            return columns.get(currentIndex - 1);
        }
        return null;
    }

    public boolean isValidStructure() {
        if (columns.size() < 3) {
            return false;
        }

        long initialCount = columns.stream()
                .filter(col -> col.getType() == ColumnType.INITIAL)
                .count();
        
        long finalCount = columns.stream()
                .filter(col -> col.getType() == ColumnType.FINAL)
                .count();
        
        long cancellationCount = columns.stream()
                .filter(col -> col.getType() == ColumnType.CANCELLATION)
                .count();

        if (initialCount != 1 || finalCount != 1 || cancellationCount != 1) {
            return false;
        }

        Column initialColumn = getInitialColumn();
        Column finalColumn = getFinalColumn();
        Column cancellationColumn = getCancellationColumn();

        if (initialColumn.getOrder() != 1) {
            return false;
        }

        if (finalColumn.getOrder() != columns.size() - 1) {
            return false;
        }

        if (cancellationColumn.getOrder() != columns.size()) {
            return false;
        }

        return true;
    }

    private void sortColumns() {
        columns.sort(Comparator.comparing(Column::getOrder));
    }

    public List<Card> getAllCards() {
        List<Card> allCards = new ArrayList<>();
        for (Column column : columns) {
            allCards.addAll(column.getCards());
        }
        return allCards;
    }

    public Card findCardById(Long cardId) {
        return columns.stream()
                .flatMap(column -> column.getCards().stream())
                .filter(card -> card.getId().equals(cardId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", columnsCount=" + columns.size() +
                '}';
    }
} 