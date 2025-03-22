package daniel.nuud.plane_flights.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Instant flightDate;
    private String departureAirport;
    private Instant departureTime;
    private String arrivalAirport;
    private Instant arrivalTime;
    private String airlineName;
    private String flightNumber;
    private String arrivalIataCode;
    private String departureIataCode;
}
