package daniel.nuud.plane_flights.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "airports")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Airport {

    @Id
    @Column(name = "iata_code", unique = true)
    private String iataCode;

    @Column(unique = true)
    private String icao;

    private String airportName;

    private String countryName;

    private String region;

    private String city;
}
