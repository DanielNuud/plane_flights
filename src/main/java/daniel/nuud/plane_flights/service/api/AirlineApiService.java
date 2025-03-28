package daniel.nuud.plane_flights.service.api;

import daniel.nuud.plane_flights.dto.api.AirlineDataDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Slf4j
@Service
public class AirlineApiService {

    private WebClient webClient;

    public AirlineApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://api.api-ninjas.com/v1")
                .defaultHeader("X-Api-Key", "5DMvXnlXITZHf9vUrTzZgQ==GiXzboVVLVFb1oTC")
                .build();
    }

    public List<AirlineDataDTO> getAirlineData(String icaoCode) {
        if (icaoCode == null || icaoCode.length() < 3) {
            log.warn("Skipping airline fetch — invalid ICAO code '{}'", icaoCode);
            return null;
        }

        log.info("Fetching airline with icaoCode = {}", icaoCode);
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/airlines")
                        .queryParam("icao", icaoCode)
                        .build())
                .retrieve()
                .bodyToFlux(AirlineDataDTO.class)
                .collectList()
                .block();

    }
}
