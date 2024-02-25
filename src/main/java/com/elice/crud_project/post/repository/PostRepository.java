package com.elice.crud_project.post.repository;

import com.elice.crud_project.board.entity.Board;
import com.elice.crud_project.post.Entity.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;


@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    Page<Post> findAllByBoardOrderByCreatedAtDesc(Board board, Pageable pageable);

    Page<Post> findAllByBoardAndPostTitleContaining(Board board, String keyword, Pageable pageable);

}
