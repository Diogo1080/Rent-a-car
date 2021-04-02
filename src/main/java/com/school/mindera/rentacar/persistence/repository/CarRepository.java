package com.school.mindera.rentacar.persistence.repository;

import com.school.mindera.rentacar.persistence.entity.CarEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * The Car Repository for database persistence
 */
public interface CarRepository extends PagingAndSortingRepository<CarEntity, Long> {
}
