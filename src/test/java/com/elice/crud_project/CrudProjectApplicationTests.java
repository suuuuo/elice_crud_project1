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

import java.sql.*;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class CrudProjectApplicationTests {
	private BoardService boardService;

	@DisplayName("게시판 생성 테스트")
	@Test
	void create_test() throws SQLException {
		// given
		Board board = new Board();
		Connection conn = null;
		PreparedStatement pstmt = null;

		String jdbc_driver = "com.mysql.jdbc.Driver";
		String jdbc_url = "jdbc:mysql://localhost/elice_project?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

		conn = DriverManager.getConnection(jdbc_url, "root", "1609");
		String sql = "INSERT INTO Board(user_id, board_name, board_intro) VALUES(?,?,?)";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, String.valueOf(board.getUser().getUserId()));
		pstmt.setString(2, board.getBoardName());
		pstmt.setString(3, board.getBoardIntro());

		// SQL 쿼리 실행
		String sql2 = "select * from Board";

		pstmt = conn.prepareStatement(sql2);
		// SQL 쿼리 실행
		ResultSet rs = pstmt.executeQuery();
		int i=1;
		// SQL 쿼리 결과값 출력
		if(rs.next()) {
			Board board2 = new Board();
			board.setBoardId(rs.getInt("User_id"));
			board.setBoardName(rs.getString("Board_name"));
			board.setBoardIntro(rs.getString("Board_intro"));
		}
		/*;

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
		System.out.println(savedBoard.getBoardName());
		assertThat(savedBoard.getUser()).isEqualTo(board.getUser());
		assertThat(savedBoard.getBoardName()).isEqualTo(boardName);
		assertThat(savedBoard.getBoardIntro()).isEqualTo(boardIntro);*/

	}
}


