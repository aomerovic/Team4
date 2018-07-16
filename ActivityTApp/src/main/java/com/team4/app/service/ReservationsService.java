package com.team4.app.service;

import com.team4.app.model.Reservations;
import com.team4.app.repository.ReservationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationsService {
    @Autowired
    ReservationsRepository reservationsRepository;

    public void save(Reservations reservations) {
        reservationsRepository.save(reservations);
    }
}
