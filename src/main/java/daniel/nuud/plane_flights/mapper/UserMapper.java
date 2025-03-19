package daniel.nuud.plane_flights.mapper;

import daniel.nuud.plane_flights.dto.RegistrationRequest;
import daniel.nuud.plane_flights.dto.UserDTO;
import daniel.nuud.plane_flights.model.User;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class UserMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    public abstract UserDTO toDTO(User user);

    public abstract User fromDTO(UserDTO userDTO);

    public abstract User toEntity(RegistrationRequest registrationRequest);

    @BeforeMapping
    protected void encodePassword(RegistrationRequest registrationRequest, @MappingTarget User user) {
        if (registrationRequest.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        }
    }
}
