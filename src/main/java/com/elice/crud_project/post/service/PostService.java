package com.elice.crud_project.post.service;

import com.elice.crud_project.board.entity.Board;
import com.elice.crud_project.board.service.BoardService;
import com.elice.crud_project.post.Entity.Post;
import com.elice.crud_project.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;

    private final BoardService boardService;

    public PostService(PostRepository postRepository, BoardService boardService){
        this.boardService = boardService;
        this.postRepository = postRepository;
    }

    public Page<Post> findPostsByBoardANDketword(Board board, String keyword, PageRequest pageRequest){
        if (keyword != null && !keyword.isEmpty()){
            return postRepository.findAllByBoardAndPostTitleContaining(board, keyword,pageRequest);
        }
        else {
            return postRepository.findAllByBoardOrderByCreatedAtDesc(board, pageRequest);
        }
    }
    public Post findPost(int postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public Post createPost(Post post, int boardId){
        Board board = boardService.getBoardById(boardId);
        post.setBoard(board);
        Post createdPost = postRepository.save(post);
        return createdPost;
    }

    public Post updatePost(Post post){
        int postId = post.getPostId();
        Post updatedPost = postRepository.findById(postId).orElse(null);
        updatedPost.setPostTitle(post.getPostTitle());
        updatedPost.setPostContent(post.getPostContent());

        return postRepository.save(updatedPost);
    }

    public void deletePost(int postId){
        Post deletePost = postRepository.findById(postId).orElse(null);

        postRepository.delete(deletePost);
    }


    }
