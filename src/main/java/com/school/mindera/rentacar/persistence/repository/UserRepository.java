package com.school.mindera.rentacar.persistence.repository;

import com.school.mindera.rentacar.persistence.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
