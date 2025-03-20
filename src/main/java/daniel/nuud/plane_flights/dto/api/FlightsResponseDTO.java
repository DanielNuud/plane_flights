package daniel.nuud.plane_flights.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties("pagination")
public class FlightsResponseDTO {
    private List<FlightDataDTO> data;
}