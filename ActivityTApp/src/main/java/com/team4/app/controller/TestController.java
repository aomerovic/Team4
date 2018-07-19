package com.team4.app.controller;

import com.team4.app.model.Hotel;
import com.team4.app.model.Reservation;
import com.team4.app.model.User;
import com.team4.app.service.HotelService;
import com.team4.app.service.ReservationService;
import com.team4.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestController {

    private UserService usersService;
    private HotelService hotelsService;
    private ReservationService reservationsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public TestController(UserService usersService,
                          HotelService hotelsService,
                          ReservationService reservationsService,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersService = usersService;
        this.hotelsService = hotelsService;
        this.reservationsService = reservationsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test() {
        User user = new User("ROLE_USER", "John", "Doe", "johndoe", bCryptPasswordEncoder.encode("password123"), 12.174219, 17.068659);
        Hotel hotel = new Hotel("Test_Hotel", "No description", 7.162866, 18.260039);
        Reservation reservation = new Reservation(user,hotel);
        usersService.save(user);
        hotelsService.save(hotel);
        reservationsService.save(reservation);
    }

    @RequestMapping(value = "/test-admin", method = RequestMethod.GET)
    public void testAdmin() {
        User user = new User("ROLE_ADMIN", "Jimmy", "Shoe", "jimmy", bCryptPasswordEncoder.encode("password123"), 12.174219, 17.068659);
        usersService.save(user);
    }
}
