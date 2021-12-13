package nz.codehq.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nz.codehq.dto.DeviceRequestDTO;
import nz.codehq.dto.DeviceResponseDTO;
import nz.codehq.entities.Device;
import nz.codehq.service.DeviceService;
import nz.codehq.utils.CustomException;
import nz.codehq.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RequestMapping("/api/devices")
@Api(value = "devices", description = "Device Information")
@RestController
public class DeviceController {

    @Autowired
    DeviceService deviceService;

    @ApiOperation(value = "list all Devices", response = Iterable.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })

    @GetMapping()
    public ResponseEntity<Object>  getDevices() {
        List<DeviceResponseDTO> deviceResponseDTOList = deviceService.getAllDevices();
        return new ResponseEntity<>(deviceResponseDTOList, HttpStatus.OK);
    }

    @ApiOperation(value = "Add a Device")
    @PostMapping
    public ResponseEntity<Object> addDevice(@RequestBody DeviceRequestDTO deviceRequestDTO){
        try {
            deviceService.addNewDevice(deviceRequestDTO);
            return new ResponseEntity<Object>("Device saved successfully", HttpStatus.OK);
        } catch (CustomException e) {
            return ResponseMessage.responseError(e);
        }
    }

    @ApiOperation(value = "Get an Device with deviceId", response = Device.class)
    @GetMapping("/{deviceId}")
    public ResponseEntity<Object> getDeviceById(@PathVariable String deviceId
            , @RequestParam(value = "endTimestamp", required = false) String endTimestamp
            , @RequestParam(value = "startTimestamp", required = false) String startTimestamp) {
        try {
            DeviceResponseDTO deviceResponseDTO = deviceService.getByDeviceId(deviceId, startTimestamp, endTimestamp);
            return new ResponseEntity<>(deviceResponseDTO, HttpStatus.OK);
        } catch (CustomException e) {
            return ResponseMessage.responseError((CustomException)e);
        }
    }
}
