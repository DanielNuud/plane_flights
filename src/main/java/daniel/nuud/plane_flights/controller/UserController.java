package daniel.nuud.plane_flights.controller;

import daniel.nuud.plane_flights.dto.LoginRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @PostMapping("/login")
    public String login(@RequestParam(name = "loginRequired", required = false) final Boolean loginRequired,
                        @RequestParam(name = "loginError", required = false) final Boolean loginError,
                        final Model model) {
        model.addAttribute("authentication", new LoginRequest());
        return "authentication/login";
    }
}
