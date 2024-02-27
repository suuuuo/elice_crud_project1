package com.elice.crud_project.access.repository;

import com.elice.crud_project.access.entity.User;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
@Repository
public class JdbcUserRepository {
    private final DataSource dataSource;
    public JdbcUserRepository(DataSource dataSource){ this.dataSource = dataSource; }


}

