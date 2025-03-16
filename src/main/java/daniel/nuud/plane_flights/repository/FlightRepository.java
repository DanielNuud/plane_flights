package daniel.nuud.plane_flights.repository;

import daniel.nuud.plane_flights.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
}
