package daniel.nuud.plane_flights;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PlaneFlightsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlaneFlightsApplication.class, args);
    }

}
