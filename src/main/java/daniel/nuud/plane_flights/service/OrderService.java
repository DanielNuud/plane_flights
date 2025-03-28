package daniel.nuud.plane_flights.service;

import daniel.nuud.plane_flights.exception.NotFoundException;
import daniel.nuud.plane_flights.exception.ResourceNotFoundException;
import daniel.nuud.plane_flights.model.Flight;
import daniel.nuud.plane_flights.model.Order;
import daniel.nuud.plane_flights.model.User;
import daniel.nuud.plane_flights.repository.FlightRepository;
import daniel.nuud.plane_flights.repository.OrderRepository;
import daniel.nuud.plane_flights.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private UserRepository userRepository;

    public Order createOrder(Long flightId, String passengerName, String passengerEmail,
                             String passengerPhone, String seatType, String paymentMethod) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new ResourceNotFoundException("Flight with id: " + flightId + " not found"));

        User user = userRepository.findByEmail(passengerEmail)
                .orElseThrow(() -> new NotFoundException("User with email: " + passengerEmail + " not found"));

        Order order = new Order();
        order.setFlight(flight);
        order.setUser(user);
        order.setPassengerName(passengerName);
        order.setPassengerEmail(passengerEmail);
        order.setPassengerPhone(passengerPhone);
        order.setSeatType(seatType);
        order.setBank(paymentMethod);

        return orderRepository.save(order);
    }

    public List<Order> getOrdersByUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email: " + email + " not found"));
        return orderRepository.findAllByUser(user);
    }
}
