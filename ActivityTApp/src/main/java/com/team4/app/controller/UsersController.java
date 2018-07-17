package com.team4.app.controller;


import com.team4.app.model.User;
import com.team4.app.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class UsersController {
    private UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }



    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginView() {
        return "auth/login";
    }


}
