package com.elice.crud_project.comment.service;

import com.elice.crud_project.comment.entity.Comment;
import com.elice.crud_project.comment.repository.CommentRepository;
import com.elice.crud_project.post.Entity.Post;
import com.elice.crud_project.post.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository){
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public List<Comment> findComments() { return commentRepository.findAll(); } //댓글 전체 조회
    public List<Comment> findCommentByPostId(int postId) { // 포스트 아이디로 댓글 조회
        return commentRepository.findCommentsByPostPostId(postId);
    }
    public Comment findCommentByCommentId(int commentId){ // 댓글아이디로 댓글 조회
        return commentRepository.findById(commentId).orElse(null);
    }

    public Comment createComment(int postId, Comment comment){ // 댓글 생성
        Post post = postRepository.findById(postId).orElse(null);
        comment.setPost(post);
        return commentRepository.save(comment);
    }

    public Comment updateComment(int commentId, Comment comment){ //댓글 수정
        Comment updateComment = commentRepository.findCommentByCommentId(commentId);
        updateComment.setCommemtContent(comment.getCommemtContent());
        return commentRepository.save(updateComment);
    }

    public void deleteComment(int commentId){ // 댓글삭제
        Comment deleteComment = commentRepository.findById(commentId).orElse(null);
        commentRepository.delete(deleteComment);
    }

}
