package daniel.nuud.plane_flights.repository;

import daniel.nuud.plane_flights.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
