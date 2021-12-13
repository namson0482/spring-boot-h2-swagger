package nz.codehq.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeviceRequestDTO {
    private String deviceId;
    private float latitude;
    private float longitude;
    @JsonProperty("data")
    private WeatherRequestDTO weatherRequestDTO;
}
