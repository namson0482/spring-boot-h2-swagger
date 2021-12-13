package nz.codehq.config.mapper;

import nz.codehq.dto.DeviceRequestDTO;
import nz.codehq.dto.DeviceResponseDTO;
import nz.codehq.dto.TemperatureResponseDTO;
import nz.codehq.dto.WeatherResponseDTO;
import nz.codehq.entities.Device;
import nz.codehq.entities.Temperature;
import nz.codehq.entities.Weather;
import nz.codehq.utils.CommonUtil;
import org.mapstruct.Mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring")
public interface DeviceMapper {

    default Device toNewDevice(DeviceRequestDTO deviceRequestDTO) {
        Device device = Device.builder()
                .deviceId(deviceRequestDTO.getDeviceId().trim())
                .latitude(deviceRequestDTO.getLatitude())
                .longitude(deviceRequestDTO.getLongitude())
                .listWeather(new ArrayList<>())
                .build();

        Weather weather = Weather.builder()
                .device(device)
                .humidity(deviceRequestDTO.getWeatherRequestDTO().getHumidity())
                .temperature(new ArrayList<>())
                .created(new Date())
                .build();

        Temperature temperature = Temperature.builder()
                .unit(deviceRequestDTO.getWeatherRequestDTO().getTemperatureDTO().getUnit())
                .value(deviceRequestDTO.getWeatherRequestDTO().getTemperatureDTO().getValue())
                .weather(weather)
                .build();

        weather.getTemperature().add(temperature);
        device.getListWeather().add(weather);

        return device;
    }

    default DeviceResponseDTO toDeviceResponseDTO(Device device, String startTimestamp, String endTimestamp) {
        try {

            Date startDate = null;
            Date endDate = null;

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(startTimestamp != null && endTimestamp != null) {
                startDate = simpleDateFormat.parse(startTimestamp + " " + "00:00:00");
                endDate = simpleDateFormat.parse(endTimestamp + " " + "23:59:59");
            }

            DeviceResponseDTO deviceRequestDTO = DeviceResponseDTO.builder()
                    .deviceId(device.getDeviceId())
                    .latitude(device.getLatitude())
                    .longitude(device.getLongitude())
                    .build();

            List<WeatherResponseDTO> listWeatherRequestDTO = new ArrayList();
            for(Weather weather: device.getListWeather()) {
                if(startDate !=null && endDate != null
                        && !CommonUtil.isDateInBetweenIncludingEndPoints(startDate, endDate, weather.getCreated())) continue;

                WeatherResponseDTO weatherResponseDTO = WeatherResponseDTO.builder()
                        .deviceId(device.getDeviceId())
                        .humidity(weather.getHumidity())
                        .timestamp(CommonUtil.getDateIso8601(weather.getCreated()))
                        .build();

                List<TemperatureResponseDTO> listTemperatureDTO = new ArrayList<>();
                for(Temperature temperature : weather.getTemperature()) {
                    TemperatureResponseDTO temperatureResponseDTO = TemperatureResponseDTO.builder()
                            .unit(temperature.getUnit())
                            .value(temperature.getValue())
                            .build();
                    listTemperatureDTO.add(temperatureResponseDTO);
                }
                weatherResponseDTO.setListTemperatureResponseDTO(listTemperatureDTO);
                listWeatherRequestDTO.add(weatherResponseDTO);
            }
            deviceRequestDTO.setListWeatherRequestDTO(listWeatherRequestDTO);
            return deviceRequestDTO;
        } catch (ParseException e) {
            return null;
        }
    }
}
