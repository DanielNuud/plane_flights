package daniel.nuud.plane_flights.repository;

import daniel.nuud.plane_flights.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("""
        SELECT f
        FROM Flight f
        JOIN f.departureAirport d
        JOIN f.arrivalAirport a
        WHERE d.city = :depCity
          AND a.city = :arrCity
          AND f.flightDate >= :startOfDay
          AND f.flightDate < :startOfNextDay
    """)
    List<Flight> findFlightsByCityAndDate(
            @Param("depCity") String departureCity,
            @Param("arrCity") String arrivalCity,
            @Param("startOfDay") Instant startOfDay,
            @Param("startOfNextDay") Instant startOfNextDay
    );
}
