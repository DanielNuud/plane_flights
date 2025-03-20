package daniel.nuud.plane_flights.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class FlightDataDTO {
    @JsonProperty("flight_date")
    private String flightDate;

    @JsonProperty("departure")
    private DepartureDTO departure;

    @JsonProperty("arrival")
    private ArrivalDTO arrival;

    @JsonProperty("airline")
    private AirlineDTO airline;

    @JsonProperty("flight")
    private FlightDTO flight;

}
