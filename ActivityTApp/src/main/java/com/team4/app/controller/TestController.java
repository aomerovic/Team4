package com.team4.app.controller;

import com.team4.app.model.Hotels;
import com.team4.app.model.Reservations;
import com.team4.app.model.Users;
import com.team4.app.service.HotelsService;
import com.team4.app.service.ReservationsService;
import com.team4.app.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestController {

    private UsersService usersService;
    private HotelsService hotelsService;
    private ReservationsService reservationsService;

    @Autowired
    public TestController(UsersService usersService,
                          HotelsService hotelsService,
                          ReservationsService reservationsService) {
        this.usersService = usersService;
        this.hotelsService = hotelsService;
        this.reservationsService = reservationsService;
    }
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test() {
        Users user = new Users("ROLE_USER", "John", "Doe", 12.174219, 17.068659);
        Hotels hotel = new Hotels("Test_Hotel", "No description", 7.162866, 18.260039);
        Reservations reservation = new Reservations(user,hotel);
        usersService.save(user);
        hotelsService.save(hotel);
        reservationsService.save(reservation);
    }
}
