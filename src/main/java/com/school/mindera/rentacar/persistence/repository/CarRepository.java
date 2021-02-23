package com.school.mindera.rentacar.persistence.repository;

import com.school.mindera.rentacar.persistence.entity.CarEntity;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<CarEntity, Long> {
}
