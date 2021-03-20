package com.school.mindera.rentacar.service;

import com.school.mindera.rentacar.command.Rent.CreateOrUpdateRentDto;
import com.school.mindera.rentacar.command.Rent.RentDetailsDto;
import com.school.mindera.rentacar.converter.RentConverter;
import com.school.mindera.rentacar.error.ErrorMessages;
import com.school.mindera.rentacar.exception.*;
import com.school.mindera.rentacar.persistence.entity.CarEntity;
import com.school.mindera.rentacar.persistence.entity.RentEntity;
import com.school.mindera.rentacar.persistence.entity.UserEntity;
import com.school.mindera.rentacar.persistence.repository.CarRepository;
import com.school.mindera.rentacar.persistence.repository.RentRepository;
import com.school.mindera.rentacar.persistence.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * An {@link RentService} implementation
 */
@Service
public class RentServiceImp implements RentService {

    private final RentRepository rentRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private static final Logger LOGGER = LogManager.getLogger(RentServiceImp.class);

    public RentServiceImp(UserRepository userRepository, CarRepository carRepository, RentRepository rentRepository) {
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.rentRepository = rentRepository;
    }

    /**
     * @see RentService#addNewRent(CreateOrUpdateRentDto)
     */
    @Override
    public RentDetailsDto addNewRent(CreateOrUpdateRentDto rentDetails)
            throws CarNotFoundException,
            UserNotFoundException,
            CarAlreadyRentedException,
            DatabaseCommunicationException {

        //Get car entity
        LOGGER.debug("Getting car with id {}", rentDetails.getCarId());
        CarEntity carEntity = carRepository.findById(rentDetails.getCarId())
                .orElseThrow(() -> {
                    LOGGER.error(ErrorMessages.CAR_NOT_FOUND);
                    return new CarNotFoundException(ErrorMessages.CAR_NOT_FOUND);
                });

        //Get user entity
        LOGGER.debug("Getting user with id {}", rentDetails.getUserId());
        UserEntity userEntity = userRepository.findById(rentDetails.getUserId())
                .orElseThrow(() -> {
                    LOGGER.error(ErrorMessages.USER_NOT_FOUND);
                    return new UserNotFoundException(ErrorMessages.USER_NOT_FOUND);
                });

        //Check if car is rented
        LOGGER.debug("Checking if car is already rented");
        if (rentRepository.findInterceptionOfDates(
                rentDetails.getCarId(),
                rentDetails.getExpectedBeginDate(),
                rentDetails.getExpectedEndDate())) {
            LOGGER.error(ErrorMessages.CAR_EXPECTED_TO_BE_UNAVAILABLE);
            throw new CarAlreadyRentedException(ErrorMessages.CAR_EXPECTED_TO_BE_UNAVAILABLE);
        }

        //Build rent entity
        RentEntity rentEntity = RentConverter.fromCreateOrUpdateRentDtoToRentEntity(rentDetails, carEntity, userEntity);
        rentEntity.setExpectedPrice(calculatePrice(
                rentDetails.getExpectedBeginDate(),
                rentDetails.getExpectedEndDate(),
                carEntity.getCarSegment().getDailyPrice()));

        //Persist rent into database
        try {
            LOGGER.debug("Saving rent into database");
            rentRepository.save(rentEntity);

        } catch (Exception e) {
            LOGGER.error("Failed while saving rent into database - {}", rentEntity);
            throw new DatabaseCommunicationException(ErrorMessages.DATABASE_COMMUNICATION_ERROR, e);

        }

        //Convert to RentDetailsDto and return created rent
        return RentConverter.fromRentEntityToRentDetailsDto(rentEntity);
    }

    /**
     * @see RentService#getRentById(long)
     */
    @Override
    public RentDetailsDto getRentById(long rentId) throws RentNotFoundException {
        //Get rent from database
        RentEntity rentEntity = rentRepository.findById(rentId).orElseThrow(() -> {
            LOGGER.error(ErrorMessages.RENT_NOT_FOUND);
            return new RentNotFoundException(ErrorMessages.RENT_NOT_FOUND);
        });

        //Convert to RentDetailsDto and return
        return RentConverter.fromRentEntityToRentDetailsDto(rentEntity);
    }

