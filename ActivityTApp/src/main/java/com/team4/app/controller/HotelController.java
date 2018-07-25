package com.team4.app.controller;

import com.team4.app.model.Hotel;
import com.team4.app.model.Reservation;
import com.team4.app.model.User;
import com.team4.app.repository.HotelRepository;
import com.team4.app.repository.ReservationRepository;
import com.team4.app.repository.UserRepository;
import com.team4.app.service.HotelService;
import com.team4.app.service.ReservationService;
import com.team4.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@Controller
public class HotelController {
    private HotelService hotelService;
    private UserService userService;
    private ReservationService reservationService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    private ReservationRepository reservationRepository;
    private HotelRepository hotelRepository;
    @Autowired
    public HotelController(HotelService hotelService,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           ReservationService reservationService,
                           UserRepository userRepository,
                           ReservationRepository reservationRepository,
                           HotelRepository hotelRepository,
                           UserService userService)
    {
        this.reservationService = reservationService;
        this.hotelService = hotelService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.hotelRepository = hotelRepository;
        this.userService = userService;
    }@GetMapping("/save1")
    public String showPage(Principal principal, Model model, @RequestParam(defaultValue = "0") int page)
    {
        User user = new User("ROLE_USER", "John", "Doe", "johndoe", bCryptPasswordEncoder.encode("password123"), 12.174219, 17.068659);
        model.addAttribute("data", hotelService.findAll());
        User user1 = userRepository.findByUsername(principal.getName());
        System.out.println(principal.getName());
        model.addAttribute("reservations", reservationRepository.findAll());
        model.addAttribute("user", user1);
        return "panels/testMap";
    }
   @GetMapping(value = "/save11",produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<List<Reservation>> getAll(){
       List<Reservation> hotelList = reservationService.findAll();
       return ResponseEntity.ok().body(hotelList);
   }

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reservation> book(@RequestBody Reservation reservation)
    {
        Hotel hotel = reservation.getHotel();
        User user = reservation.getUser();
       // User user = new User("ROLE_USER", "John", "Doe", "johndoe", bCryptPasswordEncoder.encode("password123"), 12.174219, 17.068659);
      //  userService.save(user);
        hotel = hotelService.findOne(hotel.getId()).get();
        user = userService.findById(user.getId()).get();
       System.out.println(user);
        Reservation reservation1 = new Reservation(reservation.getUser(), reservation.getHotel());
        Reservation saved = reservationRepository.save(reservation1);
        URI uri = URI.create("http://localhost:8081/save");
        return ResponseEntity.created(uri).body(saved);
    }


}