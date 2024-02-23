package com.elice.crud_project.board.entity;

import com.elice.crud_project.access.entity.User;
import com.elice.crud_project.post.Entity.Post;
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
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Board_id")
    private int Board_id;

    @ManyToOne // 유저 : 게시판 = 1 : N
    @JoinColumn(name="User_id")
    private User user;

    @OneToMany(mappedBy = "board") // 게시판 : 게시글 = 1 : N
    private List<Post> posts = new ArrayList<>();

    @Column(name = "Board_name")
    private String Board_name;

    @Column(name = "Board_intro")
    private String Board_intro;

    public Board(int boardId, User user, String boardName, String boardIntro) {
        this.Board_id = boardId;
        this.user = user;
        this.Board_name = boardName;
        this.Board_intro = boardIntro;
    }


    public static BoardBuilder builder() {
        return new BoardBuilder();
    }

    public BoardBuilder toBuilder() {
        return (new BoardBuilder().Board_id(this.Board_id).user(this.user)
                .Board_name(this.Board_name).Board_intro(this.Board_intro));
      
    }

    public static class BoardBuilder{
        private int Board_id;
        private User user;
        private String Board_name;
        private String Board_intro;

        BoardBuilder() { }

        public BoardBuilder Board_id(final int Board_id) {
            this.Board_id = Board_id; return this;
        }

        public BoardBuilder Board_name(final String Board_name) {
            this.Board_name = Board_name; return this;
        }

        public BoardBuilder Board_intro(final String Board_intro) {
            this.Board_intro = Board_intro; return this;
        }

        public BoardBuilder user(final User user) {
            this.user= user; return this;
        }

        public Board build() {
            return new Board(this.Board_id, this.user, this.Board_name, this.Board_intro);
        }

        public String toString() {
            return "Board.BoardBuilder(Board_id=" + this.Board_id +
                    ", User.id =" + this.user.getUserId()
                    + ", Board_name =" + this.Board_name
                    + ", Board_intro =" + this.Board_intro + ")";
        }
    }
}


