package com.team4.app.controller;

import com.team4.app.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
public class UsersController {
    private UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/default", method = RequestMethod.GET)
    public String redirectToPanel(Model model, Authentication authentication) {
        if (authentication.getAuthorities().toString().contains("ROLE_ADMIN")) {
            System.out.println("==== ADMIN");
            return "redirect:/admin";
        }
        else if (authentication.getAuthorities().toString().contains("ROLE_USER")) {
            System.out.println("==== USER");
            return "redirect:/save1";
        } else if (authentication.getAuthorities().toString().contains("ROLE_SUPERVISOR")) {
            System.out.println("==== SUPERVISOR");
            return "redirect:/supervisor";
        }

        System.out.println("==== ROLE FAILED");
        return "/login";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginPage(Model model, @RequestParam(name = "error", required = false) String error){

        if (error != null) {
            model.addAttribute("errorMsg", error.replace('-', ' '));
        }
        return "auth/login";
    }
}