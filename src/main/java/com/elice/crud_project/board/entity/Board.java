package com.elice.crud_project.board.entity;

import com.elice.crud_project.access.entity.User;
import com.elice.crud_project.post.Entity.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private int boardId;

    @ManyToOne // 유저 : 게시판 = 1 : N
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "board") // 게시판 : 게시글 = 1 : N
    private List<Post> posts = new ArrayList<>();

    @Column(name = "board_name")
    private String boardName;

    @Column(name = "board_intro")
    private String boardIntro;

    public Board() {
    }

    @Builder
    public Board(int boardId, User user, String boardName, String boardIntro) {
        this.boardId = boardId;
        this.user = user;
        this.boardName = boardName;
        this.boardIntro = boardIntro;
    }
}


