package daniel.nuud.plane_flights.repository;

import daniel.nuud.plane_flights.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
