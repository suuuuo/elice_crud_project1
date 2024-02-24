package com.elice.crud_project;

import com.elice.crud_project.board.repository.BoardRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration; //DB 추가하고 나면 삭제하기
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration; //DB 추가하고 나면 삭제하기
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class}) //DB 추가하고 나면 삭제하기
public class CrudProjectApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(CrudProjectApplication.class, args);
	}

	/*@Bean
	@Profile("local")
	public DataInit stubDataInit(BoardRepository boardRepository, PostRepository postRepository, CommentRepository commentRepository) {
		return new DataInit(boardRepository, postRepository, commentRepository);
	}*/


}
