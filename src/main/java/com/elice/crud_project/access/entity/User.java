package com.elice.crud_project.access.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "login_id", length = 20)
    private String loginId;

    @Column(name = "password", length = 20)
    private String password;

    @Builder
    public User(int userId, String loginId, String password){
        this.userId = userId;
        this.loginId = loginId;
        this.password = password;
    }

}
