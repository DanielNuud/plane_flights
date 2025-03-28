package daniel.nuud.plane_flights.repository;

import daniel.nuud.plane_flights.model.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, String> {
    Optional<Airline> findByIcao(String icao);
}
