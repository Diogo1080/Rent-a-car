package com.school.mindera.rentacar.converter;

import com.school.mindera.rentacar.command.rent.CreateOrUpdateRentDto;
import com.school.mindera.rentacar.command.rent.RentDetailsDto;
import com.school.mindera.rentacar.persistence.entity.CarEntity;
import com.school.mindera.rentacar.persistence.entity.RentEntity;
import com.school.mindera.rentacar.persistence.entity.UserEntity;

/**
 * Rent converter
 * Transforms from one data model to another
 */
public class RentConverter {
    /**
     * From {@link RentEntity} to {@link RentDetailsDto}
     * @param rentEntity {@link RentEntity}
     * @return {@link RentDetailsDto}
     */
    public static RentDetailsDto fromRentEntityToRentDetailsDto(RentEntity rentEntity) {
        return RentDetailsDto.builder()
                .id(rentEntity.getId())
                .carId(rentEntity.getCarEntity().getId())
                .userId(rentEntity.getUserEntity().getId())
                .expectedBeginDate(rentEntity.getExpectedBeginDate())
                .expectedEndDate(rentEntity.getExpectedEndDate())
                .expectedPrice(rentEntity.getExpectedPrice())
                .finalPrice(rentEntity.getFinalPrice())
                .beginDate(rentEntity.getBeginDate())
                .endDate(rentEntity.getEndDate())
                .build();
    }

    /**
     * From {@link CreateOrUpdateRentDto} to {@link RentEntity}
     * Requires two extra parameters in order to do a RentEntity
     * @param createOrUpdateRentDto {@link CreateOrUpdateRentDto}
     * @param car {@link CarEntity}
     * @param user {@link UserEntity}
     * @return {@link RentEntity}
     */
    public static RentEntity fromCreateOrUpdateRentDtoToRentEntity(CreateOrUpdateRentDto createOrUpdateRentDto, CarEntity car, UserEntity user) {
        return RentEntity.builder()
                .carEntity(car)
                .userEntity(user)
                .expectedBeginDate(createOrUpdateRentDto.getExpectedBeginDate())
                .expectedEndDate(createOrUpdateRentDto.getExpectedEndDate())
                .expectedPrice(createOrUpdateRentDto.getExpectedPrice())
                .build();
    }
}
