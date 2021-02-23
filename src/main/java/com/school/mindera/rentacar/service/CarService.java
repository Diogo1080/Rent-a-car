package com.school.mindera.rentacar.service;

import com.school.mindera.rentacar.persistence.entity.CarEntity;
import com.school.mindera.rentacar.persistence.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public CarEntity createCar(CarEntity carEntity) {
        return carRepository.save(carEntity);
    }

    public CarEntity getCarById(long id){
        return carRepository.findById(id).orElse(null);
    }

    public List<CarEntity> getAllCars(){
        return (List<CarEntity>) carRepository.findAll();
    }

    public CarEntity updateCarById(CarEntity carEntity) {
        return carRepository.save(carEntity);
    }

    public CarEntity deleteCar(long id){

        carRepository.deleteById(id);

        return carRepository.findById(id).orElse(null);
    }
}
