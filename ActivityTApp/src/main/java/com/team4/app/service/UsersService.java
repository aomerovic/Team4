package com.team4.app.service;

import com.team4.app.model.Users;
import com.team4.app.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    UsersRepository usersRepository;

    public void save(Users users) {
        usersRepository.save(users);
    }
}
