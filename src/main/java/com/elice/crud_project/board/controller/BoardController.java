package com.elice.crud_project.board.controller;

import com.elice.crud_project.board.entity.Board;
import com.elice.crud_project.board.service.BoardService;
import com.elice.crud_project.post.Entity.Post;
import com.elice.crud_project.post.Entity.PostPostDto;
import com.elice.crud_project.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class BoardController {
    private final BoardService boardService;

    private final PostService postService;

    @Autowired
    public BoardController(BoardService boardService, PostService postService){
        this.boardService = boardService;
        this.postService = postService;
    }

    @GetMapping("/boards") //게시판 목록 - 확인
    public String mainView(Model model){
        List<Board> boards = boardService.getAllBoards();
        model.addAttribute("boards", boards);
        System.out.println(boards.get(0));
        return "board/boards";
    }

    @GetMapping("/boards/{board_id}") // 게시판 메인화면
    public String getBoard(@PathVariable int board_id,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size,
                           @RequestParam(required = false) String keyword,
                           Model model) {
        Board board = boardService.getBoardByBoardId(board_id);
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Post> postPage = postService.findPostsByBoardANDKeyword(board, keyword, pageRequest);

        model.addAttribute("board", board);
        model.addAttribute("keyword", keyword);
        model.addAttribute("postPage", postPage);
        return "board/board";
    }

    @GetMapping("/boards/new") //게시판 생성 - 확인
    public String newBoard(){
        return "board/createBoard";
    }

    @PostMapping("/boards/new") //새 게시판 생성
    public String createBoard(@ModelAttribute BoardForm form) {
        System.out.println(form.getBoardIntro());
        Board board = new Board();

        board.setBoardName(form.getBoardName());
        board.setBoardIntro(form.getBoardIntro());

        int boardId = boardService.saveBoard(board);
        return "redirect:/boards"; //게시판 목록으로 이동
    }

    @GetMapping("/boards/{board_id}/edit") // 게시판 수정
    public String editBoard(@PathVariable int board_id, Model model) {
        model.addAttribute("board", boardService.getBoardByBoardId(board_id));
        return "/board/editBoard";
    }

    @PostMapping("/boards/{board_id}/edit") //게시판 수정 요청
    public String updateBoard(@PathVariable int board_id, @ModelAttribute BoardForm boardForm ) {
        System.out.println("수정 요청!");
        Board board = boardService.getBoardByBoardId(board_id);
        board.setBoardName( boardForm.getBoardName());
        System.out.println( boardForm.getBoardName());
        board.setBoardIntro( boardForm.getBoardIntro());
        boardService.updateBoard(board);

        return "redirect:/boards"; //게시판 목록으로 이동?
    }

    @DeleteMapping("/boards/{board_id}") //게시판 삭제
    public String deleteBoard(@PathVariable int board_id) {
        boardService.deleteBoardById(board_id);
        return "redirect:/boards";
    }
}
