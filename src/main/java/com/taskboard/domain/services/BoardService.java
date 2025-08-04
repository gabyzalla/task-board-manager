package com.taskboard.domain.services;

import com.taskboard.domain.entities.Board;
import com.taskboard.domain.entities.Column;
import com.taskboard.domain.enums.ColumnType;
import com.taskboard.domain.repositories.BoardRepository;
import com.taskboard.domain.repositories.ColumnRepository;
import java.util.List;
import java.util.Optional;

public class BoardService {
    private final BoardRepository boardRepository;
    private final ColumnRepository columnRepository;

    public BoardService(BoardRepository boardRepository, ColumnRepository columnRepository) {
        this.boardRepository = boardRepository;
        this.columnRepository = columnRepository;
    }

    public Board createBoard(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do board não pode ser vazio");
        }

        Optional<Board> existingBoard = boardRepository.findByName(name);
        if (existingBoard.isPresent()) {
            throw new IllegalArgumentException("Já existe um board com este nome");
        }

        Board board = new Board(name);
        return boardRepository.save(board);
    }

    public Board createBoardWithDefaultColumns(String name) {
        Board board = createBoard(name);
        
        Column initialColumn = new Column("A Fazer", 1, ColumnType.INITIAL, board.getId());
        Column pendingColumn = new Column("Em Andamento", 2, ColumnType.PENDING, board.getId());
        Column finalColumn = new Column("Concluído", 3, ColumnType.FINAL, board.getId());
        Column cancellationColumn = new Column("Cancelado", 4, ColumnType.CANCELLATION, board.getId());

        board.addColumn(initialColumn);
        board.addColumn(pendingColumn);
        board.addColumn(finalColumn);
        board.addColumn(cancellationColumn);

        for (Column column : board.getColumns()) {
            columnRepository.save(column);
        }

        return boardRepository.save(board);
    }

    public List<Board> getAllBoards() {
        List<Board> boards = boardRepository.findAll();
        for (Board board : boards) {
            List<Column> columns = columnRepository.findByBoardId(board.getId());
            board.setColumns(columns);
        }
        return boards;
    }

    public Optional<Board> getBoardById(Long id) {
        Optional<Board> boardOpt = boardRepository.findById(id);
        if (boardOpt.isPresent()) {
            Board board = boardOpt.get();
            List<Column> columns = columnRepository.findByBoardId(board.getId());
            board.setColumns(columns);
        }
        return boardOpt;
    }

    public void deleteBoard(Long id) {
        if (!boardRepository.existsById(id)) {
            throw new IllegalArgumentException("Board não encontrado");
        }
        
        columnRepository.deleteByBoardId(id);
        boardRepository.deleteById(id);
    }

    public boolean isValidBoardStructure(Board board) {
        return board.isValidStructure();
    }

    public void validateBoardStructure(Board board) {
        if (!isValidBoardStructure(board)) {
            throw new IllegalArgumentException("Estrutura do board inválida. " +
                    "Deve ter pelo menos 3 colunas: uma inicial, uma final e uma de cancelamento. " +
                    "A coluna inicial deve ser a primeira, a final a penúltima e a de cancelamento a última.");
        }
    }
} 