package daniel.nuud.plane_flights.controller;

import daniel.nuud.plane_flights.model.Order;
import daniel.nuud.plane_flights.model.User;
import daniel.nuud.plane_flights.service.OrderService;
import daniel.nuud.plane_flights.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/flight/{id}/order")
    public String createOrder(
            @PathVariable("id") Long flightId,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("bank") String bank,
            Principal principal
    ) {
        String fullName = firstName + " " + lastName;
        orderService.createOrder(flightId, fullName, email, phone, bank, principal.getName());
        return "redirect:/confirmation";
    }

    @GetMapping("/confirmation")
    public String confirmation() {
        return "confirmation";
    }

    @GetMapping("/orders")
    public String viewUserOrders(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUserName(username);

        List<Order> userOrders = orderService.getOrdersByUser(user.getEmail());
        model.addAttribute("orders", userOrders);

        return "orders";
    }
}
