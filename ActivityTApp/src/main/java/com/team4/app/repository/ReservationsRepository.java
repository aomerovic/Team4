package com.team4.app.repository;

import com.team4.app.model.Reservations;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationsRepository extends CrudRepository<Reservations, Long> {


}
