package com.team4.app.service;

import com.team4.app.model.Reservation;
import com.team4.app.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    @Autowired
    ReservationRepository reservationsRepository;
    public Iterable<Reservation> findAll()
    {
        return reservationsRepository.findAll();
    }
    public void save(Reservation reservations) {
        reservationsRepository.save(reservations);
    }
}
