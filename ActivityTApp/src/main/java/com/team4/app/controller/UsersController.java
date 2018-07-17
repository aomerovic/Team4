package com.team4.app.controller;

import org.hibernate.mapping.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsersController {

    public UsersController() {

    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginView() {
        return "auth/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String postLogin(Model model, @RequestParam("params")Map params) {

        return "";
    }
}
