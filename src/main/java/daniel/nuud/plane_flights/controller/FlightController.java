package daniel.nuud.plane_flights.controller;

import daniel.nuud.plane_flights.model.Flight;
import daniel.nuud.plane_flights.service.UserService;
import daniel.nuud.plane_flights.service.api.FlightApiService;
import daniel.nuud.plane_flights.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class FlightController {

    @Autowired
    private FlightApiService flightApiService;

    @Autowired
    private FlightService flightService;

    @Autowired
    private UserService userService;

//    @GetMapping("/flights")
//    public String getFlights(Model model) {
//        List<FlightDataDTO> flights = flightApiService.getRealTimeFlights();
//        model.addAttribute("flights", flights);
//        return "flights";
//    }

    @GetMapping("/flights")
    public String searchFlights(@RequestParam("departure")  String departure,
                                @RequestParam("departureDate") String departureDate,
                                Model model) {

        List<Flight> flights = flightService.getSearchedFlights(departure, departureDate);
        model.addAttribute("flights", flights);
        return "flights";
    }

    @GetMapping("/flight/{id}")
    public String showFlight(@PathVariable("id") Long id,
                             @AuthenticationPrincipal UserDetails userDetails,
                             Model model) {
        var user = userService.findByUserName(userDetails.getUsername());
        Flight flight = flightService.getFlightById(id);
        model.addAttribute("flight", flight);
        model.addAttribute("user", user);
        return "flight-details";
    }



}
