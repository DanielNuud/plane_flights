package daniel.nuud.plane_flights.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class DepartureDTO {

    @JsonProperty("airport")
    private String departureAirport;

    @JsonProperty("iata")
    private String iataCode;

    @JsonProperty("scheduled")
    private String departureTime;

    @JsonProperty("icao")
    private String icaoCode;
}
