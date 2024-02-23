package com.elice.crud_project.board.repository;

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
    public JdbcTemplateBoardRepository(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Board save(Board board){
        String sql = "insert into board (user_id, board_name, board_intro) values (?, ?, ?)";
        int Board_id = jdbcTemplate.update(sql, board.getUser().getUserId(), board.getBoardName(), board.getBoardIntro());
        board.setBoardId((int)Board_id);
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

    public void deleteById(int Board_id){
        String sql = "DELETE FROM Board WHERE board_id = ?";
        jdbcTemplate.update(sql, Board_id);
    }

    private RowMapper<Board> boardRowMapper(){
        return (rs, rowNum) -> {
            Board board = new Board();
            board.setBoardId(rs.getInt("Board_id"));
            board.setBoardName(rs.getString("Board_name"));
            board.setBoardIntro(rs.getString("Board_intro"));
            return board;
        };
    }
}
