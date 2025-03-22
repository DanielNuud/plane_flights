package daniel.nuud.plane_flights.controller;

import daniel.nuud.plane_flights.dto.api.FlightDataDTO;
import daniel.nuud.plane_flights.model.Airport;
import daniel.nuud.plane_flights.service.AirportService;
import daniel.nuud.plane_flights.service.api.AirportApiService;
import daniel.nuud.plane_flights.service.api.FlightApiService;
import daniel.nuud.plane_flights.service.FlightService;
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
    private AirportService airportService;

    @Autowired
    private FlightService flightService;

    @GetMapping("/")
    public String home(Model model) {
        List<FlightDataDTO> flights = flightApiService.getRealTimeFlights();
        flightService.getFlights();
        List<Airport> airports = airportService.getAllAirports();
        model.addAttribute("flights", flights);
        model.addAttribute("airports", airports);
        return "home";
    }
}
