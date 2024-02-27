package com.elice.crud_project.comment.repository;

import com.elice.crud_project.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findCommentsByPostPostId(int postId);
    Comment findCommentByCommentId(int commentId);
}