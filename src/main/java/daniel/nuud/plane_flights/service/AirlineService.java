package daniel.nuud.plane_flights.service;

import daniel.nuud.plane_flights.dto.api.AirlineDataDTO;
import daniel.nuud.plane_flights.model.Airline;
import daniel.nuud.plane_flights.repository.AirlineRepository;
import daniel.nuud.plane_flights.service.api.AirlineApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AirlineService {

    @Autowired
    private AirlineRepository airlineRepository;

    @Autowired
    private AirlineApiService airlineApiService;

    public Airline getOrFetch(String icaoCode) {
        if (icaoCode == null || icaoCode.length() < 3) {
            log.warn("Skipping airline fetch — invalid ICAO code '{}'", icaoCode);
            return null;
        }

        return airlineRepository.findByIcao(icaoCode)
                .orElseGet(() -> {
                    List<AirlineDataDTO> data = airlineApiService.getAirlineData(icaoCode);
                    if (!data.isEmpty()) {
                        AirlineDataDTO dto = data.get(0);
                        Airline airline = new Airline();
                        airline.setIcao(dto.getIcao());
                        airline.setName(dto.getName());
                        airline.setIata(dto.getIata());
                        airline.setLogoUrl(dto.getLogo_url());
                        return airlineRepository.save(airline);
                    }
                    return null;
                });
    }
}
