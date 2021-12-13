package nz.codehq.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class WeatherResponseDTO {
    private String deviceId;
    private String humidity;
    private String timestamp;
    @JsonProperty("temperature")
    private List<TemperatureResponseDTO> listTemperatureResponseDTO = new ArrayList<>();
}
