package com.team4.app.service;

import com.team4.app.model.User;
import com.team4.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository usersRepository;

    public void save(User users) {
        usersRepository.save(users);
    }
}