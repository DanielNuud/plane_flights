package daniel.nuud.plane_flights.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AirportDataDTO {
    @JsonProperty("name")
    private String airportName;

    @JsonProperty("iata")
    private String iataCode;

    @JsonProperty("icao")
    private String icaoCode;

    @JsonProperty("city")
    private String city;

    @JsonProperty("country")
    private String countryName;

    @JsonProperty("region")
    private String region;
}
