package com.elice.crud_project.board.repository;

import com.elice.crud_project.access.entity.User;
import com.elice.crud_project.access.service.UserService;
import com.elice.crud_project.board.entity.Board;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTemplateBoardRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserService userService;
    public JdbcTemplateBoardRepository( DataSource dataSource, UserService userService){
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.userService = userService;
    }

    public Board save(Board board){
        String sql = "insert into board (user_id, board_name, board_intro) values (?, ?, ?)";
        int boardId = jdbcTemplate.update(sql, board.getUser().getUserId(), board.getBoardName(), board.getBoardIntro());
        board.setBoardId((int)boardId);
        return board;
    }

    public int update(Board board){
        String sql = "update board set board_name = ? , board_intro = ?  where board_id = ?";
        int result = jdbcTemplate.update(sql, board.getBoardName(), board.getBoardIntro(), board.getBoardId());
        return result;
    }

    public List<Board> findAll(){
        String sql = "select * from board";
        return jdbcTemplate.query(sql, boardRowMapper());
    }

    public Optional<Board> findById(int boardId) {
        String sql = "select * from board where board_id = ?";
        return jdbcTemplate.query(sql, boardRowMapper(), boardId).stream().findAny();
    }

    public void deleteById(int boardId){
        String sql = "DELETE FROM Board WHERE board_id = ?";
        jdbcTemplate.update(sql, boardId);
    }

    private RowMapper<Board> boardRowMapper(){
        return (rs, rowNum) -> {
            Board board = new Board();
            board.setUser((User)userService.getUserByUserId(rs.getInt("user_id")));
            board.setBoardId(rs.getInt("board_id"));
            board.setBoardName(rs.getString("board_name"));
            board.setBoardIntro(rs.getString("board_intro"));
            return board;
        };
    }
}
