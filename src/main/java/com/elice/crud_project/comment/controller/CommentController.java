package com.elice.crud_project.comment.controller;

import com.elice.crud_project.comment.entity.Comment;
import com.elice.crud_project.comment.entity.CommentDto;
import com.elice.crud_project.comment.mapper.CommentMapper;
import com.elice.crud_project.comment.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @PostMapping("/comment") // 댓글 작성 요청
    public String createComment(@ModelAttribute CommentDto commentDto,
                                @RequestParam int postId,
                                RedirectAttributes redirectAttributes){
      Comment comment = commentMapper.commentDtoToComment(commentDto);
      commentService.createComment(postId, comment);
        System.out.println(commentDto.getCommentContent());
      redirectAttributes.addAttribute("post_id", postId);
      return "redirect:/post/{post_id}"; // 해당 게시글 화면으로 다시 돌아감
    }

    @PostMapping("/comment/{commemt_id}/edit") // 댓글 수정 요청
    public String updateComment(@PathVariable int commemt_id,
                                @ModelAttribute CommentDto commentDto,
                                RedirectAttributes redirectAttributes,
                                @CookieValue(name = "loginId", required = false) String loginId) {

        Comment comment = commentMapper.commentDtoToComment(commentDto);
        Comment updateComment = commentService.updateComment(commemt_id, comment);
        redirectAttributes.addAttribute("postId",updateComment.getPost().getPostId());
        return "redirect:/post/{postId}"; // 해당 게시글 화면으로 다시 돌아감
    }

    @DeleteMapping("/comment/{comment_id}/delete") //댓글 삭제 요청
    public String deleteComment(@PathVariable int comment_id,
                                @CookieValue(name = "loginId", required = false) String loginId) {
        Comment deleteComment = commentService.findCommentByCommentId(comment_id);
        int postId = deleteComment.getPost().getPostId();
        commentService.deleteComment(comment_id);
        return "redirect:/post/" + postId; // 해당 게시글 화면으로 다시 돌아감
    }
}
