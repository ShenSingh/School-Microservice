package com.zenveus.authservice.service;

import com.zenveus.authservice.entity.User;
import com.zenveus.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User addUser(User user) {
        return userRepository.save(user);
    }
    public User fetchUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    public User fetchUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public User fetchUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
