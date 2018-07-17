package com.team4.app.controller;

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

    private HotelService hotelsService;

    @Autowired
    public UsersController(HotelService hotelsService) {
        this.hotelsService = hotelsService;
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


    /*@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginView() {
        return "auth/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String postLogin(Model model, @RequestParam("params")Map params) {

        return "";
    }*/

}
