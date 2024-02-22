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
        String sql = "insert into board (User_id, Board_name, Board_intro) values (?, ?, ?)";
        int Board_id = jdbcTemplate.update(sql, board.getUser().getUserId(), board.getBoard_name(), board.getBoard_intro());
        board.setBoard_id((int)Board_id);
        return board;
    }

    public int update(Board board){
        String sql = "update board set Board_name = ? , Board_intro = ?  where Board_id = ?";
        int result = jdbcTemplate.update(sql, board.getBoard_name(), board.getBoard_intro(), board.getBoard_id());
        return result;
    }

    public List<Board> findAll(){
        String sql = "select * from board";
        return jdbcTemplate.query(sql, boardRowMapper());
    }

    public Optional<Board> findById(int Board_id) {
        String sql = "select * from board where Board_id = ?";
        return jdbcTemplate.query(sql, boardRowMapper(), Board_id).stream().findAny();
    }

    public void deleteById(int Board_id){
        String sql = "DELETE FROM Board WHERE Board_id = ?";
        jdbcTemplate.update(sql, Board_id);
    }

    private RowMapper<Board> boardRowMapper(){
        return (rs, rowNum) -> {
            Board board = new Board();
            board.setBoard_id(rs.getInt("Board_id"));
            board.setBoard_name(rs.getString("Board_name"));
            board.setBoard_intro(rs.getString("Board_intro"));
            return board;
        };
    }

}
