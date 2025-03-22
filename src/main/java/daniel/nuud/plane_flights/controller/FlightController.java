package daniel.nuud.plane_flights.controller;

import daniel.nuud.plane_flights.model.Flight;
import daniel.nuud.plane_flights.service.api.FlightApiService;
import daniel.nuud.plane_flights.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FlightController {

    @Autowired
    private FlightApiService flightApiService;

    @Autowired
    private FlightService flightService;

//    @GetMapping("/flights")
//    public String getFlights(Model model) {
//        List<FlightDataDTO> flights = flightApiService.getRealTimeFlights();
//        model.addAttribute("flights", flights);
//        return "flights";
//    }

    @GetMapping("/flights")
    public String searchFlights(@RequestParam String departure,
                                @RequestParam String arrival,
                                @RequestParam String departureDate,
                                @RequestParam int passengers,
                                Model model) {

        List<Flight> flights = flightService.getSearchedFlights(departure, arrival, departureDate);
        model.addAttribute("flights", flights);
        return "flights";
    }


}
