package com.school.mindera.rentacar.persistence.repository;

import com.school.mindera.rentacar.persistence.entity.RentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * The rent Repository for database persistence
 */
@Repository
public interface RentRepository extends CrudRepository<RentEntity, Long> {

    /**
     * Find if set of dates on a certain car rent intercept with given dates
     * @param carId the car to search for
     * @param beginDate the begin date to compare
     * @param endDate the end date to compare
     * @return True if any record is found, False otherwise
     */
    @Query(
            value = "SELECT CASE WHEN EXISTS( " +
                        "SELECT *\n" +
                        "FROM rent t1\n" +
                        "WHERE t1.car_id = :carId\n" +
                        "AND (\n" +
                        "\t( :beginDate BETWEEN t1.expected_begin_date AND t1.expected_end_date) OR\n" +
                        "\t( :endDate BETWEEN t1.expected_begin_date AND t1.expected_end_date)\n" +
                        ")" +
                    ") then 'TRUE' else 'False' end",
            nativeQuery = true)
    boolean findInterceptionOfDates(
            @Param("carId")long carId,
            @Param("beginDate") Date beginDate,
            @Param("endDate") Date endDate);


    /**
     * Find if set of dates on a certain car rent intercept with given dates excluding itself
     * @param carId the car to search for
     * @param exceptionRentId the rent to exclude from query
     * @param beginDate the begin date to compare
     * @param endDate the end date to compare
     * @return True if any record is found, False otherwise
     */
    @Query(
            value = "SELECT CASE WHEN EXISTS( " +
                    "SELECT *\n" +
                    "FROM rent t1\n" +
                    "WHERE t1.car_id = :carId\n" +
                    "AND (\n" +
                        "(" +
                            "\t( :beginDate BETWEEN t1.expected_begin_date AND t1.expected_end_date) OR\n" +
                            "\t( :endDate BETWEEN t1.expected_begin_date AND t1.expected_end_date)\n" +
                        ") AND (" +
                            "\t t1.id != :exceptionRentId" +
                        ")" +
                    ")" +
                    ") then 'TRUE' else 'False' end",
            nativeQuery = true)
    boolean findInterceptionOfDatesWithExceptionOfSelf(
            @Param("carId")long carId,
            @Param("exceptionRentId") long exceptionRentId,
            @Param("beginDate") Date beginDate,
            @Param("endDate") Date endDate);
}
