package com.team4.app.repository;

import com.team4.app.model.Hotels;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelsRepository extends CrudRepository<Hotels, Long> {


}
