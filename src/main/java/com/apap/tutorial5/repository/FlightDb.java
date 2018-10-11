package com.apap.tutorial5.repository;

import com.apap.tutorial5.model.FlightModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightDb extends JpaRepository<FlightModel, Long> {
    @Query("SELECT f FROM FlightModel f WHERE f.flightNumber = ?1")
    List<FlightModel> findFlightByName(String flightNumber);

    FlightModel findByFlightNumber(String flightNumber);
}