    /**
     * @see RentService#getAllRents()
     */
    @Override
    public List<RentDetailsDto> getAllRents() {
        //Get all rents from database
        Iterable<RentEntity> rentList = rentRepository.findAll();

        // Convert list items from RentEntity to RentDetailsDto
        List<RentDetailsDto> rentListResponse = new ArrayList<>();
        for (RentEntity rent : rentList) {
            rentListResponse.add(RentConverter.fromRentEntityToRentDetailsDto(rent));
        }

        // Return list of CarDetailsDto
        return rentListResponse;
    }

    /**
     * @see RentService#updateRentDetails(long, CreateOrUpdateRentDto)
     */
    @Override
    public RentDetailsDto updateRentDetails(long rentId, CreateOrUpdateRentDto rentDetails)
            throws RentNotFoundException,
            CarNotFoundException,
            UserNotFoundException,
            CarAlreadyRentedException,
            DatabaseCommunicationException {

        //Get rent entity
        LOGGER.debug("Getting rent with id {}", rentId);
        RentEntity rentEntity = rentRepository.findById(rentId)
                .orElseThrow(() -> {
                    LOGGER.error(ErrorMessages.RENT_NOT_FOUND);
                    return new RentNotFoundException(ErrorMessages.RENT_NOT_FOUND);
                });

        //Get car entity
        LOGGER.debug("Getting car with id {}", rentDetails.getCarId());
        CarEntity carEntity = carRepository.findById(rentDetails.getCarId())
                .orElseThrow(() -> {
                    LOGGER.error(ErrorMessages.CAR_NOT_FOUND);
                    return new CarNotFoundException(ErrorMessages.CAR_NOT_FOUND);
                });

        //Get user entity
        LOGGER.debug("Getting user with id {}", rentDetails.getUserId());
        UserEntity userEntity = userRepository.findById(rentDetails.getUserId())
                .orElseThrow(() -> {
                    LOGGER.error(ErrorMessages.USER_NOT_FOUND);
                    return new UserNotFoundException(ErrorMessages.USER_NOT_FOUND);
                });

        //Checking if car is already rented
        LOGGER.debug("Checking if car is already rented");
        if (rentRepository.findInterceptionOfDatesWithExceptionOfSelf(
                rentDetails.getCarId(),
                rentId,
                rentDetails.getExpectedBeginDate(),
                rentDetails.getExpectedEndDate())) {
            LOGGER.error(ErrorMessages.CAR_EXPECTED_TO_BE_UNAVAILABLE);
            throw new CarAlreadyRentedException(ErrorMessages.CAR_EXPECTED_TO_BE_UNAVAILABLE);
        }

        // Update data with rent details
        rentEntity.setCarEntity(carEntity);
        rentEntity.setExpectedBeginDate(rentDetails.getExpectedBeginDate());
        rentEntity.setExpectedEndDate(rentDetails.getExpectedEndDate());
        rentEntity.setExpectedPrice(calculatePrice(
                rentDetails.getExpectedBeginDate(),
                rentDetails.getExpectedEndDate(),
                carEntity.getCarSegment().getDailyPrice()));

        //Save changes
        try {
            LOGGER.debug("Saving rent into database");
            rentRepository.save(rentEntity);
        } catch (Exception e) {
            LOGGER.error("Failed while saving rent into database - {}", rentEntity);
            throw new DatabaseCommunicationException(ErrorMessages.DATABASE_COMMUNICATION_ERROR, e);
        }

        return RentConverter.fromRentEntityToRentDetailsDto(rentEntity);
    }

