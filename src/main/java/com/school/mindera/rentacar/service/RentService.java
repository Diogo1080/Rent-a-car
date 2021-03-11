package com.school.mindera.rentacar.service;

import com.school.mindera.rentacar.converter.RentConverter;
import com.school.mindera.rentacar.error.ErrorMessages;
import com.school.mindera.rentacar.exception.CarAlreadyRentedException;
import com.school.mindera.rentacar.exception.CarNotFoundException;
import com.school.mindera.rentacar.exception.RentNotFoundException;
import com.school.mindera.rentacar.exception.UserNotFoundException;
import com.school.mindera.rentacar.model.Rent.CreateOrUpdateRentDto;
import com.school.mindera.rentacar.model.Rent.RentDateDto;
import com.school.mindera.rentacar.model.Rent.RentDetailsDto;
import com.school.mindera.rentacar.persistence.entity.CarEntity;
import com.school.mindera.rentacar.persistence.entity.RentEntity;
import com.school.mindera.rentacar.persistence.entity.UserEntity;
import com.school.mindera.rentacar.persistence.repository.CarRepository;
import com.school.mindera.rentacar.persistence.repository.RentRepository;
import com.school.mindera.rentacar.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RentService {

    @Autowired
    RentRepository rentRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    UserRepository userRepository;

    public RentDetailsDto addNewRent(CreateOrUpdateRentDto createOrUpdateRentDto, long carId, long userId) {

        CarEntity carEntity = carRepository.findById(carId).orElseThrow(() -> new CarNotFoundException(ErrorMessages.CAR_NOT_FOUND));
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND));

        List<RentEntity> rents = rentRepository.findAllByCarEntity(carEntity);



        if (!carEntity.isAvailable()) {
            throw new CarAlreadyRentedException(ErrorMessages.CAR_ALREADY_RENTED);
        }

        long diffInMillies = Math.abs(createOrUpdateRentDto.getExpectedBeginDate().getTime() - createOrUpdateRentDto.getExpectedEndDate().getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        diff = diff == 0 ? 1 : diff;

        createOrUpdateRentDto.setExpectedPrice(carEntity.getCarSegment().getDailyPrice().doubleValue() * diff);

        RentEntity rentEntity = RentConverter.fromCreateOrUpdateRentDtoToRentEntity(createOrUpdateRentDto, carEntity, userEntity);

        rentRepository.save(rentEntity);

        return RentConverter.fromRentEntityToRentDetailsDto(rentEntity);
    }

    public RentDetailsDto getRentById(long rentId) {
        RentEntity rentEntity = rentRepository.findById(rentId).orElseThrow(() -> new RentNotFoundException(ErrorMessages.RENT_NOT_FOUND));

        return RentConverter.fromRentEntityToRentDetailsDto(rentEntity);
    }

    public List<RentDetailsDto> getAllRents() {
        Iterable<RentEntity> rentList = rentRepository.findAll();

        List<RentDetailsDto> rentListResponse = new ArrayList<>();
        for (RentEntity rent : rentList) {
            rentListResponse.add(RentConverter.fromRentEntityToRentDetailsDto(rent));
        }

        return rentListResponse;
    }

    public RentDetailsDto updateRentDetails(long rentId, CreateOrUpdateRentDto createOrUpdateRentDto) {
        return null;
    }

    public void deleteRent(long rentId) {
    }

    public RentDetailsDto updateRentBeginDate(long rentId, RentDateDto rentDateDto) {
        RentEntity rentEntity = rentRepository.findById(rentId).orElseThrow(() -> new RentNotFoundException(ErrorMessages.RENT_NOT_FOUND));
        CarEntity carEntity = rentEntity.getCarEntity();

        rentEntity.setBeginDate(rentDateDto.getDate());
        carEntity.setAvailable(false);

        rentRepository.save(rentEntity);
        carRepository.save(carEntity);

        return RentConverter.fromRentEntityToRentDetailsDto(rentEntity);

    }

    public RentDetailsDto updateRentEndDate(long rentId, RentDateDto rentDateDto) {
        RentEntity rentEntity = rentRepository.findById(rentId).orElseThrow(() -> new RentNotFoundException(ErrorMessages.RENT_NOT_FOUND));
        CarEntity carEntity = rentEntity.getCarEntity();

        rentEntity.setEndDate(rentDateDto.getDate());

        long diffInMillies = Math.abs(rentEntity.getBeginDate().getTime() - rentEntity.getEndDate().getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        diff = diff == 0 ? 1 : diff;

        rentEntity.setFinalPrice(carEntity.getCarSegment().getDailyPrice().doubleValue() * diff);

        carEntity.setAvailable(true);

        rentRepository.save(rentEntity);
        carRepository.save(carEntity);

        return RentConverter.fromRentEntityToRentDetailsDto(rentEntity);
    }
}
