package com.elice.crud_project;

import com.elice.crud_project.access.controller.UserController;
import com.elice.crud_project.access.entity.User;
import com.elice.crud_project.access.service.UserService;
import com.elice.crud_project.board.entity.Board;
import com.elice.crud_project.board.repository.JdbcTemplateBoardRepository;
import com.elice.crud_project.board.service.BoardService;
import com.elice.crud_project.comment.service.CommentService;
import com.elice.crud_project.post.Entity.Post;
import com.elice.crud_project.post.repository.PostRepository;
import com.elice.crud_project.post.service.PostService;
import org.assertj.core.api.BooleanArrayAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
//@ExtendWith(MockitoExtension.class)
//@WebMvcTest(UserController.class)
class CrudProjectApplicationTest {

    @Autowired
    TestRestTemplate restTemplate;
    //@Autowired
   // private MockMvc mockMvc;

    @Autowired
    private UserService userService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private JdbcTemplateBoardRepository boardRepository;
    private BoardService boardService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;
    @BeforeEach
    void setUp() {
        boardService = new BoardService(boardRepository);
        postService = new PostService(postRepository, boardService,commentService);
    }

   /*  @DisplayName("[POST] 새 계정 생성 테스트")
    @Test
    public void CreateTEST() throws Exception {
       User user = User.builder()
                .loginId("joo")
                .password("1234")
                .build();

        User saveUser = new User();
        saveUser.setUserId(3);
        saveUser.setLoginId(user.getLoginId());
        saveUser.setPassword(user.getPassword());

        given(userService.saveUser(any(User.class))).willReturn(saveUser.getUserId());

        mockMvc.perform(post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"loginId\": \"soo\", \"password\": \"1234\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(3))
                .andExpect(jsonPath("$.loginId").value("soo"))
                .andExpect(jsonPath("$.password").value("1234"));
                }*/

/*


    @DisplayName("게시판 수정 테스트")
    @Test
    void updateBoardTest(){

        Board board = new Board(4, userService.getUserByLoginId("soo"), "테스트", "테스트 게시판");
        when(boardRepository.findById(4)).thenReturn(Optional.of(board));

        Board updateBoard = new Board(4, userService.getUserByLoginId("soo"), "바뀐 이름", "바뀐 테스트 입니다");
        when(boardRepository.save(any(Board.class))).thenReturn(updateBoard);

        Board result = boardService.updateBoard2(updateBoard);

        assertEquals("바뀐 이름",result.getBoardName());
        assertEquals("바뀐 테스트",result.getBoardIntro());
        verify(boardRepository).save(any(Board.class));
    }*/

    @LocalServerPort
    int port = 8080;

    @Test
    void create_test() {
        User user = User.builder()
                .loginId("joo")
                .password("1234")
                .build();

        Post post = new Post(boardService.getBoardByBoardId(1), userService.getUserByLoginId("soo"),
                "테스트 게시글", "테스트 게시글입니다!");

        String url = "http://localhost:" + port + "/post/create";

        // when
        ResponseEntity<Integer> responseEntity = restTemplate.postForEntity(url, post, Integer.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        Post createdPost = postService.findPost(responseEntity.getBody());
        assertThat(createdPost.getPostTitle()).isEqualTo("테스트 게시글");
        assertThat(createdPost.getPostTitle()).isEqualTo("테스트 게시글입니다!");

    }


}
