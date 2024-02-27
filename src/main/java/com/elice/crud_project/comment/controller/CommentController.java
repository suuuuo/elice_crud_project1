package com.elice.crud_project.comment.controller;

import com.elice.crud_project.access.entity.User;
import com.elice.crud_project.access.service.UserService;
import com.elice.crud_project.comment.entity.Comment;
import com.elice.crud_project.comment.entity.CommentDto;
import com.elice.crud_project.comment.mapper.CommentMapper;
import com.elice.crud_project.comment.service.CommentService;
import com.elice.crud_project.post.Entity.Post;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final UserService userService;

    @PostMapping("/comment") // 댓글 작성 요청
    public String createComment(@ModelAttribute CommentDto commentDto,
                                @RequestParam int postId,
                                RedirectAttributes redirectAttributes,
                                @CookieValue(name = "loginId", required = false) String loginId) {

        Comment comment = commentMapper.commentDtoToComment(commentDto);
        User user = userService.getUserByLoginId(loginId);
        comment.setUser(user);
        commentService.createComment(postId, comment);
        System.out.println(commentDto.getCommentContent());
        redirectAttributes.addAttribute("post_id", postId);
        return "redirect:/post/{post_id}"; // 해당 게시글 화면으로 다시 돌아감
    }

    @PostMapping("/comment/{comment_id}/edit") // 댓글 수정 요청
    public String updateComment(@PathVariable int comment_id,
                                @ModelAttribute CommentDto commentDto,
                                RedirectAttributes redirectAttributes,
                                @CookieValue(name = "loginId", required = false) String loginId) {

        Comment basecomment = commentService.findCommentByCommentId(comment_id);
        if (basecomment.getUser().getLoginId().equals(loginId)) {
            Comment comment = commentMapper.commentDtoToComment(commentDto);
            Comment updateComment = commentService.updateComment(comment_id, comment);
            redirectAttributes.addAttribute("postId", updateComment.getPost().getPostId());
            return "redirect:/post/{postId}"; // 해당 게시글 화면으로 다시 돌아감
        } else {
            System.out.println("댓글을 작성한 사람만 수정할 수 있습니다!");
            redirectAttributes.addAttribute("postId", basecomment.getPost().getPostId());
            return "redirect:/post/{postId}";
        }
    }

    @DeleteMapping("/comment/{comment_id}/delete") //댓글 삭제 요청
    public String deleteComment(@PathVariable int comment_id,
                                RedirectAttributes redirectAttributes,
                                @CookieValue(name = "loginId", required = false) String loginId) {

        Comment comment = commentService.findCommentByCommentId(comment_id);
        System.out.println(comment.getUser().getLoginId());

        if(comment.getUser().getLoginId().equals(loginId)){
            int postId = comment.getPost().getPostId();
            commentService.deleteComment(comment_id);
            return "/post/" + postId; // 해당 게시글 화면으로 다시 돌아감

        } else {
            System.out.println("댓글을 작성한 사람만 삭제할 수 있습니다!");
            redirectAttributes.addAttribute("postId", comment.getPost().getPostId());
            return "/post/{postId}";
        }
    }


}
