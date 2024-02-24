package com.elice.crud_project.board.controller;

import com.elice.crud_project.board.entity.Board;
import com.elice.crud_project.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class BoardController {
    private BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService){
        this.boardService = boardService;
    }

        @GetMapping("/boards") //게시판 목록
        public String mainview(Model model){
        model.addAttribute("boards", boardService.getAllBoards());
        return "board/boards";
    }

    @GetMapping("/boards/new") //게시판 생성
    public String newboard(){
        return "board/createBoard";
    }

    @GetMapping("/boards/edit/{board_id}") // 게시판 수정
    public String editboard(@PathVariable int boardId, Model model) {
        model.addAttribute("board", boardService.getBoardById(boardId));
        return "/board/editBoard";
    }

    @GetMapping("/boards/{Board_id}") // 게시판 메인화면
    public String getboard(@PathVariable int board_id, Model model) {
        model.addAttribute("board", boardService.getBoardById(board_id));
        return "/board/board";
    }

    @PostMapping("/boards/new") //새 게시판 생성
    public String createBoard(@ModelAttribute BoardForm form) {
        Board board = new Board();

        board.setBoardName(form.getBoardName());
        board.setBoardIntro(form.getBoardIntro());

        int boardId = boardService.saveBoard(board);
        return "redirect:/boards" + boardId; //새로 생긴 게시판으로 이동?
    }

    @PutMapping("/boards/edit/{board_id}") //게시판 수정 요청
    public String updateBoard(@PathVariable int boardId, @ModelAttribute BoardForm form) {
        Board board = boardService.getBoardById(boardId);
        board.setBoardName(form.getBoardName());
        board.setBoardIntro(form.getBoardIntro());
        boardService.updateBoard(board);

        return "redirect:/boards/" + boardId; //수정된 게시판으로 이동?
    }

    @DeleteMapping("/boards/{board_id}") //게시판 삭제
    public String deleteBoard(@PathVariable int boardId) {
        boardService.deleteBoardById(boardId);
        return "redirect:/boards";
    }
}
