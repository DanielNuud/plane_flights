package daniel.nuud.plane_flights.service;

import daniel.nuud.plane_flights.dto.RegistrationRequest;
import daniel.nuud.plane_flights.dto.UserDTO;
import daniel.nuud.plane_flights.exception.NotFoundException;
import daniel.nuud.plane_flights.mapper.UserMapper;
import daniel.nuud.plane_flights.model.User;
import daniel.nuud.plane_flights.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createUser(RegistrationRequest registrationRequest) {
        var user = new User();
        user.setFirstName(registrationRequest.getFirstName());
        user.setLastName(registrationRequest.getLastName());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setRole("USER");
        userRepository.save(user);
    }

    public UserDTO getUserById(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id: " + id + " not found"));
        return userMapper.toDTO(user);
    }

    public User findByUserName(String login) {
        return userRepository.findByEmail(login).orElseThrow(() -> new NotFoundException("User" + login + " not found"));
    }
}
