package daniel.nuud.plane_flights.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightDTO {
    @JsonProperty("number")
    private String flightNumber;
}
