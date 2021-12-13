package nz.codehq.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeatherRequestDTO {
    private String deviceId;
    private String humidity;
    @JsonProperty("temperature")
    private TemperatureRequestDTO temperatureDTO;
}
