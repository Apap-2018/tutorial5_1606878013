package com.apap.tutorial5.controller;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.service.FlightService;
import com.apap.tutorial5.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FlightController {
    @Autowired
    private FlightService flightService;

    @Autowired
    private PilotService pilotService;

    private List<FlightModel> temp;

    @RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
    private String add(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
        FlightModel flight = new FlightModel();
        PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
        flight.setPilot(pilot);
        temp = pilot.getPilotFlight();
        pilot.setPilotFlight(new ArrayList<FlightModel>());
        pilot.addPilotFlight(flight);

        model.addAttribute("flight", flight);
        return "addFlight";
    }

    @RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.POST, params= {"save"})
    private String addFlightSubmit(@ModelAttribute PilotModel pilot) {
        for(FlightModel flight: temp) {
            pilot.addPilotFlight(flight);
        }
        for(FlightModel flight : pilot.getPilotFlight()) {
            flight.setPilot(pilot);
            flightService.addFlight(flight);
        }
        return "add";
    }

    @RequestMapping(value="/flight/add/{licenseNumber}", params={"addRow"}, method = RequestMethod.POST)
    public String addRow(@ModelAttribute PilotModel pilot, BindingResult bindingResult, Model model) {
        pilot.getPilotFlight().add(new FlightModel());
        model.addAttribute("pilot", pilot);
        return "addFlight";
    }

    @RequestMapping(value="/flight/add/{licenseNumber}", method = RequestMethod.POST, params={"removeRow"})
    public String removeRow(@ModelAttribute PilotModel pilot, final BindingResult bindingResult, final HttpServletRequest req, Model model) {
        final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
        pilot.getPilotFlight().remove(rowId.intValue());

        model.addAttribute("pilot", pilot);
        return "addFlight";
    }

    @RequestMapping(value = "/flight/delete/", method = RequestMethod.POST)
    private String deleteFlight(@ModelAttribute PilotModel pilot, Model model) {
        for(FlightModel flight : pilot.getPilotFlight()) {
            flightService.deleteFlight(flight);
        }
        return "delete";
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

    @RequestMapping(value = "/flight/view", method = RequestMethod.GET)
    private String viewFlightByName(@RequestParam("flightNumber") String flightNumber, Model model) {
        List<FlightModel> flight = flightService.findFlightByName(flightNumber);
        model.addAttribute("flight", flight);
        return "view-flight";
    }

    @RequestMapping(value = "/flight/viewall", method = RequestMethod.GET)
    private String viewAllFlight(Model model) {
        List<FlightModel> flights = flightService.getAllFlights();

        model.addAttribute("flights", flights);
        return "view-flight";
    }
}