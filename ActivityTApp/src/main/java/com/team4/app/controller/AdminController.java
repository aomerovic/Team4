package com.team4.app.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.team4.app.model.Hotel;
import com.team4.app.model.Reservation;
import com.team4.app.model.User;
import com.team4.app.service.HotelService;
import com.team4.app.service.ReservationService;
import com.team4.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.management.relation.RelationService;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {

    private UserService userService;
    private HotelService hotelService;
    private ReservationService reservationService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AdminController(UserService userService,
                           HotelService hotelService,
                           ReservationService reservationService,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.hotelService = hotelService;
        this.reservationService = reservationService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String showAdminPanel(Model model) {

        User user = new User();
        Iterable<User> users = userService.findAll();

        Hotel hotel = new Hotel();
        Iterable<Hotel> hotels = hotelService.findAll();

        model.addAttribute("user", user);
        model.addAttribute("users", users);
        model.addAttribute("hotel", hotel);
        model.addAttribute("hotels", hotels);

        return "panels/admin";
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public String addNewUser(Model model, @Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        model.addAttribute("user", new User());
        model.addAttribute("hotel", new Hotel());

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Something went wrong.");
            bindingResult.reject("Error");
            return "redirect:/admin";

        }
        else {
            if (userService.findByUsername(user.getUsername()) != null){
                redirectAttributes.addFlashAttribute("error", "Invalid username!");
                bindingResult.reject("Username exists");
                return "redirect:/admin";
            }
            user.setRole("ROLE_USER");
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userService.save(user);

            redirectAttributes.addFlashAttribute("success", "User added!");
            return "redirect:/admin";

        }
    }

    @RequestMapping(value = "/user/delete/{id}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){

        if (userService.findById(id).isPresent()) {

            User user = userService.findById(id).get();

            List<Reservation> reservations = (List<Reservation>) reservationService.findByUser(user);
            for (Reservation reservation : reservations) {
                reservationService.delete(reservation);
            }

            userService.delete(user);
            redirectAttributes.addFlashAttribute("successdelete", "User deleted!");
        }
        else {
            redirectAttributes.addFlashAttribute("errordelete", "Something went wrong.");
        }

        return "redirect:/admin";
    }

    @RequestMapping(value = "/hotel/add", method = RequestMethod.POST)
    public String addNewHotel(Model model, @Valid Hotel hotel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        model.addAttribute("user", new User());
        model.addAttribute("hotel", new Hotel());

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Something went wrong.");
            bindingResult.reject("Error");
            return "redirect:/admin";

        }
        else {
            hotelService.save(hotel);
            redirectAttributes.addFlashAttribute("success", "Hotel added!");
            return "redirect:/admin";
        }
    }

    @RequestMapping(value = "/hotel/delete/{id}", method = RequestMethod.GET)
    public String deleteHotel(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){

        if (hotelService.findById(id).isPresent()) {

            Hotel hotel = hotelService.findById(id).get();

            List<Reservation> reservations = (List<Reservation>) reservationService.findByHotel(hotel);
            for (Reservation reservation : reservations) {
                reservationService.delete(reservation);
            }

            hotelService.delete(hotel);
            redirectAttributes.addFlashAttribute("successdelete", "Hotel deleted!");
        }
        else {
            redirectAttributes.addFlashAttribute("errordelete", "Something went wrong.");
        }

        return "redirect:/admin";
    }
}
