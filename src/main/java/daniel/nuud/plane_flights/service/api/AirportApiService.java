package daniel.nuud.plane_flights.service.api;

import daniel.nuud.plane_flights.dto.api.AirportDataDTO;
import daniel.nuud.plane_flights.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class AirportApiService {

    private WebClient webClient;

    @Autowired
    private AirportRepository airportRepository;

    public AirportApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://api.api-ninjas.com/v1")
                .defaultHeader("X-Api-Key", "5DMvXnlXITZHf9vUrTzZgQ==GiXzboVVLVFb1oTC")
                .build();
    }

    public List<AirportDataDTO> getAirportsData(String iataCode) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/airports")
                        .queryParam("iata", iataCode)
                        .build())
                .retrieve()
                .bodyToFlux(AirportDataDTO.class)
                .collectList()
                .block();
    }
}
