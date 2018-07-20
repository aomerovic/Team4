package com.team4.app.service;

import com.team4.app.model.User;
import com.team4.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository usersRepository;

    public Optional<User> findById(Long id) {
        return usersRepository.findById(id);
    }
    public User findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }
    public Iterable<User> findAll() {
        return usersRepository.findAll();
    }

    public void save(User users) {
        usersRepository.save(users);
    }

    public void delete(User user) {
        usersRepository.delete(user);
    }
}
