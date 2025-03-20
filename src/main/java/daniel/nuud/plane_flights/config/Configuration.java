package daniel.nuud.plane_flights.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

}
