package com.team4.app.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.team4.app.model.Hotel;
import com.team4.app.model.Reservation;
import com.team4.app.model.User;
import com.team4.app.service.EmailService;
import com.team4.app.service.HotelService;
import com.team4.app.service.ReservationService;
import com.team4.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class AdminController {

    private UserService userService;
    private HotelService hotelService;
    private ReservationService reservationService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private EmailService emailService;

    @Autowired
    public AdminController(UserService userService,
                           HotelService hotelService,
                           ReservationService reservationService,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           EmailService emailService) {
        this.userService = userService;
        this.hotelService = hotelService;
        this.reservationService = reservationService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailService = emailService;
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
    public String addNewUser(Model model, @Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        model.addAttribute("user", new User());
        model.addAttribute("hotel", new Hotel());

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorUserDelete", "Something went wrong.");
            bindingResult.reject("Error");
            return "redirect:/admin";

        }
        else {
            if (userService.findByUsername(user.getEmail()) != null){
                redirectAttributes.addFlashAttribute("errorUserDelete", "User with that email already exists!");
                bindingResult.reject("email");
                return "redirect:/admin";
            }
            user.setRole("ROLE_SUPERVISOR");

            user.setEnabled(false);
            user.setPasswordResetToken(userService.getNewToken());
            user.setConfirmAccountToken(userService.getNewToken());
            user.setReactivateAccountToken(userService.getNewToken());

            userService.save(user);

            String appUrl = request.getScheme() + "://" + request.getServerName();
            SimpleMailMessage registrationEmail = new SimpleMailMessage();
            registrationEmail.setTo(user.getEmail());
            registrationEmail.setSubject("Confirm your email");
            registrationEmail.setText("To activate your account, please click on the link below:\n"
                    + appUrl + ":8080/confirm-user-account?token=" + user.getConfirmAccountToken());

            emailService.sendEmail(registrationEmail);

            redirectAttributes.addFlashAttribute("successUserDelete", "User added!");
            return "redirect:/admin";

        }
    }

    @RequestMapping(value = "/edit/user/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User getUser(@PathVariable("id") Long id, Model model) {
        User user;
        if (userService.findById(id).isPresent()){
            user = userService.findById(id).get();
        }
        else {
            user = new User();
        }

        model.addAttribute("user", user);
        return user;
    }


    @RequestMapping(value = "/user/edit", method = RequestMethod.POST)
    public String editUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        Long id = user.getId();
        User existingUser = userService.findById(id).get();
        if (existingUser == null) {
            redirectAttributes.addFlashAttribute("errorUserDelete", "Something went wrong.");
            return "redirect:/admin";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorUserDelete", "Something went wrong.");
            bindingResult.reject("user");
            return "redirect:/admin";
        }
        else {
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setUsername(user.getUsername());
            existingUser.setLatitude(user.getLatitude());
            existingUser.setLongitude(user.getLongitude());

            if (user.getPassword() != null) {
                existingUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            }

            userService.save(existingUser);

            redirectAttributes.addFlashAttribute("successUserDelete", "User updated.");
            return "redirect:/admin";
        }

    }

    @RequestMapping(value = "/user/delete/{id}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {

        if (userService.findById(id).isPresent()) {

            User user = userService.findById(id).get();

            List<Reservation> reservations = (List<Reservation>) reservationService.findByUser(user);
            for (Reservation reservation : reservations) {
                reservationService.delete(reservation);
            }

            userService.delete(user);
            redirectAttributes.addFlashAttribute("successUserDelete", "User deleted!");
        } else {
            redirectAttributes.addFlashAttribute("errorUserDelete", "Something went wrong.");
        }

        return "redirect:/admin";
    }

    @RequestMapping(value = "/hotel/add", method = RequestMethod.POST)
    public String addNewHotel(Model model, @Valid Hotel hotel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        model.addAttribute("user", new User());
        model.addAttribute("hotel", new Hotel());

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorHotelMsg", "Something went wrong.");
            bindingResult.reject("Error");
            return "redirect:/admin";

        }
        else {
            hotelService.save(hotel);
            redirectAttributes.addFlashAttribute("successHotelMsg", "Hotel added!");
            return "redirect:/admin";
        }
    }

    @RequestMapping(value = "/edit/hotel/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Hotel getHotel(@PathVariable("id") Long id, Model model) {
        Hotel hotel;
        if (hotelService.findById(id).isPresent()){
            hotel = hotelService.findById(id).get();
        }
        else {
            hotel = new Hotel();
        }

        model.addAttribute("hotel", hotel);
        return hotel;
    }


    @RequestMapping(value = "/hotel/edit", method = RequestMethod.POST)
    public String editHotel(@Valid @ModelAttribute("hotel") Hotel hotel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        Long id = hotel.getId();
        Hotel existingHotel = hotelService.findById(id).get();
        if (existingHotel == null) {
            redirectAttributes.addFlashAttribute("errorHotelMsg", "Something went wrong.");
            return "redirect:/admin";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorHotelMsg", "Something went wrong.");
            bindingResult.reject("hotel");
            return "redirect:/admin";
        }
        else {
            existingHotel.setHotelName(hotel.getHotelName());
            existingHotel.setHotelDescription(hotel.getHotelDescription());
            existingHotel.setLatitude(hotel.getLatitude());
            existingHotel.setLongitude(hotel.getLongitude());

            hotelService.save(existingHotel);

            redirectAttributes.addFlashAttribute("successHotelMsg", "Hotel updated.");
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
            redirectAttributes.addFlashAttribute("successHotelMsg", "Hotel deleted!");
        }
        else {
            redirectAttributes.addFlashAttribute("errorHotelMsg", "Something went wrong.");
        }

        return "redirect:/admin";
    }
}