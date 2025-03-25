package daniel.nuud.plane_flights.repository;

import daniel.nuud.plane_flights.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {
    boolean existsByIataCode(String iataCode);

    Airport findByIataCode(String iataCode);
}