package com.team4.app.service;

import com.team4.app.model.Hotel;
import com.team4.app.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HotelService {
    @Autowired
    HotelRepository hotelsRepository;

    public Optional<Hotel> findById(Long id) {
        return hotelsRepository.findById(id);
    }
    public Iterable<Hotel> findAll() {
        return hotelsRepository.findAll();
    }

    public void save(Hotel hotels) {
        hotelsRepository.save(hotels);
    }

    public void delete(Hotel hotel) {
        hotelsRepository.delete(hotel);
    }

}
