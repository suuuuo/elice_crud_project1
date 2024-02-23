package com.elice.crud_project.board.controller;

import com.elice.crud_project.board.entity.Board;
import com.elice.crud_project.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping("/boards/edit/{Board_id}") // 게시판 수정
    public String editboard(@PathVariable int Board_id, Model model) {
        model.addAttribute("board", boardService.getBoardById(Board_id));
        return "/board/editBoard";
    }


    @GetMapping("/boards/{Board_id}") // 게시판 메인화면
    public String getboard(@PathVariable int Board_id, Model model) {
        model.addAttribute("board", boardService.getBoardById(Board_id));
        return "/board/board";
    }

    @PostMapping("/boards/new")
    public String createBoard(@ModelAttribute BoardForm form) {
        Board board = new Board();

        board.setBoard_name(form.getBoard_name());
        board.setBoard_intro(form.getBoard_intro());

        int Board_id = boardService.saveBoard(board);
        return "redirect:/boards" + Board_id;
    }

    @PutMapping("/boards/edit/{Board_id}")
    public String updateBoard(@PathVariable int Board_id, @ModelAttribute BoardForm form) {
        Board board = boardService.getBoardById(Board_id);
        board.setBoard_name(form.getBoard_name());
        board.setBoard_intro(form.getBoard_intro());
        boardService.updateBoard(board);

        return "redirect:/boards/" + Board_id;
    }

    @DeleteMapping("/boards/{Board_id}")
    public String deleteBoard(@PathVariable int Board_id) {
        boardService.deleteBoardById(Board_id);
        return "redirect:/boards";
    }
}
