package com.hertz.hertzreservation.service;

import com.hertz.hertzreservation.dto.VehicleRequestDTO;
import com.hertz.hertzreservation.dto.VehicleResponseDTO;
import com.hertz.hertzreservation.entity.VehicleEntity;
import com.hertz.hertzreservation.repository.VehicleRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements VehicleService {

    private static final Logger logger =
            LoggerFactory.getLogger(VehicleServiceImpl.class);

    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public VehicleResponseDTO createVehicle(VehicleRequestDTO request) {

        logger.info("Starting vehicle creation process. Category={}, Size={}",
                request.getCategory(), request.getSize());

        validateDateRange(request.getAvailableFrom(), request.getAvailableTo());

        VehicleEntity entity = mapToEntity(request);

        logger.debug("Saving vehicle entity to MongoDB");

        VehicleEntity saved = vehicleRepository.save(entity);

        logger.info("Vehicle created successfully with id={}", saved.getId());

        return mapToResponse(saved);
    }

    @Override
    public VehicleResponseDTO updateVehicle(String id, VehicleRequestDTO request) {

        logger.info("Updating vehicle with id={}", id);

        VehicleEntity existing = vehicleRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Vehicle not found for update, id={}", id);
                    return new RuntimeException("Vehicle not found");
                });

        validateDateRange(request.getAvailableFrom(), request.getAvailableTo());

        logger.debug("Applying updates to vehicle entity id={}", id);

        existing.setCategory(request.getCategory());
        existing.setSize(request.getSize());
        existing.setRentalType(request.getRentalType());
        existing.setDriveType(request.getDriveType());
        existing.setDurationType(request.getDurationType());
        existing.setPrice(request.getPrice());
        existing.setAvailableFrom(request.getAvailableFrom());
        existing.setAvailableTo(request.getAvailableTo());

        VehicleEntity updated = vehicleRepository.save(existing);

        logger.info("Vehicle updated successfully with id={}", updated.getId());

        return mapToResponse(updated);
    }

    @Override
    public void deleteVehicle(String id) {

        logger.info("Deleting vehicle with id={}", id);

        if (!vehicleRepository.existsById(id)) {
            logger.error("Vehicle deletion failed. Vehicle not found id={}", id);
            throw new RuntimeException("Vehicle not found");
        }

        vehicleRepository.deleteById(id);

        logger.info("Vehicle deleted successfully with id={}", id);
    }

    @Override
    public VehicleResponseDTO getVehicleById(String id) {

        logger.info("Fetching vehicle with id={}", id);

        VehicleEntity entity = vehicleRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Vehicle not found with id={}", id);
                    return new RuntimeException("Vehicle not found");
                });

        logger.debug("Vehicle fetched successfully from MongoDB id={}", id);

        return mapToResponse(entity);
    }

    @Override
    public List<VehicleResponseDTO> getAllVehicles() {

        logger.info("Fetching all vehicles from database");

        List<VehicleEntity> vehicleEntities = vehicleRepository.findAll();

        List<VehicleResponseDTO> vehicles = vehicleEntities
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        logger.info("Total vehicles fetched={}", vehicles.size());

        return vehicles;
    }

    private void validateDateRange(Instant from, Instant to) {

        logger.debug("Validating vehicle availability date range");

        if (from.isAfter(to)) {
            logger.error("Invalid vehicle date range. availableFrom={} availableTo={}", from, to);
            throw new IllegalArgumentException(
                    "availableFrom must be before availableTo");
        }
    }

    private VehicleEntity mapToEntity(VehicleRequestDTO request) {

        logger.debug("Mapping VehicleRequestDTO to VehicleEntity");

        VehicleEntity entity = new VehicleEntity();

        entity.setCategory(request.getCategory());
        entity.setSize(request.getSize());
        entity.setRentalType(request.getRentalType());
        entity.setDriveType(request.getDriveType());
        entity.setDurationType(request.getDurationType());
        entity.setPrice(request.getPrice());
        entity.setAvailableFrom(request.getAvailableFrom());
        entity.setAvailableTo(request.getAvailableTo());

        return entity;
    }

    private VehicleResponseDTO mapToResponse(VehicleEntity entity) {

        logger.debug("Mapping VehicleEntity to VehicleResponseDTO id={}", entity.getId());

        return new VehicleResponseDTO(
                entity.getId(),
                entity.getCategory(),
                entity.getSize(),
                entity.getRentalType(),
                entity.getDriveType(),
                entity.getDurationType(),
                entity.getPrice(),
                entity.getAvailableFrom(),
                entity.getAvailableTo()
        );
    }
}