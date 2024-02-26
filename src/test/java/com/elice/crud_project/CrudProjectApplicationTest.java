package com.elice.crud_project;


import com.elice.crud_project.access.entity.User;
import com.elice.crud_project.access.repository.JdbcTemplateUserRepository;
import com.elice.crud_project.access.service.UserService;
import com.elice.crud_project.board.entity.Board;
import com.elice.crud_project.board.service.BoardService;
import com.elice.crud_project.post.Entity.Post;
import com.elice.crud_project.post.repository.PostRepository;
import com.elice.crud_project.post.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

class CrudProjectApplicationTest {

    @Mock
    private JdbcTemplateUserRepository userRepository;
    private UserService userService;

        @BeforeEach
        void setUp(){
            MockitoAnnotations.openMocks(this);
            userService = new UserService(userRepository);
        }

        @Test
        void testCreatePost(){
            System.out.println("테스트 시작");
            User user = userService.getUserByLoginIdANDPassword("momo", "1234");
            System.out.println( user.getLoginId() + user.getPassword());


        }

    }
