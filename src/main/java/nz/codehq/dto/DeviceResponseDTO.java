package nz.codehq.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class DeviceResponseDTO {
    private String deviceId;
    private float latitude;
    private float longitude;
    @JsonProperty("data")
    private List<WeatherResponseDTO> listWeatherRequestDTO = new ArrayList<>();
}
