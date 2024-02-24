package com.elice.crud_project;

import com.elice.crud_project.access.entity.User;
import com.elice.crud_project.board.entity.Board;
import com.elice.crud_project.board.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class CrudProjectApplicationTests {
	private BoardService boardService;

	@DisplayName("게시판 생성 테스트")
	@Test
	void create_test(){
		// given
		    int userId = 1;
			String loginId = "soo";
			String password = "1235";

		User user = new User(userId, loginId, password);

		    int boardUserId = user.getUserId();
			String boardName = "first";
			String boardIntro = "this is first create!";

		Board board = Board.builder()
                .user(user)
				.boardName(boardName)
				.boardIntro(boardIntro)
				.build();

			// when ...
		 int boardId = boardService.saveBoard(board);

			// then
		   Board savedBoard = boardService.getBoardById(boardId);
		   assertThat(savedBoard.getUser()).isEqualTo(board.getUser());
		   assertThat(savedBoard.getBoardName()).isEqualTo(boardName);
		   assertThat(savedBoard.getBoardIntro()).isEqualTo(boardIntro);

		}
}


