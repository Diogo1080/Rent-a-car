package com.school.mindera.rentacar.controller;

import com.school.mindera.rentacar.persistence.entity.CarEntity;
import com.school.mindera.rentacar.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    public ResponseEntity<CarEntity> createCar(@RequestBody CarEntity carEntity) {

        CarEntity createdCar = carService.createCar(carEntity);

        return new ResponseEntity<>(createdCar, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CarEntity>> getAllCars(){
        List<CarEntity> allCars = carService.getAllCars();

        return new ResponseEntity<>(allCars, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarEntity> getCarById(@PathVariable long id){
        CarEntity selectedCar = carService.getCarById(id);

        return new ResponseEntity<>(selectedCar, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CarEntity> updateCar(@RequestBody CarEntity carEntity){
        CarEntity updatedCar = carService.updateCarById(carEntity);

        return new ResponseEntity<>(updatedCar, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CarEntity> deleteCar(@PathVariable long id){
        CarEntity deletedCar = carService.deleteCar(id);

        return new ResponseEntity<>(deletedCar, HttpStatus.OK);
    }
}
