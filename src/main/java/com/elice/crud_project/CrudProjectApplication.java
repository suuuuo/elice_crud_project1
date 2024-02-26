package com.elice.crud_project;

import com.elice.crud_project.access.repository.UserRepository;
import com.elice.crud_project.board.repository.BoardRepository;
import com.elice.crud_project.board.repository.JdbcTemplateBoardRepository;
import com.elice.crud_project.post.repository.PostRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration; //DB 추가하고 나면 삭제하기
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration; //DB 추가하고 나면 삭제하기
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import com.elice.crud_project.comment.repository.CommentRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class}) //DB 추가하고 나면 삭제하기
public class CrudProjectApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(CrudProjectApplication.class, args);
	}

	@Bean
	@Profile({"local"})
	public DataInit stubDataInit(UserRepository userRepository, JdbcTemplateBoardRepository boardRepository,
								 PostRepository postRepository,
								 CommentRepository commentRepository) {
		return new DataInit(userRepository, boardRepository, postRepository, commentRepository);
	}

}
