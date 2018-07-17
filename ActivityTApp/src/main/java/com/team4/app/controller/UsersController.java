package com.team4.app.controller;

import com.team4.app.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class UsersController {
    private UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/default", method = RequestMethod.GET)
    public String redirectToPanel(Authentication authentication) {
        if (authentication.getAuthorities().toString().contains("ROLE_ADMIN")) {
            return "redirect:/admin";
        }
        else if (authentication.getAuthorities().toString().contains("ROLE_USER")) {
            return "redirect:/user";
        } else if (authentication.getAuthorities().toString().contains("ROLE_SUPERVISOR")) {
            return "redirect:/supervisor";
        }

        return "/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginView() {

        return "auth/login";
    }



}
