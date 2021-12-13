package nz.codehq.controller;

import lombok.extern.slf4j.Slf4j;
import nz.codehq.dto.*;
import nz.codehq.entities.Device;
import nz.codehq.entities.Temperature;
import nz.codehq.entities.Weather;
import nz.codehq.service.DeviceService;
import nz.codehq.utils.CustomErrorResponse;
import nz.codehq.utils.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class DeviceControllerTest {

    @InjectMocks
    DeviceController underTest;

    @Mock
    DeviceService deviceService;

    DeviceResponseDTO deviceResponseDTO;

    List<WeatherResponseDTO> listWeatherRequestDTO;

    List<TemperatureResponseDTO> listTemperatureResponseDTO;

    Device device;

    DeviceRequestDTO deviceRequestDTO;

    private void initDeviceObject() {
        device = Device.builder()
                .deviceId("abc123")
                .latitude(35f)
                .longitude(34f)
                .build();

        TemperatureRequestDTO temperatureRequestDTO = TemperatureRequestDTO.builder()
                .unit("C")
                .value(25)
                .build();


        WeatherRequestDTO weatherRequestDTO = WeatherRequestDTO.builder()
                .deviceId("abc23")
                .humidity("213")
                .temperatureDTO(temperatureRequestDTO)
                .build();

        deviceRequestDTO = DeviceRequestDTO.builder()
                .deviceId("abc123")
                .latitude(35f)
                .longitude(34f)
                .weatherRequestDTO(weatherRequestDTO)
                .build();

        List<Temperature> listTemperature = new ArrayList<>();

        Temperature temperature = Temperature.builder()
                .unit("C")
                .value(25)
                .build();
        listTemperature.add(temperature);

        Weather weather = Weather.builder()
                .device(device)
                .humidity("213")
                .temperature(listTemperature)
                .build();
        List<Weather> weatherList = new ArrayList<>();
        weatherList.add(weather);
        device.setListWeather(weatherList);
    }

    @BeforeEach
    void setUp() {
        initDeviceObject();
        TemperatureResponseDTO temperatureResponseDTO = TemperatureResponseDTO.builder()
                .unit("C")
                .value(23f)
                .build();
        listTemperatureResponseDTO = new ArrayList<>();
        listTemperatureResponseDTO.add(temperatureResponseDTO);

        WeatherResponseDTO weatherResponseDTO = WeatherResponseDTO.builder()
                .deviceId("xyz123")
                .humidity("123")
                .listTemperatureResponseDTO(listTemperatureResponseDTO)
                .build();

        listWeatherRequestDTO = new ArrayList<>();
        listWeatherRequestDTO.add(weatherResponseDTO);

        deviceResponseDTO = DeviceResponseDTO.builder()
                .deviceId("xyz123")
                .latitude(41.25f)
                .longitude(-120.9762f)
                .listWeatherRequestDTO(listWeatherRequestDTO)
                .build();
    }

    @DisplayName("testGetDeviceById: test successfully")
    @Test
    void testGetDeviceById() {

        when(deviceService.getByDeviceId(anyString(), isNull(), isNull()))
                .thenReturn(deviceResponseDTO);
        underTest.getDeviceById("xyz123", null, null);
        verify(deviceService, times(1)).getByDeviceId(anyString(), isNull(), isNull());
    }

    @DisplayName("testGetDeviceByIdNotExists: deviceId is not exists")
    @Test
    void testGetDeviceByIdNotExists() {

        CustomException notExistsException = new CustomException("2000","Device Id not exists");
        when(deviceService.getByDeviceId(anyString(), isNull(), isNull())).thenThrow(notExistsException);
        ResponseEntity<Object> obj =  underTest.getDeviceById("xyz123", null, null);
        assertEquals("2000", ((CustomErrorResponse)obj.getBody()).getErrorCode());
        verify(deviceService, times(1)).getByDeviceId(anyString(), isNull(), isNull());
    }

    @DisplayName("testAddDeviceSuccess: add new device successfully")
    @Test
    void testAddDeviceSuccess() {

        when(deviceService.addNewDevice(any(DeviceRequestDTO.class))).thenReturn(device);
        underTest.addDevice(deviceRequestDTO);
        verify(deviceService, times(1)).addNewDevice(any(DeviceRequestDTO.class));
    }

    @DisplayName("testAddDeviceException: add new device with exception")
    @Test
    void testAddDeviceException() {

        CustomException deviceIdIsInvalidException = new CustomException("2001","Device Id is invalid");
        when(deviceService.addNewDevice(any(DeviceRequestDTO.class))).thenThrow(deviceIdIsInvalidException);
        underTest.addDevice(deviceRequestDTO);
        verify(deviceService, times(1)).addNewDevice(any(DeviceRequestDTO.class));
    }

    @DisplayName("testGetDevicesSuccess: get devices list successfully ")
    @Test
    void testGetDevicesSuccess() {
        List<TemperatureResponseDTO> listTemperatureResponseDTO = new ArrayList<>();
        TemperatureResponseDTO temperatureResponseDTO = TemperatureResponseDTO.builder()
                .unit("C")
                .value(123f)
                .build();
        listTemperatureResponseDTO.add(temperatureResponseDTO);

        List<WeatherResponseDTO> listWeatherRequestDTO = new ArrayList<>();
        WeatherResponseDTO weatherResponseDTO = WeatherResponseDTO.builder()
                .deviceId("abc123")
                .humidity("123")
                .timestamp("2021-12-12")
                .listTemperatureResponseDTO(listTemperatureResponseDTO)
                .build();
        listWeatherRequestDTO.add(weatherResponseDTO);

        List<DeviceResponseDTO> deviceResponseDTOList = new ArrayList<>();
        DeviceResponseDTO deviceResponseDTO = DeviceResponseDTO.builder()
                .deviceId("abc123")
                .latitude(123f)
                .longitude(322f)
                .listWeatherRequestDTO(listWeatherRequestDTO)
                .build();
        deviceResponseDTOList.add(deviceResponseDTO);

        when(deviceService.getAllDevices()).thenReturn(deviceResponseDTOList);
        underTest.getDevices();
        verify(deviceService, times(1)).getAllDevices();
    }
}