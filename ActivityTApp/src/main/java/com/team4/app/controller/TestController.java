package com.team4.app.controller;

import com.team4.app.model.Hotel;
import com.team4.app.model.Reservation;
import com.team4.app.model.User;
import com.team4.app.service.HotelService;
import com.team4.app.service.ReservationService;
import com.team4.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sun.misc.Request;

@Controller
public class TestController {

    private Reservation reservation;
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
    public void test() {
        User user = new User("ROLE_USER", "John", "Doe", "johndoe", "password123", 12.174219, 17.068659);
        Hotel hotel = new Hotel("Test_Hotel", "No description", 7.162866, 18.260039);
        reservation = new Reservation(user,hotel);
        usersService.save(user);
        hotelsService.save(hotel);
        reservationsService.save(reservation);
    }
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public void user() {
        Double userLongitude = reservation.getUser().getLongitude();
        Double userLatitude = reservation.getUser().getLatitude();
        Double hotelLongitude = reservation.getHotel().getLongitude();
        Double hotelLatitude = reservation.getHotel().getLatitude();

    }
}
