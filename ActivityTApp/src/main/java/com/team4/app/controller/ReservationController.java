package com.team4.app.controller;

import com.team4.app.model.Reservation;
import com.team4.app.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ReservationController {

    private ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService)
    {
        this.reservationService=reservationService;
    }

    @RequestMapping(value="/supervisor", method = RequestMethod.GET)
    public String showPanel(Model model)
    {
        List<Reservation> reservations = (List<Reservation>) reservationService.findAll();
        model.addAttribute("reservations", reservations);
        model.addAttribute("bla", "bla");

        return "panels/supervisor";
    }

    @ResponseBody
    @RequestMapping(value = "/routes/all", method = RequestMethod.GET)
    public List<Reservation> getRoutes() {
        return (List<Reservation>) reservationService.findAll();
    }
}