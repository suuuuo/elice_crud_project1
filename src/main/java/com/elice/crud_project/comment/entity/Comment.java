package com.elice.crud_project.comment.entity;

import com.elice.crud_project.access.entity.User;
import com.elice.crud_project.global.entity.BaseEntity;
import com.elice.crud_project.post.Entity.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    private int commentId;

    @ManyToOne //양방향 관계
    @JoinColumn(name="post_id")
    private Post post;

    @ManyToOne //단방향 관계
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name="comment_content", length = 500)
    private String commentContent;

    public Comment(Post post,User user, String commentContent){
        this.post = post;
        this.user = user;
        this.commentContent = commentContent;
    }
}
