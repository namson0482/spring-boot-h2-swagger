package nz.codehq.service.impl;

import lombok.extern.slf4j.Slf4j;
import nz.codehq.config.mapper.DeviceMapper;
import nz.codehq.dto.DeviceRequestDTO;
import nz.codehq.dto.DeviceResponseDTO;
import nz.codehq.entities.Device;
import nz.codehq.repository.DeviceRepository;
import nz.codehq.service.DeviceService;
import nz.codehq.utils.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    DeviceMapper deviceMapper;

    @Override
    public Device addNewDevice(DeviceRequestDTO deviceRequestDTO) {
        if(deviceRepository.findByDeviceId(deviceRequestDTO.getDeviceId()) != null) {
            throw new CustomException("2000","Device Id exists");
        }
        if(deviceRequestDTO.getDeviceId() == null || deviceRequestDTO.getDeviceId().equalsIgnoreCase("")) {
            throw new CustomException("2001","Device Id is invalid");
        }
        Device device = deviceMapper.toNewDevice(deviceRequestDTO);
        device = deviceRepository.save(device);
        return device;
    }

    @Override
    public List<DeviceResponseDTO> getAllDevices() {
        List<Device> deviceList = deviceRepository.findAll();
        List<DeviceResponseDTO> deviceResponseDTOList = new ArrayList<>();
        for(Device device:deviceList) {
            deviceResponseDTOList.add(deviceMapper.toDeviceResponseDTO(device, null, null));
        }
        return deviceResponseDTOList;
    }

    @Override
    public DeviceResponseDTO getByDeviceId(String deviceId, String startTimestamp, String endTimestamp) {
        Device device = deviceRepository.findByDeviceId(deviceId);

        if(device == null) {
            throw new CustomException("2000","Device Id not exists");
        }
        DeviceResponseDTO deviceResponseDTO = deviceMapper.toDeviceResponseDTO(device, startTimestamp, endTimestamp);
        return deviceResponseDTO;
    }
}
