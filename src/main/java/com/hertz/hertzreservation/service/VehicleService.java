package com.hertz.hertzreservation.service;

import com.hertz.hertzreservation.dto.VehicleRequestDTO;
import com.hertz.hertzreservation.dto.VehicleResponseDTO;

import java.util.List;


public interface VehicleService {

    VehicleResponseDTO createVehicle(VehicleRequestDTO request);

    VehicleResponseDTO updateVehicle(String id, VehicleRequestDTO request);

    void deleteVehicle(String id);

    VehicleResponseDTO getVehicleById(String id);

    List<VehicleResponseDTO> getAllVehicles();
    
    void reserveVehicle(String vehicleId, String customerEmail);
}