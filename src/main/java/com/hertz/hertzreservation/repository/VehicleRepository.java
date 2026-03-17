package com.hertz.hertzreservation.repository;

import com.hertz.hertzreservation.entity.VehicleEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


//  Primary Key Type: String (Mongo ObjectId)

@Repository
public interface VehicleRepository extends MongoRepository<VehicleEntity, String> {

}