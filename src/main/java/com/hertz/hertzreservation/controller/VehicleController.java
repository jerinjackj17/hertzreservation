package com.hertz.hertzreservation.controller;

import com.hertz.hertzreservation.dto.VehicleRequestDTO;
import com.hertz.hertzreservation.dto.VehicleResponseDTO;
import com.hertz.hertzreservation.service.VehicleService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@CrossOrigin(origins = "http://localhost:3000")
public class VehicleController {

    private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    // create vehicle
    @PostMapping
    public ResponseEntity<VehicleResponseDTO> createVehicle(
            @Valid @RequestBody VehicleRequestDTO request) {

        logger.info("Received request to create vehicle");

        VehicleResponseDTO response = vehicleService.createVehicle(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // update vehicle
    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> updateVehicle(
            @PathVariable String id,
            @Valid @RequestBody VehicleRequestDTO request) {

        logger.info("Received request to update vehicle with id={}", id);

        VehicleResponseDTO response = vehicleService.updateVehicle(id, request);

        return ResponseEntity.ok(response);
    }

    // delete vehicle
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(
            @PathVariable String id) {

        logger.info("Received request to delete vehicle with id={}", id);

        vehicleService.deleteVehicle(id);

        return ResponseEntity.noContent().build();
    }

    // get vehicle by id
    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> getVehicleById(
            @PathVariable String id) {

        logger.info("Received request to fetch vehicle with id={}", id);

        VehicleResponseDTO response = vehicleService.getVehicleById(id);

        return ResponseEntity.ok(response);
    }

    // get all vehicles
    @GetMapping
    public ResponseEntity<List<VehicleResponseDTO>> getAllVehicles() {

        logger.info("Received request to fetch all vehicles");

        List<VehicleResponseDTO> vehicles = vehicleService.getAllVehicles();

        return ResponseEntity.ok(vehicles);
    }
}