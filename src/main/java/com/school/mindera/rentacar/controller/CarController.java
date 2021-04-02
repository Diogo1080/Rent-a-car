package com.school.mindera.rentacar.controller;

import com.school.mindera.rentacar.command.Paginated;
import com.school.mindera.rentacar.command.car.CarDetailsDto;
import com.school.mindera.rentacar.command.car.CreateOrUpdateCarDto;
import com.school.mindera.rentacar.service.CarServiceImp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Car Controller provides end points for CRUD operations on cars
 */

@RestController
@RequestMapping("cars")
public class CarController {

    private final CarServiceImp carService;
    private static Logger LOGGER = LogManager.getLogger(CarController.class);

    public CarController(CarServiceImp carService) {
        this.carService = carService;
    }

    /**
     * Create a car
     *
     * @param createOrUpdateCarDto {@link CreateOrUpdateCarDto}
     * @return {@link CarDetailsDto}
     */
    @PostMapping
    public ResponseEntity<CarDetailsDto> createCar(@Valid @RequestBody CreateOrUpdateCarDto createOrUpdateCarDto) {
        LOGGER.info("Request to create new car - {}", createOrUpdateCarDto);
        CarDetailsDto carDetails = carService.addNewCar(createOrUpdateCarDto);

        LOGGER.info("Retrieving created car - {}", carDetails);
        return new ResponseEntity<>(carDetails, HttpStatus.CREATED);
    }

    /**
     * Get a certain car with id
     *
     * @param carId Receives car Id
     * @return {@link CarDetailsDto}
     */
    @GetMapping("/{carId}")
    public ResponseEntity<CarDetailsDto> getCarById(@PathVariable long carId) {
        LOGGER.info("Request to get car of id - {}", carId);
        CarDetailsDto carDetails = carService.getCarById(carId);

        LOGGER.info("Retrieving gotten car - {}", carDetails);
        return new ResponseEntity<>(carDetails, HttpStatus.OK);
    }


    /**
     * Get cars list with pagination
     *
     * @param page the page number
     * @param size the number of elements per page
     * @return {@link Paginated<CarDetailsDto>}
     */
    @GetMapping
    public ResponseEntity<Paginated<CarDetailsDto>> getCarsList(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        LOGGER.info("Request to get cars list with page and size - {} {}", page, size);
        Paginated<CarDetailsDto> carsList = carService.getCarsList(PageRequest.of(page, size));

        LOGGER.info("Retrieving cars list - {}", carsList);
        return new ResponseEntity<>(carsList, HttpStatus.OK);
    }

    /**
     * Updates a car with certain id
     *
     * @param carId                Receives car Id
     * @param createOrUpdateCarDto {@link CreateOrUpdateCarDto}
     * @return {@link CarDetailsDto}
     */
    @PutMapping("/{carId}")
    public ResponseEntity<CarDetailsDto> updateCar(@PathVariable long carId,
                                                   @Valid @RequestBody CreateOrUpdateCarDto createOrUpdateCarDto) {
        LOGGER.info("Request to update car of id - {} - with information - {}", carId, createOrUpdateCarDto);
        CarDetailsDto carDetailsDto = carService.updateCarDetails(carId, createOrUpdateCarDto);

        LOGGER.info("Retrieving updated car - {}", carDetailsDto);
        return new ResponseEntity<>(carDetailsDto, HttpStatus.OK);
    }

    /**
     * Delete car with certain id
     *
     * @param carId Receives car id
     * @return Ok if deleted
     */
    @DeleteMapping("/{carId}")
    public ResponseEntity deleteCar(@PathVariable long carId) {
        LOGGER.info("Request to delete car of id - {}", carId);
        carService.deleteCar(carId);


        LOGGER.info("Responding if delete was successful or not");
        return new ResponseEntity(HttpStatus.OK);
    }

}
