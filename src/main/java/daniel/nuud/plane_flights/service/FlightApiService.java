package daniel.nuud.plane_flights.service;

import daniel.nuud.plane_flights.dto.api.FlightDataDTO;
import daniel.nuud.plane_flights.dto.api.FlightsResponseDTO;
import daniel.nuud.plane_flights.model.Flight;
import daniel.nuud.plane_flights.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;

@Service
public class FlightApiService {

    private WebClient webClient;

    @Autowired
    private FlightRepository flightRepository;

    @Value("${aviationstack.access-key}")
    private String accessKey;

    public FlightApiService(WebClient.Builder webClientBuilder,
                            @Value("${aviationstack.base-url}") String baseUrl) {
        this.webClient = webClientBuilder
                .baseUrl(baseUrl)
                .build();
    }

    @Scheduled(fixedRate = 100000)
    @Cacheable(value = "flights", key = "'realTimeFlights'")
    public List<FlightDataDTO> getRealTimeFlights() {
        FlightsResponseDTO response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/flights")
                        .queryParam("access_key", accessKey)
                        .build())
                .retrieve()
                .bodyToMono(FlightsResponseDTO.class)
                .block();

        System.out.println("RESPONSE FROM API: " + response);

        if (response != null && response.getData() != null) {
            System.out.println("RECEIVED " + response.getData().size() + " FLIGHTS");

            response.getData().forEach(flightDataDTO -> {
                Flight flight = new Flight();

                if (flightDataDTO.getFlightDate() != null) {
                    flight.setFlightDate(parseDateTime(flightDataDTO.getFlightDate()));
                }

                if (flightDataDTO.getFlight().getFlightNumber() != null) {
                    flight.setFlightNumber(flightDataDTO.getFlight().getFlightNumber());
                }

                if (flightDataDTO.getArrival().getArrivalAirport() != null) {
                    flight.setArrivalAirport(flightDataDTO.getArrival().getArrivalAirport());
                }

                if (flightDataDTO.getDeparture().getDepartureAirport() != null) {
                    flight.setDepartureAirport(flightDataDTO.getDeparture().getDepartureAirport());
                }
                if (flightDataDTO.getDeparture().getDepartureTime() != null) {
                    flight.setDepartureTime(parseDateTime(flightDataDTO.getDeparture().getDepartureTime()));
                }

                if (flightDataDTO.getArrival().getArrivalTime() != null) {
                    flight.setArrivalTime(parseDateTime(flightDataDTO.getArrival().getArrivalTime()));
                }

                if (flightDataDTO.getAirline().getAirlineName() != null) {
                    flight.setAirlineName(flightDataDTO.getAirline().getAirlineName());
                }

                flightRepository.save(flight);
            });
        }

        System.out.println("Size of data: " + response.getData().size());
        System.out.println("First flight date: " + response.getData().get(0).getFlightDate());

        return response.getData();
    }

    public Instant parseDateTime(String dateTimeString) {
        if (dateTimeString == null) {
            return null;
        }
        try {
            return OffsetDateTime.parse(dateTimeString).toInstant();
        } catch (DateTimeParseException e) {
            LocalDate localDate = LocalDate.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return localDate.atStartOfDay(ZoneOffset.UTC).toInstant();
        }
    }

}
