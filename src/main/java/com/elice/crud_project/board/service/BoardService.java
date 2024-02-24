package com.elice.crud_project.board.service;

import com.elice.crud_project.board.entity.Board;
import com.elice.crud_project.board.repository.JdbcTemplateBoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    private final JdbcTemplateBoardRepository boardRepository;

    public BoardService(JdbcTemplateBoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    public List<Board> getAllBoards(){
        return boardRepository.findAll();
    }

    public Board getBoardById(int boardId){
        return boardRepository.findById(boardId).orElse(null);
    }

    public int saveBoard(Board board){
        Board result = boardRepository.save(board);
        return result.getBoardId();
    }
    public void updateBoard(Board board){
        boardRepository.update(board);
    }

    public void deleteBoardById(int boardId){
        boardRepository.deleteById(boardId);
    }


}
