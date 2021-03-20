package com.school.mindera.rentacar.converter;

import com.school.mindera.rentacar.command.Car.CarDetailsDto;
import com.school.mindera.rentacar.command.Car.CreateOrUpdateCarDto;
import com.school.mindera.rentacar.persistence.entity.CarEntity;

/**
 * Car converter
 * Transforms from one data model to another
 */
public class CarConverter {

    /**
     * From {@link CarEntity} to {@link CarDetailsDto}
     * @param carEntity {@link CarEntity}
     * @return {@link CarDetailsDto}
     */
    public static CarDetailsDto fromCarEntityToCarDetailsDto(CarEntity carEntity) {
        return CarDetailsDto.builder()
                .id(carEntity.getId())
                .brand(carEntity.getBrand())
                .modelDescription(carEntity.getModelDescription())
                .engineType(carEntity.getEngineType())
                .dateOfPurchase(carEntity.getDateOfPurchase())
                .carSegment(carEntity.getCarSegment())
                .plate(carEntity.getPlate())
                .available(carEntity.isAvailable())
                .dailyPrice(carEntity.getCarSegment().getDailyPrice())
                .build();
    }

    /**
     * From {@link CreateOrUpdateCarDto} to {@link CarEntity}
     * @param createOrUpdateCarDto {@link CreateOrUpdateCarDto}
     * @return {@link CarEntity}
     */
    public static CarEntity fromCreateOrUpdateCarDtoToCarEntity(CreateOrUpdateCarDto createOrUpdateCarDto) {
        return CarEntity.builder()
                .brand(createOrUpdateCarDto.getBrand())
                .modelDescription(createOrUpdateCarDto.getModelDescription())
                .engineType(createOrUpdateCarDto.getEngineType())
                .dateOfPurchase(createOrUpdateCarDto.getDateOfPurchase())
                .carSegment(createOrUpdateCarDto.getCarSegment())
                .plate(createOrUpdateCarDto.getPlate())
                .build();
    }
}
