package com.team4.app.controller;

import com.team4.app.model.Users;
import com.team4.app.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestController {

    private UsersService usersService;

    @Autowired
    public TestController(UsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test() {
        Users user = new Users("ROLE_USER", "John", "Doe", 12.174219, 17.068659);

        usersService.save(user);
    }
}
