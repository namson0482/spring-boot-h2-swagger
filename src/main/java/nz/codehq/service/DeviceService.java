package nz.codehq.service;

import nz.codehq.dto.DeviceRequestDTO;
import nz.codehq.dto.DeviceResponseDTO;
import nz.codehq.entities.Device;

import java.text.ParseException;
import java.util.List;

public interface DeviceService {
    Device addNewDevice(DeviceRequestDTO deviceRequestDTO);
    List<DeviceResponseDTO> getAllDevices() ;
    DeviceResponseDTO getByDeviceId(String deviceId, String startTimestamp, String endTimestamp);
}
