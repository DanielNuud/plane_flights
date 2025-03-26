package daniel.nuud.plane_flights.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AirlineDTO {
    @JsonProperty("name")
    private String airlineName;

    @JsonProperty("iata")
    private String iata;

    @JsonProperty("icao")
    private String icao;

}
