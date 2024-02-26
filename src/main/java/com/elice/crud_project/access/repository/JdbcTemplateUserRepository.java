package com.elice.crud_project.access.repository;

import com.elice.crud_project.access.entity.User;
import com.elice.crud_project.board.entity.Board;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
@Repository
public class JdbcTemplateUserRepository {

    private final JdbcTemplate jdbcTemplate;
    public JdbcTemplateUserRepository(DataSource dataSource) { jdbcTemplate = new JdbcTemplate(dataSource); }

    public User save(User user){
        String sql = "INSERT INTO user(login_id, password) VALUES (?,?)";
        int userId = jdbcTemplate.update(sql, user.getLoginId(), user.getPassword());
        user.setUserId((int)userId);
        return user;
    }

    public int update(User user){
        String sql = "UPDATE user set login_id = ?, password = ? where user_id = ?";
        int result = jdbcTemplate.update(sql, user.getLoginId(), user.getPassword(), user.getUserId());
        return result;
    }

    public List<User> findAll(){
        String sql = "select * from user";
        return jdbcTemplate.query(sql, userRowMapper());
    }

    public Optional<User> findByLoginIdANDPassword(String loginId,String password){
        String sql = "SELECT user_id FROM user WHERE login_id = ? AND password = ?";
        return  jdbcTemplate.query(sql, userRowMapper(), loginId, password).stream().findAny();
    }

    public void deleteById(int userId) {
        String sql = "DELETE FROM user WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
    }

    //로그아웃

    private RowMapper<User> userRowMapper(){
        return (rs, rowNum) -> {
            User user = new User();
            user.setUserId(rs.getInt("user_id"));
            user.setLoginId(rs.getString("login_id"));
            user.setPassword(rs.getString("password"));
            return user;
        };
    }
}
