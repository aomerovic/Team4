package com.team4.app.controller;

import com.team4.app.model.Hotel;
import com.team4.app.model.Reservation;
import com.team4.app.model.User;
import com.team4.app.service.HotelService;
import com.team4.app.service.ReservationService;
import com.team4.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestController {

    private UserService usersService;
    private HotelService hotelsService;
    private ReservationService reservationsService;


    @Autowired
    public TestController(UserService usersService,
                          HotelService hotelsService,
                          ReservationService reservationsService) {
        this.usersService = usersService;
        this.hotelsService = hotelsService;
        this.reservationsService = reservationsService;
}
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test(Model model) {

        User user = new User("ROLE_USER", "John", "Doe", "johndoe", "password123", 13.546982, 17.123587);
        Hotel hotel = new Hotel("Test_Hotel", "No description", 14.569825, 18.658951);
        Reservation reservation = new Reservation(user,hotel);
        usersService.save(user);
        hotelsService.save(hotel);
        reservationsService.save(reservation);


    }
}
