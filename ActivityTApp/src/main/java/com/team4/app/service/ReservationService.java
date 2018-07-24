package com.team4.app.service;

import com.team4.app.model.Hotel;
import com.team4.app.model.Reservation;
import com.team4.app.model.User;
import com.team4.app.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    ReservationRepository reservationsRepository;

    public Optional<Reservation> findById(Long id) {
        return reservationsRepository.findById(id);
    }

    public Iterable<Reservation> findByUser(User user) {
        return reservationsRepository.findByUser(user);
    }

    public Iterable<Reservation> findByHotel(Hotel hotel) {
        return reservationsRepository.findByHotel(hotel);
    }

    public Iterable<Reservation> findAll(){
        return reservationsRepository.findAll();
    }

    public void save(Reservation reservations) {
        reservationsRepository.save(reservations);
    }

    public void delete(Reservation reservation) {
        reservationsRepository.delete(reservation);
    }
}
