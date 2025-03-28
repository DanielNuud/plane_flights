package daniel.nuud.plane_flights.controller;

import daniel.nuud.plane_flights.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/flight/{flightId}/order")
    public String createOrder(@PathVariable Long flightId,
                              @RequestParam String passengerName,
                              @RequestParam String passengerEmail,
                              @RequestParam String passengerPhone,
                              @RequestParam String seatType,
                              @RequestParam String paymentMethod) {

        orderService.createOrder(flightId, passengerName, passengerEmail, passengerPhone, seatType, paymentMethod);

        return "redirect:/confirmation";
    }

    @GetMapping("/confirmation")
    public String confirmation() {
        return "confirmation";
    }

}
