package daniel.nuud.plane_flights.dto.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AirlineDataDTO {
    private String iata;
    private String icao;
    private String name;
    private String logo_url;
}
