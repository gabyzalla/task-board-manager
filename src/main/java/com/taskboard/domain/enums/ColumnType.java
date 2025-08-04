package com.taskboard.domain.enums;

public enum ColumnType {
    INITIAL("Inicial"),
    PENDING("Pendente"),
    FINAL("Final"),
    CANCELLATION("Cancelamento");

    private final String description;

    ColumnType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
} 