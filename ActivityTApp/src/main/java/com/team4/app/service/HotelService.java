package com.team4.app.service;

import com.team4.app.model.Hotel;
import com.team4.app.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelService {
    @Autowired
    HotelRepository hotelsRepository;

    public void save(Hotel hotels) {
        hotelsRepository.save(hotels);
    }
}
