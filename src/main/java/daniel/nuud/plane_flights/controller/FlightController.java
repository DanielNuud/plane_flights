package daniel.nuud.plane_flights.controller;

import daniel.nuud.plane_flights.dto.api.FlightDataDTO;
import daniel.nuud.plane_flights.service.FlightApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class FlightController {

    @Autowired
    private FlightApiService flightApiService;

    @GetMapping("/flights")
    public String getFlights(Model model) {
        List<FlightDataDTO> flights = flightApiService.getRealTimeFlights();
        model.addAttribute("flights", flights);
        return "flights";
    }

}
