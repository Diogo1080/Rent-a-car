package com.school.mindera.rentacar.persistence.repository;

import com.school.mindera.rentacar.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The user Repository for database persistence
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

    /**
     * Login Query for finding if given credentials are right
     *
     * @param email    the email given
     * @param password the password given
     * @return {@link UserEntity}
     */
    @Query(
            value = "SELECT *\n" +
                    "FROM users t1\n" +
                    "WHERE t1.email = :email\n" +
                    "AND t1.password = :password",
            nativeQuery = true)
    Optional<UserEntity> findMatchingCredentials(
            @Param("email") String email,
            @Param("password") String password);

    /**
     * Get user by email
     * @param email
     * @return
     */
    Optional<UserEntity> findByEmail(String email);
}
