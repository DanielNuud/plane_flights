package daniel.nuud.plane_flights.service;

import daniel.nuud.plane_flights.dto.RegistrationRequest;
import daniel.nuud.plane_flights.dto.UserDTO;
import daniel.nuud.plane_flights.exception.NotFoundException;
import daniel.nuud.plane_flights.exception.ResourceNotFoundException;
import daniel.nuud.plane_flights.mapper.UserMapper;
import daniel.nuud.plane_flights.model.User;
import daniel.nuud.plane_flights.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserDTO createUser(RegistrationRequest registrationRequest) {
        var user = userMapper.toEntity(registrationRequest);
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    public UserDTO getUserById(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id: " + id + " not found"));
        return userMapper.toDTO(user);
    }


}
