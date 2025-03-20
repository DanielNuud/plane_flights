package daniel.nuud.plane_flights.service;

import daniel.nuud.plane_flights.dto.api.FlightDataDTO;
import daniel.nuud.plane_flights.dto.api.FlightsResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;

@Service
public class FlightApiService {

    private WebClient webClient;

    @Value("${aviationstack.access-key}")
    private String accessKey;

    public FlightApiService(WebClient.Builder webClientBuilder,
                            @Value("${aviationstack.base-url}") String baseUrl) {
        this.webClient = webClientBuilder
                .baseUrl(baseUrl)
                .build();
    }

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
        }

        System.out.println("Size of data: " + response.getData().size());
        System.out.println("First flight date: " + response.getData().get(0).getFlightDate());

        return response != null ? response.getData() : Collections.emptyList();
    }

}
