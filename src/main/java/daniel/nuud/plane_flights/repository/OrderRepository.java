package daniel.nuud.plane_flights.repository;

import daniel.nuud.plane_flights.model.Order;
import daniel.nuud.plane_flights.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser(User user);
}
