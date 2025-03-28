package daniel.nuud.plane_flights.controller;

import daniel.nuud.plane_flights.dto.api.FlightDataDTO;
import daniel.nuud.plane_flights.model.Airport;
import daniel.nuud.plane_flights.model.Flight;
import daniel.nuud.plane_flights.repository.FlightRepository;
import daniel.nuud.plane_flights.service.AirportService;
import daniel.nuud.plane_flights.service.api.FlightApiService;
import daniel.nuud.plane_flights.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private FlightApiService flightApiService;

    @Autowired
    private AirportService airportService;

    @Autowired
    private FlightService flightService;

    @Autowired
    private FlightRepository flightRepository;

    @GetMapping("/")
    public String home(@RequestParam(value = "sort", required = false) String sort, Model model) {
//        List<FlightDataDTO> flights = flightApiService.getRealTimeFlights();
//        flightService.getFlights();
        List<Airport> airports = airportService.getAllAirports();

//        flightService.updateFlightAssociations();
        List<String> departureCities = flightService.findDistinctDepartureCities();
        List<String> arrivalCities = flightService.findDistinctArrivalCities();

        List<Flight> flightList = flightService.getSortedList(sort);

        model.addAttribute("departureCities", departureCities);
        model.addAttribute("arrivalCities", arrivalCities);
        model.addAttribute("flights", flightList);
        model.addAttribute("airports", airports);
        return "main";
    }

    @GetMapping("/flights")
    public String getSearchedFlights(
            @RequestParam(required = false) String departure,
            @RequestParam(required = false) String arrival,
            @RequestParam(required = false) String departureDate,
            @RequestParam(required = false, defaultValue = "1") int passengers,
            Model model
    ) {

        List<Flight> flights = flightService.searchFlights(departure, arrival, departureDate, passengers);

        model.addAttribute("flights", flights);

        model.addAttribute("selectedDeparture", departure);
        model.addAttribute("selectedArrival", arrival);
        model.addAttribute("selectedDate", departureDate);
        model.addAttribute("selectedPassengers", passengers);

        model.addAttribute("departureCities", flightService.findDistinctDepartureCities());

        return "main";
    }
}
