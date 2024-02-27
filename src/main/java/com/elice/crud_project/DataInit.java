package com.elice.crud_project;

import com.elice.crud_project.access.entity.User;
import com.elice.crud_project.access.repository.UserRepository;
import com.elice.crud_project.board.entity.Board;
import com.elice.crud_project.board.repository.BoardRepository;
import com.elice.crud_project.board.repository.JdbcTemplateBoardRepository;
import com.elice.crud_project.board.service.BoardService;
import com.elice.crud_project.comment.entity.Comment;
import com.elice.crud_project.comment.repository.CommentRepository;
import com.elice.crud_project.post.Entity.Post;
import com.elice.crud_project.post.repository.PostRepository;
import com.elice.crud_project.post.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

public class DataInit {

    //로그 추가
    private static final Logger log = LoggerFactory.getLogger(DataInit.class);
    private final UserRepository userRepository;
    private final JdbcTemplateBoardRepository boardRepository;
    private final PostRepository postRepository;

    private  final CommentRepository commentRepository;

    @EventListener({ApplicationReadyEvent.class})
    public void init() {
        User user = new User("soo", "1234");
        this.userRepository.save(user);

        User user2 = new User("joo", "1234");
        this.userRepository.save(user2);

        this.boardRepository.save(new Board(user, "중고거래 게시판", "사용자들이 중고 물품을 사고팔 수 있는 게시판입니다."));
        this.boardRepository.save(new Board(user, "반려동물 게시판", "사용자들이 반려 동물 정보를 공유할 수 있는 게시판."));
        this.boardRepository.save(new Board(user2, "독후감 게시판", "독후감을 공유할 수 있는 게시판입니다."));

        Board board = (Board)this.boardRepository.findById(1).orElseThrow(() -> {
            return new RuntimeException();
        });

        this.postRepository.save(new Post(board, user, "테스트", "테스트 게시글입니다!"));

        Post post = (Post)this.postRepository.findByPostId(1);
        this.commentRepository.save(new Comment(post, user, "안녕하세요!"));

    }



    public DataInit(UserRepository userRepository,
        final JdbcTemplateBoardRepository boardRepository, final PostRepository postRepository, final CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }
}
