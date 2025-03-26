package daniel.nuud.plane_flights.controller.api;

import daniel.nuud.plane_flights.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FlightApiController {

    @Autowired
    private FlightRepository flightRepository;

    @GetMapping("/arrival")
    public List<String> getArrivalCities(@RequestParam("departure") String departureCity) {
        return flightRepository.findDistinctArrivalCitiesByDeparture(departureCity);
    }

}

