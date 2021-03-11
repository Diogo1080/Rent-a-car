package com.school.mindera.rentacar.persistence.repository;

import com.school.mindera.rentacar.persistence.entity.CarEntity;
import com.school.mindera.rentacar.persistence.entity.RentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentRepository extends CrudRepository<RentEntity, Long> {
    List<RentEntity> findAllByCarEntity (CarEntity carEntity);
}
