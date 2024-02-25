package com.elice.crud_project.access.service;

import com.elice.crud_project.access.entity.User;
import com.elice.crud_project.access.repository.JdbcTemplateUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {

    private final JdbcTemplateUserRepository userRepository;

    public UserService(JdbcTemplateUserRepository userRepository){
        this.userRepository = userRepository;
    }
    public List<User> getAllUsers(){ return  userRepository.findAll(); }

    public User getUserByLoginIdANDPassword(String loginId, String password) {
        return userRepository.findByLoginIdANDPassword(loginId, password).orElse(null);
    }

    public int saveUser(User user){
        User result = userRepository.save(user);
        return result.getUserId();
    }

    public void updateUser(User user){ userRepository.update(user); }

    public void deleteUserById(int userId){userRepository.deleteById(userId);}
}
