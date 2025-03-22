package daniel.nuud.plane_flights.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ArrivalDTO {
    @JsonProperty("airport")
    private String arrivalAirport;
    @JsonProperty("scheduled")
    private String arrivalTime;
    @JsonProperty("iata")
    private String iataCode;
}
