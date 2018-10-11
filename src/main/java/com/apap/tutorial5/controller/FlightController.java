package com.apap.tutorial5.controller;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.service.FlightService;
import com.apap.tutorial5.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@Controller
public class FlightController {
    @Autowired
    private FlightService flightService;

    @Autowired
    private PilotService pilotService;

    @RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
    private String add(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
        FlightModel flight = new FlightModel();
        PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
        flight.setPilot(pilot);

        model.addAttribute("flight", flight);
        return "addFlight";
    }

    @RequestMapping(value = "/flight/add", method = RequestMethod.POST)
    private String addFlightSubmit(@ModelAttribute FlightModel flight) {
        flightService.addFlight(flight);
        return "add";
    }

    @RequestMapping(value = "/flight/delete/{id}", method = RequestMethod.GET)
    private String deleteFlight(@PathVariable(value = "id") long id, Model model) {
        FlightModel flight = flightService.findFlightById(id);
        flightService.deleteFlight(flight);
        return "delete";
    }

    @RequestMapping(value = "/flight/update/{id}", method = RequestMethod.GET)
    private String update(@PathVariable(value = "id") long id, Model model) {
        FlightModel flightModel = flightService.findFlightById(id);
        model.addAttribute("flight", flightModel);
        return "updateFlight";
    }

    @RequestMapping(value = "/flight/update/{id}", method = RequestMethod.POST)
    private String updateFlightSubmit(@PathVariable(value = "id") long id,
                                      @RequestParam("flightNumber") String flightNumber,
                                      @RequestParam("origin") String origin,
                                      @RequestParam("destination") String destination,
                                      @RequestParam("time") Date time, Model model) {
        FlightModel flight = flightService.findFlightById(id);
        flightService.updateFlight(flight, flightNumber, origin, destination, time);
        return "update";
    }

    @RequestMapping(value = "/flight/viewall", method = RequestMethod.GET)
    private String viewAllFlight(Model model) {
        List<FlightModel> flights = flightService.getAllFlights();

        model.addAttribute("flights", flights);
        return "view-flight";
    }
}