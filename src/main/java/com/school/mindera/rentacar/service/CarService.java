package com.school.mindera.rentacar.service;

import com.school.mindera.rentacar.command.car.CarDetailsDto;
import com.school.mindera.rentacar.command.car.CreateOrUpdateCarDto;
import com.school.mindera.rentacar.exception.CarAlreadyExistsException;
import com.school.mindera.rentacar.exception.CarNotFoundException;
import com.school.mindera.rentacar.exception.DatabaseCommunicationException;

import java.util.List;

/**
 * Common interface for car service, provides methods to manage cars
 */
public interface CarService {
    /**
     * Create new car
     * @param carDetails {@link CreateOrUpdateCarDto}
     * @return {@link CarDetailsDto} the car created
     * @throws CarAlreadyExistsException when the car already exists
     * @throws DatabaseCommunicationException when communication with database isn't established
     */
    CarDetailsDto addNewCar(CreateOrUpdateCarDto carDetails) throws CarAlreadyExistsException, DatabaseCommunicationException;

    /**
     * Get given car with id
     * @param carId Receives car id
     * @return {@link CarDetailsDto} the car created
     * @throws CarNotFoundException the car was not found
     */
    CarDetailsDto getCarById(long carId) throws ClassNotFoundException;

    /**
     * Get all cars
     * @return a list of {@link CarDetailsDto}
     */
    List<CarDetailsDto> getAllCars();

    /**
     * Delete car with given id
     * @param carId Receives car id
     */
    void deleteCar(long carId);


    /**
     * Update car with given id
     * @param carId Receives car id
     * @param carDetails {@link CreateOrUpdateCarDto}
     *  @return {@link CarDetailsDto} the car created
     */
    CarDetailsDto updateCarDetails(long carId, CreateOrUpdateCarDto carDetails);
}
