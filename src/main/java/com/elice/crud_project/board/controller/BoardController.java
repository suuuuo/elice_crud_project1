package com.elice.crud_project.board.controller;

import com.elice.crud_project.access.entity.User;
import com.elice.crud_project.access.service.UserService;
import com.elice.crud_project.board.entity.Board;
import com.elice.crud_project.board.service.BoardService;
import com.elice.crud_project.comment.entity.Comment;
import com.elice.crud_project.comment.service.CommentService;
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

    private final UserService userService;

    private final PostService postService;

    private final CommentService commentService;

    @Autowired
    public BoardController(BoardService boardService,
                           PostService postService,
                           UserService userService,
                           CommentService commentService){
        this.boardService = boardService;
        this.postService = postService;
        this.userService = userService;
        this.commentService= commentService;
    }

    @GetMapping("/boards") //게시판 목록
    public String mainView(Model model,
                           @CookieValue(name = "loginId", required = false) String loginId){
        model.addAttribute("user", loginId);
        List<Board> boards = boardService.getAllBoards();
        model.addAttribute("boards", boards);
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

    @GetMapping("/boards/new") //게시판 생성 -
    public String newBoard(){
        return "board/createBoard";
    }

    @PostMapping("/boards/new") //새 게시판 생성
    public String createBoard(@ModelAttribute BoardForm form,
                              @CookieValue(name = "loginId", required = false) String loginId) {

        System.out.println("생성 요청!");
        System.out.println("게시판 이름 : " + form.getBoardName());
        System.out.println("게시판 설명 : " + form.getBoardIntro());

        User user = userService.getUserByLoginId(loginId);

        Board board = new Board();
        board.setUser(user);
        board.setBoardName(form.getBoardName());
        board.setBoardIntro(form.getBoardIntro());

        int boardId = boardService.saveBoard(board);
        return "redirect:/boards"; //게시판 목록으로 이동
    }


    @GetMapping("/boards/{board_id}/edit") // 게시판 수정
    public String editBoard(@PathVariable int board_id, Model model
                            ,@CookieValue(name = "loginId", required = false) String loginId) {
        Board board = boardService.getBoardByBoardId(board_id);
        if(board.getUser().getLoginId().equals(loginId)) {
            model.addAttribute("board", boardService.getBoardByBoardId(board_id));
            return "/board/editBoard";
        }
        else {
            System.out.println("게시판을 생성한 사람만 수정할 수 있습니다!");
            return "redirect:/boards";
        }
    }

    @PostMapping("/boards/{board_id}/edit") //게시판 수정 요청
    public String updateBoard(@PathVariable int board_id,
                              @ModelAttribute BoardForm boardForm ) {
        System.out.println("수정 요청!");
        Board board = boardService.getBoardByBoardId(board_id);
        board.setBoardName( boardForm.getBoardName());
        System.out.println( boardForm.getBoardName());
        board.setBoardIntro( boardForm.getBoardIntro());
        boardService.updateBoard(board);

        return "redirect:/boards"; //게시판 목록으로 이동?
    }



    @DeleteMapping("/boards/{board_id}/delete") //게시판 삭제
    public String deleteBoard(@PathVariable int board_id,
                              @CookieValue(name = "loginId", required = false) String loginId) {

        Board board = boardService.getBoardByBoardId(board_id);
        if(board.getUser().getLoginId().equals(loginId)) {
            List<Post> postList = postService.findPostsByBoardId(board_id);
            for(int i = 0; i<postList.size(); i++) {
                Post post = postList.get(i);

                List<Comment> commentList = commentService.findCommentByPostId(post.getPostId());
                for(int j = 0; j<commentList.size(); j++) {
                    Comment comment = commentList.get(j);
                    commentService.deleteComment(comment.getCommentId());
                }
                postService.deletePost(post.getPostId());
            }

            boardService.deleteBoardById(board_id);
            return "redirect:/boards";
        }
        else{
            System.out.println("게시판을 생성한 사람만 삭제할 수 있습니다!");
            return "redirect:/boards/{board_id}";
        }
    }
}
