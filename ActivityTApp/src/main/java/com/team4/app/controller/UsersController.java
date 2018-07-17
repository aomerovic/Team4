package com.team4.app.controller;


import com.team4.app.service.UserService;
import org.springframework.security.core.Authentication;

import com.team4.app.model.Hotel;
import com.team4.app.model.Reservation;
import com.team4.app.model.User;
import com.team4.app.repository.HotelRepository;
import com.team4.app.service.HotelService;
import com.team4.app.service.ReservationService;
import com.team4.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.Map;

@Controller
public class UsersController {
    private UserService userService;
    private HotelService hotelsService;

    @Autowired
    public UsersController(UserService userService,
                           HotelService hotelsService) {
        this.userService = userService;
        this.hotelsService = hotelsService;
    }


    @RequestMapping(value = "/default", method = RequestMethod.GET)
    public String redirectToPanel(Authentication authentication) {
        if (authentication.getAuthorities().toString().contains("ROLE_ADMIN")) {
            return "redirect:/admin";
        } else if (authentication.getAuthorities().toString().contains("ROLE_USER")) {
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

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public String showUser()
    {
        return "panels/user";
    }
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String SaveHotel(Model model, @RequestParam Map<String,String> params) {
        Hotel hotel = new Hotel();
        hotel.setHotelName(params.get("hotel"));
        hotel.setHotelDescription(params.get("description"));
        hotel.setLongitude(Double.parseDouble(params.get("longitude")));
        hotel.setLatitude(Double.parseDouble(params.get("latitude")));

        hotelsService.save(hotel);
        model.addAttribute("success","Hotel successfully saved");
        return "panels/user";

    }

}
