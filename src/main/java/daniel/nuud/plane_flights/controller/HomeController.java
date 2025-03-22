package daniel.nuud.plane_flights.controller;

import daniel.nuud.plane_flights.dto.api.AirportDataDTO;
import daniel.nuud.plane_flights.dto.api.FlightDataDTO;
import daniel.nuud.plane_flights.service.AirportApiService;
import daniel.nuud.plane_flights.service.FlightApiService;
import daniel.nuud.plane_flights.service.FlightService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private FlightApiService flightApiService;

    @Autowired
    private AirportApiService airportApiService;

    @GetMapping("/")
    public String home(Model model) {
        List<FlightDataDTO> flights = flightApiService.getRealTimeFlights();
        model.addAttribute("flights", flights);
        return "home";
    }
}
