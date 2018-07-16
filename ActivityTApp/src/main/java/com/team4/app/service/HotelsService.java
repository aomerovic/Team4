package com.team4.app.service;

import com.team4.app.model.Hotels;
import com.team4.app.repository.HotelsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelsService {
    @Autowired
    HotelsRepository hotelsRepository;

    public void save(Hotels hotels) {
        hotelsRepository.save(hotels);
    }
}
