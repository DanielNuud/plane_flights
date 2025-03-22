package daniel.nuud.plane_flights.service;

import daniel.nuud.plane_flights.dto.api.AirportDataDTO;
import daniel.nuud.plane_flights.model.Airport;
import daniel.nuud.plane_flights.model.Flight;
import daniel.nuud.plane_flights.repository.AirportRepository;
import daniel.nuud.plane_flights.repository.FlightRepository;
import daniel.nuud.plane_flights.service.api.AirportApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FlightService {

    @Autowired
    private AirportApiService airportApiService;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private AirportRepository airportRepository;

    public void getFlights() {
        List<Flight> flights = flightRepository.findAll();

        Set<String> iataCodes = new HashSet<>();

        for (Flight flight : flights) {
            if (flight.getDepartureIataCode() != null) {
                iataCodes.add(flight.getDepartureIataCode());
            }

            if (flight.getArrivalIataCode() != null) {
                iataCodes.add(flight.getArrivalIataCode());
            }
        }

        for (String iataCode : iataCodes) {

            if (!airportRepository.existsByIataCode(iataCode)) {

                List<AirportDataDTO> data = airportApiService.getAirportsData(iataCode);

                if (!data.isEmpty()) {
                    AirportDataDTO airportDataDTO = data.get(0);

                    Airport airport = new Airport();

                    airport.setIataCode(iataCode);
                    airport.setAirportName(airportDataDTO.getAirportName());
                    airport.setCountryName(airportDataDTO.getCountryName());
                    airport.setRegion(airportDataDTO.getRegion());
                    airport.setCity(airportDataDTO.getCity());
                    airportRepository.save(airport);
                }

            }
        }
    }

    public List<Flight> getSearchedFlights(String departureCity,
                                           String arrivalCity,
                                           String dateString) {
        LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Instant startOfDay = localDate.atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant startOfNextDay = localDate.plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant();

        return flightRepository.findFlightsByCityAndDate(departureCity, arrivalCity, startOfDay, startOfNextDay);
    }
}
