package daniel.nuud.plane_flights.service.api;

import daniel.nuud.plane_flights.dto.api.FlightDataDTO;
import daniel.nuud.plane_flights.dto.api.FlightsResponseDTO;
import daniel.nuud.plane_flights.model.Flight;
import daniel.nuud.plane_flights.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    @Cacheable(value = "flights", key = "'realTimeFlights'")
    public List<FlightDataDTO> getRealTimeFlights() {
        FlightsResponseDTO response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/flights")
                        .queryParam("access_key", "f2b8adc7e51d804d0509c993fdd50d33")
                        .queryParam("limit", 25)
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

                if (flightDataDTO.getDeparture().getDepartureTime() != null) {
                    flight.setDepartureTime(parseDateTime(flightDataDTO.getDeparture().getDepartureTime()));
                }

                if (flightDataDTO.getArrival().getArrivalTime() != null) {
                    flight.setArrivalTime(parseDateTime(flightDataDTO.getArrival().getArrivalTime()));
                }

                if (flightDataDTO.getAirline().getAirlineName() != null) {
                    flight.setAirlineName(flightDataDTO.getAirline().getAirlineName());
                }

                if (flightDataDTO.getDeparture().getIataCode() != null) {
                    flight.setDepartureIataCode(flightDataDTO.getDeparture().getIataCode());
                }

                if (flightDataDTO.getArrival().getIataCode() != null) {
                    flight.setArrivalIataCode(flightDataDTO.getArrival().getIataCode());
                }

                if (flightDataDTO.getAirline().getIata() != null) {
                    flight.setAirlineIata(flightDataDTO.getAirline().getIata());
                }

                if (flightDataDTO.getAirline().getIcao() != null) {
                    flight.setAirlineIcao(flightDataDTO.getAirline().getIcao());
                }

                if (flightDataDTO.getDeparture().getIcaoCode() != null) {
                    flight.setDepartureIcaoCode(flightDataDTO.getDeparture().getIcaoCode());
                }
                if (flightDataDTO.getArrival().getIcaoCode() != null) {
                    flight.setArrivalIcaoCode(flightDataDTO.getArrival().getIcaoCode());
                }

                Duration duration = Duration.between(flight.getDepartureTime(), flight.getArrivalTime());
                long hours = duration.toHours();
                long minutes = duration.minusHours(hours).toMinutes();
                flight.setDuration(hours + "h " + minutes + "m");

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
