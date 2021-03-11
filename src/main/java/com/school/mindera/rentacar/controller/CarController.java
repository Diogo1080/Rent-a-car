package com.school.mindera.rentacar.controller;

import com.school.mindera.rentacar.model.Car.CarDetailsDto;
import com.school.mindera.rentacar.model.Car.CreateOrUpdateCarDto;
import com.school.mindera.rentacar.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("cars")
public class CarController {

    @Autowired
    CarService carService;

    @PostMapping
    public ResponseEntity<CarDetailsDto> createCar(@Valid @RequestBody CreateOrUpdateCarDto createOrUpdateCarDto) {
        CarDetailsDto carDetails = carService.addNewCar(createOrUpdateCarDto);
        return new ResponseEntity<>(carDetails, HttpStatus.CREATED);
    }

    @GetMapping("/{carId}")
    public ResponseEntity<CarDetailsDto> getCarById(@PathVariable long carId) {
        CarDetailsDto carDetails = carService.getCarById(carId);
        return new ResponseEntity<>(carDetails, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CarDetailsDto>> getCarsList() {
        List<CarDetailsDto> carsList = carService.getAllCars();
        return new ResponseEntity<>(carsList, HttpStatus.OK);
    }

    @PutMapping("/{carId}")
    public ResponseEntity<CarDetailsDto> updateCar(@PathVariable long carId,
                                                   @Valid @RequestBody CreateOrUpdateCarDto createOrUpdateCarDto) {
        CarDetailsDto carDetailsDto = carService.updateCarDetails(carId, createOrUpdateCarDto);
        return new ResponseEntity<>(carDetailsDto, HttpStatus.OK);
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity deleteCar(@PathVariable long carId) {
        carService.deleteCar(carId);
        return new ResponseEntity(HttpStatus.OK);
    }

}
