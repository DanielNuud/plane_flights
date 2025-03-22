package daniel.nuud.plane_flights.service;

import daniel.nuud.plane_flights.dto.api.AirportDataDTO;
import daniel.nuud.plane_flights.dto.api.AirportResponseDTO;
import daniel.nuud.plane_flights.model.Airport;
import daniel.nuud.plane_flights.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class AirportApiService {

    private WebClient webClient;

    @Autowired
    private AirportRepository airportRepository;

    private static final String BASE_URL = "https://api.api-ninjas.com/v1";

    @Value("${ninja-api.api-key}")
    private String API_KEY;

    public AirportApiService() {
        this.webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader("X-Api-Key", API_KEY) // ключ в заголовке
                .build();;
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
