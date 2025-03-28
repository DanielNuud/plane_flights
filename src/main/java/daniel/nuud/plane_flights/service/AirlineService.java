package daniel.nuud.plane_flights.service;

import daniel.nuud.plane_flights.dto.api.AirlineDataDTO;
import daniel.nuud.plane_flights.model.Airline;
import daniel.nuud.plane_flights.repository.AirlineRepository;
import daniel.nuud.plane_flights.service.api.AirlineApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirlineService {

    @Autowired
    private AirlineRepository airlineRepository;

    @Autowired
    private AirlineApiService airlineApiService;

    public Airline getOrFetch(String icao) {
        return airlineRepository.findById(icao).orElseGet(() -> {
            List<AirlineDataDTO> response = airlineApiService.getAirlineData(icao);
            if (response != null && !response.isEmpty()) {
                AirlineDataDTO dto = response.get(0);
                Airline airline = new Airline();
                airline.setIata(dto.getIata());
                airline.setIcao(dto.getIcao());
                airline.setName(dto.getName());
                airline.setLogoUrl(dto.getLogo_url());
                System.out.printf("ICAO: %s | IATA: %s | Airline: %s%n", icao, dto.getIata(), dto.getName());

                return airlineRepository.save(airline);
            }
            return airlineRepository.findById("UNKNOWN").orElseGet(() -> {
                Airline unknownAirline = new Airline();
                unknownAirline.setIcao("UNKNOWN");
                unknownAirline.setName("Unknown Airline");
                unknownAirline.setLogoUrl("/images/nologo.png");
                return airlineRepository.save(unknownAirline);
            });
        });
    }
}
