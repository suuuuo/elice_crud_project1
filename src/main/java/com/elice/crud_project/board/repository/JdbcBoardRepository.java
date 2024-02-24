package com.elice.crud_project.board.repository;

import com.elice.crud_project.board.entity.Board;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

@Repository
public class JdbcBoardRepository {
    private final DataSource dataSource;
    public JdbcBoardRepository(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public Board save(Board board) throws SQLException {
        String sql = "INSERT INTO Board(user_id, board_name, board_intro) VALUES(?,?,?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DataSourceUtils.getConnection(dataSource);
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, String.valueOf(board.getUser().getUserId()));
            pstmt.setString(2, board.getBoardName());
            pstmt.setString(3, board.getBoardIntro());

            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                board.setBoardId(rs.getInt(1));
            } else {
                throw new SQLException("id 조회 실패");
            }
            return board;
        }catch (Exception e){
            throw new IllegalStateException(e);
        }finally { close(conn, pstmt, rs); }
    }

    public Optional<Board> findById(int Board_id){
        String sql = "SELECT * FROM Board where Board_id = ? ";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DataSourceUtils.getConnection(dataSource);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, String.valueOf(Board_id));

            rs = pstmt.executeQuery();
            if(rs.next()){
                Board board  =new Board();
                board.setBoardId(rs.getInt("User_id"));
                board.setBoardName(rs.getString("Board_name"));
                board.setBoardIntro(rs.getString("Board_intro"));
                return Optional.of(board);
            }
            return Optional.empty();
        }catch (Exception e){
            throw new IllegalStateException(e);
        }finally {
            close(conn, pstmt, rs);
        }
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
    {
        try {
            if (rs != null) { rs.close();}
        } catch (SQLException e) { e.printStackTrace(); }

        try {
            if (pstmt != null) { pstmt.close(); }
        } catch (SQLException e) { e.printStackTrace(); }

        try {
            if (conn != null) { close(conn); }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
