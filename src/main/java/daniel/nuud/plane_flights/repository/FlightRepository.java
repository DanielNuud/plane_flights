package daniel.nuud.plane_flights.repository;

import daniel.nuud.plane_flights.model.Airport;
import daniel.nuud.plane_flights.model.Flight;
import org.springframework.data.domain.Sort;
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
        WHERE d.city = :depCity
          AND f.flightDate >= :startOfDay
          AND f.flightDate < :startOfNextDay
    """)
    List<Flight> findFlightsByCityAndDate(
            @Param("depCity") String departureCity,
            @Param("startOfDay") Instant startOfDay,
            @Param("startOfNextDay") Instant startOfNextDay
    );

    @Query("SELECT DISTINCT f.departureAirport.city FROM Flight f WHERE f.departureAirport.city IS NOT NULL")
    List<String> findDistinctDepartureCities();

    @Query("SELECT DISTINCT f.arrivalAirport.city FROM Flight f WHERE f.arrivalAirport.city IS NOT NULL")
    List<String> findDistinctArrivalCities();

    @Query("SELECT DISTINCT f.arrivalAirport.city " +
            "FROM Flight f " +
            "WHERE f.departureAirport.city = :depCity " +
            "AND f.arrivalAirport.city IS NOT NULL")
    List<String> findDistinctArrivalCitiesByDeparture(@Param("depCity") String departureCity);

    List<Flight> findAllByFlightDate(Instant flightDate);

    List<Flight> findAllByArrivalAirport(Airport arrivalAirport);

    @Query("SELECT f FROM Flight f JOIN FETCH f.departureAirport JOIN FETCH f.arrivalAirport")
    List<Flight> findAllWithAirports();

    @Query("SELECT f FROM Flight f LEFT JOIN FETCH f.departureAirport LEFT JOIN FETCH f.arrivalAirport LEFT JOIN FETCH f.airline")
    List<Flight> findAllWithAirportsSorted(Sort sort);
}
