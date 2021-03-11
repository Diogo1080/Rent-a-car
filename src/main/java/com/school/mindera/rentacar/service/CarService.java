package com.school.mindera.rentacar.service;

import com.school.mindera.rentacar.converter.CarConverter;
import com.school.mindera.rentacar.error.ErrorMessages;
import com.school.mindera.rentacar.exception.CarAlreadyExistsException;
import com.school.mindera.rentacar.exception.CarNotFoundException;
import com.school.mindera.rentacar.model.Car.CarDetailsDto;
import com.school.mindera.rentacar.model.Car.CreateOrUpdateCarDto;
import com.school.mindera.rentacar.persistence.entity.CarEntity;
import com.school.mindera.rentacar.persistence.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarService {

    @Autowired
    CarRepository carRepository;

    public CarDetailsDto addNewCar(CreateOrUpdateCarDto carDetails) {

        // Build Car Entity
        CarEntity carEntity = CarConverter.fromCreateOrUpdateCarDtoToCarEntity(carDetails);
        carEntity.setAvailable(true);

        // Persist car into database
        try {
            carRepository.save(carEntity);
        } catch (DataIntegrityViolationException sqlException) {
            throw new CarAlreadyExistsException(ErrorMessages.CAR_ALREADY_EXISTS);
        }

        // Convert to CarDetailsDto and return created car
        return CarConverter.fromCarEntityToCarDetailsDto(carEntity);
    }

    public CarDetailsDto getCarById(long carId) {
        // Get car from database
        CarEntity carEntity = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException(ErrorMessages.CAR_NOT_FOUND));

        // Convert to CarDetailsDto and return
        return CarConverter.fromCarEntityToCarDetailsDto(carEntity);
    }

    public List<CarDetailsDto> getAllCars() {
        // Get all users from database
        Iterable<CarEntity> usersList = carRepository.findAll();

        // Convert list items from CarEntity to CarDetailsDto
        List<CarDetailsDto> carsListResponse = new ArrayList<>();
        for (CarEntity car : usersList) {
            carsListResponse.add(CarConverter.fromCarEntityToCarDetailsDto(car));
        }

        // Return list of CarDetailsDto
        return carsListResponse;
    }

    public void deleteCar(long carId) {
        // Verify if the car exists
        CarEntity car = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException(ErrorMessages.CAR_NOT_FOUND));

        // Delete car
        carRepository.delete(car);
    }

    public CarDetailsDto updateCarDetails(long carId, CreateOrUpdateCarDto carDetails) {
        // Verify if the car exists
        CarEntity carEntity = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException(ErrorMessages.CAR_NOT_FOUND));

        // Update data with carDetails received
        carEntity.setBrand(carDetails.getBrand());
        carEntity.setModelDescription(carDetails.getModelDescription());
        carEntity.setEngineType(carDetails.getEngineType());
        carEntity.setCarSegment(carDetails.getCarSegment());
        carEntity.setPlate(carDetails.getPlate());
        carEntity.setDateOfPurchase(carDetails.getDateOfPurchase());

        // Save changes
        carRepository.save(carEntity);

        // Convert to CarDetailsDto and return updated car
        return CarConverter.fromCarEntityToCarDetailsDto(carEntity);
    }
}
