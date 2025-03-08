package daniel.nuud.plane_flights.mapper;

import daniel.nuud.plane_flights.dto.RegistrationRequest;
import daniel.nuud.plane_flights.dto.UserDTO;
import daniel.nuud.plane_flights.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class UserMapper {


    public abstract UserDTO toDTO(User user);
    public abstract User fromDTO(UserDTO userDTO);
    public abstract User toEntity(RegistrationRequest registrationRequest);
}
