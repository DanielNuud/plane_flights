package daniel.nuud.plane_flights.service;

import daniel.nuud.plane_flights.dto.api.AirportDataDTO;
import daniel.nuud.plane_flights.exception.ResourceNotFoundException;
import daniel.nuud.plane_flights.model.Airline;
import daniel.nuud.plane_flights.model.Airport;
import daniel.nuud.plane_flights.model.Flight;
import daniel.nuud.plane_flights.repository.AirportRepository;
import daniel.nuud.plane_flights.repository.FlightRepository;
import daniel.nuud.plane_flights.service.api.AirportApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FlightService {

    @Autowired
    private AirportApiService airportApiService;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private AirlineService airlineService;

    public Flight getFlightById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight with id " + id + " not found"));
    }

    public void getFlights() {
        List<Flight> flights = flightRepository.findAll();

        Set<String> icaoCodes = new HashSet<>();

        for (Flight flight : flights) {
            if (flight.getDepartureIataCode() != null) {
                icaoCodes.add(flight.getDepartureIcaoCode());
            }

            if (flight.getArrivalIcaoCode() != null) {
                icaoCodes.add(flight.getArrivalIcaoCode());
            }
        }

        for (String icaoCode : icaoCodes) {

            if (!airportRepository.existsByIcao(icaoCode)) {

                List<AirportDataDTO> data = airportApiService.getAirportsData(icaoCode);

                if (!data.isEmpty()) {
                    AirportDataDTO airportDataDTO = data.get(0);

                    Airport airport = new Airport();

                    airport.setIcao(icaoCode);
                    airport.setIataCode(airportDataDTO.getIataCode());
                    airport.setAirportName(airportDataDTO.getAirportName());
                    airport.setCountryName(airportDataDTO.getCountryName());
                    airport.setRegion(airportDataDTO.getRegion());
                    airport.setCity(airportDataDTO.getCity());
                    airport.setLatitude(airportDataDTO.getLatitude());
                    airport.setLongitude(airportDataDTO.getLongitude());
                    airportRepository.save(airport);
                }

            }
        }
    }

    public List<Flight> getSearchedFlights(String departureCity,
                                           String dateString) {
        LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Instant startOfDay = localDate.atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant startOfNextDay = localDate.plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant();

        return flightRepository.findFlightsByCityAndDate(departureCity, startOfDay, startOfNextDay);
    }

    public List<String> findDistinctDepartureCities() {
        return flightRepository.findDistinctDepartureCities();
    }

    public List<String> findDistinctArrivalCities() {
        return flightRepository.findDistinctArrivalCities();
    }

    public void updateFlightAssociations() {
        List<Flight> flights = flightRepository.findAll();

        for (Flight flight : flights) {

            if (flight.getDepartureIcaoCode() != null && flight.getDepartureAirport() == null) {
                Airport depAirport = airportRepository.findByIcao(flight.getDepartureIcaoCode());
                if (depAirport != null) {
                    flight.setDepartureAirport(depAirport);
                }
            }

            if (flight.getArrivalIcaoCode() != null && flight.getArrivalAirport() == null) {
                Airport arrAirport = airportRepository.findByIcao(flight.getArrivalIcaoCode());
                if (arrAirport != null) {
                    flight.setArrivalAirport(arrAirport);
                }
            }
            flightRepository.save(flight);
        }
    }

//    public List<Flight> getAllFlightsWithAirlines() {
//        List<Flight> flights = flightRepository.findAllWithAirports();
//
//        for (Flight flight : flights) {
//            if (flight.getAirline() == null) {
//                Airline airline = airlineService.getOrFetch(flight.getAirlineIcao());
//                flight.setAirline(airline);
//                flightRepository.save(flight);
//            }
//        }
//
//        return flights;
//    }

    public List<String> getDistinctDepartureCities() {
        return flightRepository.findDistinctDepartureCities();
    }

    public List<Flight> getSortedList (String sort) {
        Sort sorting = Sort.unsorted();

        if ("airlineName".equals(sort))
            sorting = Sort.by("airlineName");
        else if ("departureTime".equals(sort))
            sorting = Sort.by("departureTime");
        else if ("arrivalTime".equals(sort))
            sorting = Sort.by("arrivalTime");

        List<Flight> flights = flightRepository.findAllWithAirportsSorted(sorting);

        for (Flight flight : flights) {
            if (flight.getAirline() == null) {
                Airline airline = airlineService.getOrFetch(flight.getAirlineIcao());
                flight.setAirline(airline);
                flightRepository.save(flight);
            }
        }

        Instant now = Instant.now();
        return flights.stream()
                .filter(flight -> flight.getDepartureTime() != null && flight.getDepartureTime().isAfter(now))
                .collect(Collectors.toList());
    }

    public List<Flight> searchFlights(String departure, String arrival, String dateStr, int passengers) {
        if (departure == null || arrival == null || dateStr == null) {
            return Collections.emptyList();
        }

        LocalDate localDate;
        try {
            localDate = LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            return Collections.emptyList();
        }

        return flightRepository.findAll().stream()
                .filter(f -> f.getDepartureAirport() != null
                        && departure.equalsIgnoreCase(f.getDepartureAirport().getCity()))
                .filter(f -> f.getArrivalAirport() != null
                        && arrival.equalsIgnoreCase(f.getArrivalAirport().getCity()))
                .filter(f -> {
                    if (f.getDepartureTime() == null) return false;
                    LocalDate flightDate = f.getDepartureTime().atZone(ZoneOffset.UTC).toLocalDate();
                    return flightDate.equals(localDate);
                })
                .collect(Collectors.toList());
    }

}
