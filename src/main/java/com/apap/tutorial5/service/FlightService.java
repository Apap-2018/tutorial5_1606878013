package com.apap.tutorial5.service;

import com.apap.tutorial5.model.FlightModel;

import java.sql.Date;
import java.util.List;

public interface FlightService {
    void addFlight(FlightModel flight);
    FlightModel findFlightById(long id);
    void deleteFlight(FlightModel flight);
    void updateFlight(FlightModel flight, String flightNumber, String origin, String destination, Date time);
    FlightModel getFlightDetailByFlightNumber (String flightNumber);
    List<FlightModel> getAllFlights();
}
