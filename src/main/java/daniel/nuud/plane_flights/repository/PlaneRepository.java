package daniel.nuud.plane_flights.repository;

import daniel.nuud.plane_flights.model.Plane;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaneRepository extends JpaRepository<Plane, Long> {
}
