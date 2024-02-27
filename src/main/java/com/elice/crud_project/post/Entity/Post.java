package com.elice.crud_project.post.Entity;

import com.elice.crud_project.access.entity.User;
import com.elice.crud_project.board.entity.Board;
import com.elice.crud_project.comment.entity.Comment;
import com.elice.crud_project.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name= "post_title", length = 20)
    private String postTitle;

    @Column(name = "post_content", length = 1000)
    private String postContent;



    public void setBoard(Board board) {
        this.board = board;
        if (!this.board.getPosts().contains(this)) {
            this.board.getPosts().add(this);
        }
    }

    public Post(Board board, User user, String postTitle, String postContent) {
        this.board = board;
        this.user = user;
        this.postTitle = postTitle;
        this.postContent = postContent;
    }
}
