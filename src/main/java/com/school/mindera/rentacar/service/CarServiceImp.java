package com.school.mindera.rentacar.service;

import com.school.mindera.rentacar.command.car.CarDetailsDto;
import com.school.mindera.rentacar.command.car.CreateOrUpdateCarDto;
import com.school.mindera.rentacar.converter.CarConverter;
import com.school.mindera.rentacar.error.ErrorMessages;
import com.school.mindera.rentacar.exception.CarAlreadyExistsException;
import com.school.mindera.rentacar.exception.CarNotFoundException;
import com.school.mindera.rentacar.exception.DatabaseCommunicationException;
import com.school.mindera.rentacar.persistence.entity.CarEntity;
import com.school.mindera.rentacar.persistence.repository.CarRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link CarService} implementation
 */
@Service
public class CarServiceImp implements CarService {

    private static Logger LOGGER = LogManager.getLogger(CarServiceImp.class);
    private final CarRepository carRepository;

    public CarServiceImp(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    /**
     * @see CarService#addNewCar(CreateOrUpdateCarDto)
     */
    @Override
    public CarDetailsDto addNewCar(CreateOrUpdateCarDto carDetails) throws CarAlreadyExistsException, DatabaseCommunicationException {

        // Build Car Entity
        CarEntity carEntity = CarConverter.fromCreateOrUpdateCarDtoToCarEntity(carDetails);
        carEntity.setAvailable(true);

        // Persist car into database
        try {
            LOGGER.debug("Saving car in database");
            carRepository.save(carEntity);

        } catch (DataIntegrityViolationException sqlException) {
            LOGGER.error(ErrorMessages.CAR_ALREADY_EXISTS);
            throw new CarAlreadyExistsException(ErrorMessages.CAR_ALREADY_EXISTS);

        } catch (Exception e) {
            LOGGER.error("Failed while saving car into database - {} - with exception - {}", carEntity, e);
            throw new DatabaseCommunicationException(ErrorMessages.DATABASE_COMMUNICATION_ERROR, e);

        }

        // Convert to CarDetailsDto and return created car
        return CarConverter.fromCarEntityToCarDetailsDto(carEntity);
    }

    /**
     * @see CarService#getCarById(long)
     */
    @Override
    public CarDetailsDto getCarById(long carId) throws CarNotFoundException {
        // Get car from database
        LOGGER.debug("Getting car with id {}", carId);
        CarEntity carEntity = carRepository.findById(carId)
                .orElseThrow(() -> {
                    LOGGER.error(ErrorMessages.CAR_NOT_FOUND);
                    return new CarNotFoundException(ErrorMessages.CAR_NOT_FOUND);
                });

        // Convert to CarDetailsDto and return
        return CarConverter.fromCarEntityToCarDetailsDto(carEntity);
    }

    /**
     * @see CarService#getAllCars()
     */
    @Override
    public List<CarDetailsDto> getAllCars() {
        // Get all users from database
        LOGGER.debug("Getting all cars");
        Iterable<CarEntity> usersList = carRepository.findAll();

        // Convert list items from CarEntity to CarDetailsDto
        List<CarDetailsDto> carsListResponse = new ArrayList<>();
        for (CarEntity car : usersList) {
            carsListResponse.add(CarConverter.fromCarEntityToCarDetailsDto(car));
        }

        // Return list of CarDetailsDto
        return carsListResponse;
    }

    /**
     * @see CarService#deleteCar(long)
     */
    @Override
    public void deleteCar(long carId) {
        // Verify if the car exists
        LOGGER.debug("Getting car with id {}", carId);
        CarEntity carEntity = carRepository.findById(carId)
                .orElseThrow(() -> {
                    LOGGER.error(ErrorMessages.CAR_NOT_FOUND);
                    return new CarNotFoundException(ErrorMessages.CAR_NOT_FOUND);
                });

        // Delete car
        try {
            LOGGER.debug("Deleting car from database");
            carRepository.delete(carEntity);

        } catch (DataIntegrityViolationException sqlException) {
            LOGGER.error("Failed while deleting car from database - {}", carEntity);
            throw new CarAlreadyExistsException(ErrorMessages.DATABASE_COMMUNICATION_ERROR);

        } catch (Exception e) {
            LOGGER.error("Failed while deleting car from database - {} - with exception - {}", carEntity,e);
            throw new DatabaseCommunicationException(ErrorMessages.DATABASE_COMMUNICATION_ERROR, e);

        }
    }

    /**
     * @see CarService#updateCarDetails(long, CreateOrUpdateCarDto)
     */
    @Override
    public CarDetailsDto updateCarDetails(long carId, CreateOrUpdateCarDto carDetails) {
        // Verify if the car exists
        LOGGER.debug("Getting car with id {}", carId);
        CarEntity carEntity = carRepository.findById(carId)
                .orElseThrow(() -> {
                    LOGGER.error(ErrorMessages.CAR_NOT_FOUND);
                    return new CarNotFoundException(ErrorMessages.CAR_NOT_FOUND);
                });

        // Update data with carDetails received
        carEntity.setBrand(carDetails.getBrand());
        carEntity.setModelDescription(carDetails.getModelDescription());
        carEntity.setEngineType(carDetails.getEngineType());
        carEntity.setCarSegment(carDetails.getCarSegment());
        carEntity.setPlate(carDetails.getPlate());
        carEntity.setDateOfPurchase(carDetails.getDateOfPurchase());

        // Save changes
        try {
            LOGGER.debug("Saving updated car into database");
            carRepository.save(carEntity);

        } catch (DataIntegrityViolationException sqlException) {
            LOGGER.error("Failed while updating car into database - {}", carEntity);
            throw new CarAlreadyExistsException(ErrorMessages.DATABASE_COMMUNICATION_ERROR);

        }

        // Convert to CarDetailsDto and return updated car
        return CarConverter.fromCarEntityToCarDetailsDto(carEntity);
    }
}
