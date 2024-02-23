package com.elice.crud_project.access.repository;


import com.elice.crud_project.access.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();
    Optional<User> findById(Long id);

    User save(User user);

    void delete(User user );
}