package com.elice.crud_project.board.repository;

import com.elice.crud_project.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAll();

    Optional<Board> findById(int boardId);

    Board save(Board board);

    void delete(Board board);
}