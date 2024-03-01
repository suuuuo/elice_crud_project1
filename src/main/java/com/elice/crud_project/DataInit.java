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

        User user2 = new User("momo", "5678");
        this.userRepository.save(user2);

        this.boardRepository.save(new Board(user, "나눔 게시판", "사용자들이 중고 물품을 나눔할 수 있는 게시판입니다."));
        this.boardRepository.save(new Board(user2, "반려동물 게시판", "사용자들이 반려 동물 정보를 공유할 수 있는 게시판."));
        this.boardRepository.save(new Board(user, "레시피 게시판", "레시피를 공유할 수 있는 게시판입니다."));

        Board board = (Board)this.boardRepository.findById(1).orElseThrow(() -> {
            return new RuntimeException();
        });
        Board board2 = (Board)this.boardRepository.findById(2).orElseThrow(() -> {
            return new RuntimeException();
        });
        Board board3 = (Board)this.boardRepository.findById(3).orElseThrow(() -> {
            return new RuntimeException();
        });


        this.postRepository.save(new Post(board, user2, "책상, 의자 나눔 합니다","이사하면서 책상이랑 의자를 새로 사서," +
                "기존에 사용하던 책상이랑 의자 나눔하려고 합니다. 상태는 깨끗하구요, 필요하신 분은 연락 주세요!" ));
        this.postRepository.save(new Post(board, user, "중고거래 게시판", "중고거래 게시판이 새로 생성되었습니다!"));
        this.postRepository.save(new Post(board2, user2, "반려동물 게시판", "반려동물 게시판이 새로 생성되었습니다!"));
        this.postRepository.save(new Post(board3, user, "레시피 게시판", "레시피 게시판이 새로 생성되었습니다!"));


        Post post = (Post)this.postRepository.findByPostId(1);
        Post post2 = (Post)this.postRepository.findByPostId(2);
        Post post3 = (Post)this.postRepository.findByPostId(3);
        Post post4 = (Post)this.postRepository.findByPostId(4);

        this.commentRepository.save(new Comment(post2, user2, "안녕하세요!"));
        this.commentRepository.save(new Comment(post2, user, "안녕하세요~~"));

        this.commentRepository.save(new Comment(post, user, "안녕하세요, 책상 높이가 얼마인가요?"));
        this.commentRepository.save(new Comment(post, user2, "높이 720, 상판은 1400*600 입니다!"));

        this.commentRepository.save(new Comment(post3, user, "두 번째 게시판이네요!"));
        this.commentRepository.save(new Comment(post3, user2, "많이 이용해 주세요!"));

        this.commentRepository.save(new Comment(post4, user2, "요리는 잘 못하지만 만들어 보았습니다~~"));
        this.commentRepository.save(new Comment(post4, user, "혼자 해먹기 좋은 요리들 올리기에 좋은 것 같아요!"));


    }



    public DataInit(UserRepository userRepository,
        final JdbcTemplateBoardRepository boardRepository, final PostRepository postRepository, final CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }
}
