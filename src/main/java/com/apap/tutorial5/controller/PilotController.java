package com.apap.tutorial5.controller;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PilotController {
    @Autowired
    private PilotService pilotService;

    @RequestMapping("/")
    private String home() {
        return "home";
    }

    @RequestMapping(value = "/pilot/add", method = RequestMethod.GET)
    private String add(Model model) {
        model.addAttribute("pilot", new PilotModel());
        return "addPilot";
    }

    @RequestMapping(value = "/pilot/add", method = RequestMethod.POST)
    private String addPilotSubmit(@ModelAttribute PilotModel pilot) {
        pilotService.addPilot(pilot);
        return "add";
    }

    @RequestMapping(value = "/pilot/view", method = RequestMethod.GET)
    private String view(@RequestParam("licenseNumber") String licenseNumber, Model model) {
        PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
        List<FlightModel> flight = pilot.getPilotFlight();

        model.addAttribute("pilot", pilot);
        model.addAttribute("flight", flight);

        return "view-pilot";
    }

    @RequestMapping(value = "/pilot/delete/{licenseNumber}", method = RequestMethod.GET)
    private String delete(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
        PilotModel pilotModel = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
        pilotService.deletePilot(pilotModel);
        return "delete";
    }

    @RequestMapping(value = "/pilot/update/{licenseNumber}", method = RequestMethod.GET)
    private String update(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
        PilotModel pilotModel = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
        model.addAttribute("pilot", pilotModel);
        return "updatePilot";
    }

    @RequestMapping(value = "/pilot/update/{licenseNumber}", method = RequestMethod.POST)
    private String update(@PathVariable(value = "licenseNumber") String licenseNumber, @RequestParam("name") String name, @RequestParam("flyHour") Integer flyHour) {
        PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
        pilotService.updatePilot(pilot, name, flyHour);
        return "update";
    }
}
