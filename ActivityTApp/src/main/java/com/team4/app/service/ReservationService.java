package com.team4.app.service;

import com.team4.app.model.Hotel;
import com.team4.app.model.Reservation;
import com.team4.app.model.User;
import com.team4.app.repository.HotelRepository;
import com.team4.app.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    @Autowired
    ReservationRepository reservationsRepository;

    public void save(Reservation reservations) {
        reservationsRepository.save(reservations);
    }
    public List<Reservation> findAll()
    {
        return (List<Reservation>) reservationsRepository.findAll();

    }

}
