package daniel.nuud.plane_flights.controller;

import daniel.nuud.plane_flights.dto.LoginRequest;
import daniel.nuud.plane_flights.dto.RegistrationRequest;
import daniel.nuud.plane_flights.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "authentication/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("registrationRequest", new RegistrationRequest());
        return "authentication/registration";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegistrationRequest registrationRequest) {
        userService.createUser(registrationRequest);
        return "redirect:/login?success";
    }
}
