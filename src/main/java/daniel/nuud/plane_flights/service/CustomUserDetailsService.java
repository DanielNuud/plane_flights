package daniel.nuud.plane_flights.service;

import daniel.nuud.plane_flights.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User byLogin = userService.findByUserName(username);

        if (byLogin == null) {
            throw new UsernameNotFoundException("User " + username + " not found");
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(byLogin.getEmail())
                .password(byLogin.getPassword())
                .roles("USER")
                .build();
    }
}
