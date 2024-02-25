package com.elice.crud_project.post.controller;


import com.elice.crud_project.board.service.BoardService;
import com.elice.crud_project.comment.entity.Comment;
import com.elice.crud_project.comment.service.CommentService;
import com.elice.crud_project.post.Entity.Post;
import com.elice.crud_project.post.Entity.PostPostDto;
import com.elice.crud_project.post.mapper.PostMapper;
import com.elice.crud_project.post.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    private final BoardService boardService;
    private final PostMapper postMapper;
    private final CommentService commentService;

    @GetMapping("/post/{post_id}") // 게시글 눌렀을 때 화면 : 게시글 조회
    public String postMain(@PathVariable int post_id, Model model){
        Post post = postService.findPost(post_id);
        model.addAttribute("post", post);
        //코멘트 추가
        List<Comment> commentList = commentService.findCommentByPostId(post_id);
        model.addAttribute("comments",commentList);
        return "post/post";
    }

    /*
    @GetMapping("/post/create") //게시글 생성창
    public String createPostPage(@RequestParam int boardId, Model model){
        model.addAttribute("boardId", boardId); // 수정?
        return "post/createPost";
    }*/

    @GetMapping("/post/create") // 확인..
    public String createPostPage(){
        return "post/createPost";
    }

    @GetMapping("/post/{post_id}/edit") // 게시글 수정창
    public String editPostPage(@PathVariable int post_id, Model model){
        Post post = postService.findPost(post_id);
        model.addAttribute("post", post);
        return "post/editPost";
    }

    @PostMapping("/post/create") // 게시글 생성 요청
    public String createPost(@ModelAttribute PostPostDto postPostDto, @RequestParam int boardId){
        Post post = postMapper.postPostDTOToPost(postPostDto);
        Post createPost = postService.createPost(post,boardId);
        return "redirect:/boards/" + createPost.getBoard().getBoardId(); // 생성한 게시글 조회 화면으로 이동
    }

    @PostMapping("/post/{post_id}/edit")
    public String editPost(@PathVariable int post_id,
                           @ModelAttribute PostPostDto postPostDto,
                           RedirectAttributes redirectAttributes) {
        Post post = postMapper.postPostDTOToPost(postPostDto);
        Post editPost = postService.updatePost(post);

        redirectAttributes.addAttribute("post_id", editPost.getPostId());
        redirectAttributes.addFlashAttribute("message", "게시글이 수정되었습니다.");
        return "redirect:/post/{post_id}"; // 수정한 게시글 조회 화면
    }

    @DeleteMapping("post/{post_id}")
    public String deletePost(@PathVariable int post_id, RedirectAttributes redirectAttributes){
        postService.deletePost(post_id);
        redirectAttributes.addFlashAttribute("message", "게시글이 삭제되었습니다!");
        return "redirect:/board/board"; //게시판 메인화면
    }
}
