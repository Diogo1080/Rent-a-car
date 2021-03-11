package com.school.mindera.rentacar.converter;

import com.school.mindera.rentacar.model.Rent.CreateOrUpdateRentDto;
import com.school.mindera.rentacar.model.Rent.RentDetailsDto;
import com.school.mindera.rentacar.persistence.entity.CarEntity;
import com.school.mindera.rentacar.persistence.entity.RentEntity;
import com.school.mindera.rentacar.persistence.entity.UserEntity;

public class RentConverter {
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

    public static RentEntity fromCreateOrUpdateRentDtoToRentEntity(CreateOrUpdateRentDto createOrUpdateRentDto, CarEntity car, UserEntity user) {
        return RentEntity.builder()
                .carEntity(car)
                .userEntity(user)
                .expectedBeginDate(createOrUpdateRentDto.getExpectedBeginDate())
                .expectedEndDate(createOrUpdateRentDto.getExpectedEndDate())
                .expectedPrice(createOrUpdateRentDto.getExpectedPrice())
                .finalPrice(createOrUpdateRentDto.getFinalPrice())
                .beginDate(createOrUpdateRentDto.getBeginDate())
                .endDate(createOrUpdateRentDto.getEndDate())
                .build();
    }
}
