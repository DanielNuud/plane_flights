package daniel.nuud.plane_flights.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "planes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Plane {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