    /**
     * @see RentService#deleteRent(long)
     */
    @Override
    public void deleteRent(long rentId) throws CarNotFoundException, InvalidRentStatusException {
        // Verify if the rent Exists
        RentEntity rentEntity = rentRepository.findById(rentId)
                .orElseThrow(() -> {
                    LOGGER.error(ErrorMessages.RENT_NOT_FOUND);
                    return new CarNotFoundException(ErrorMessages.RENT_NOT_FOUND);
                });

        // Verify if car is already delivered
        if (Objects.nonNull(rentEntity.getBeginDate())) {
            LOGGER.error(ErrorMessages.CAN_NOT_DELETE_CAR_ALREADY_DELIVERED);
            throw new InvalidRentStatusException(ErrorMessages.CAN_NOT_DELETE_CAR_ALREADY_DELIVERED);
        }

        // Delete car
        LOGGER.debug("Deleting rent from database");
        rentRepository.delete(rentEntity);
    }

    /**
     * @see RentService#deliverCar(long)
     */
    public RentDetailsDto deliverCar(long rentId) throws RentNotFoundException,DatabaseCommunicationException{
        //Get rent entity
        RentEntity rentEntity = rentRepository.findById(rentId).orElseThrow(() -> {
            LOGGER.error(ErrorMessages.RENT_NOT_FOUND);
            return new RentNotFoundException(ErrorMessages.RENT_NOT_FOUND);
        });

        //Delivering car with timestamp
        CarEntity carEntity = rentEntity.getCarEntity();
        rentEntity.setBeginDate(new Date());
        carEntity.setAvailable(false);

        //Saving changes
        try {
            LOGGER.debug("Saving changes of rent into database");
            rentRepository.save(rentEntity);
            LOGGER.debug("Saving changes of car into database");
            carRepository.save(carEntity);
        } catch (Exception e) {
            LOGGER.error("Failed while saving rent and car into database - {} - {}", rentEntity,carEntity);
            throw new DatabaseCommunicationException(ErrorMessages.DATABASE_COMMUNICATION_ERROR, e);
        }

        // Convert to RentDetailsDto and return updated rent
        return RentConverter.fromRentEntityToRentDetailsDto(rentEntity);
    }

    /**
     * @see RentService#returnCar(long)
     */
    public RentDetailsDto returnCar(long rentId) throws RentNotFoundException,DatabaseCommunicationException{
        //Get rent entity
        RentEntity rentEntity = rentRepository.findById(rentId).orElseThrow(() -> {
            LOGGER.error(ErrorMessages.RENT_NOT_FOUND);
            return new RentNotFoundException(ErrorMessages.RENT_NOT_FOUND);
        });
        //Returning car with timestamp and final price
        CarEntity carEntity = rentEntity.getCarEntity();

        carEntity.setAvailable(true);

        rentEntity.setEndDate(new Date());
        rentEntity.setFinalPrice(calculatePrice(rentEntity.getBeginDate(),
                rentEntity.getEndDate(), rentEntity.getCarEntity().getCarSegment().getDailyPrice()));

        //Saving changes
        try {
            LOGGER.debug("Saving changes of rent into database");
            rentRepository.save(rentEntity);
            LOGGER.debug("Saving changes of car into database");
            carRepository.save(carEntity);
        } catch (Exception e) {
            LOGGER.error("Failed while saving rent and car into database - {} - {}", rentEntity,carEntity);
            throw new DatabaseCommunicationException(ErrorMessages.DATABASE_COMMUNICATION_ERROR, e);
        }

        // Convert to RentDetailsDto and return updated rent
        return RentConverter.fromRentEntityToRentDetailsDto(rentEntity);
    }

    /**
     * Helper method to calculate the price considering the begin and end date and the daily price
     *
     * @param beingDate
     * @param endDate
     * @param dailyPrice
     * @return {@link BigDecimal} the total price
     */
    private BigDecimal calculatePrice(Date beingDate, Date endDate, BigDecimal dailyPrice) {
        // Convert to LocalDate
        LocalDate beginLocalDate = LocalDate.ofInstant(beingDate.toInstant(), ZoneId.systemDefault());
        LocalDate endLocalDate = LocalDate.ofInstant(endDate.toInstant(), ZoneId.systemDefault());

        // Get difference between beginDate & endDate in days
        long numOfDaysBetween = ChronoUnit.DAYS.between(beginLocalDate, endLocalDate);

        // Calculate and return the totalCost
        return dailyPrice.multiply(BigDecimal.valueOf(numOfDaysBetween != 0L ? numOfDaysBetween : 1L));
    }

}
