package nz.codehq.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TemperatureResponseDTO {
    private String unit;
    private float value;
}
