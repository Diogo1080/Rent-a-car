package com.school.mindera.rentacar.service;

import com.school.mindera.rentacar.command.Paginated;
import com.school.mindera.rentacar.command.rent.CreateOrUpdateRentDto;
import com.school.mindera.rentacar.command.rent.RentDetailsDto;
import com.school.mindera.rentacar.exception.*;
import org.springframework.data.domain.Pageable;

import java.util.List;
/**
 * Common interface for rent service, provides methods to manage rents
 */
public interface RentService {

    /**
     * Add new Rent
     * @param createOrUpdateRentDto {@link CreateOrUpdateRentDto}
     * @return {@link RentDetailsDto}
     * @throws CarNotFoundException if car not found
     * @throws UserNotFoundException if user not found
     * @throws CarAlreadyRentedException if car is already rented in given dates
     * @throws DatabaseCommunicationException if database connection isn't established
     */
    RentDetailsDto addNewRent(CreateOrUpdateRentDto createOrUpdateRentDto)
            throws CarAlreadyRentedException,
            CarNotFoundException,
            UserNotFoundException,
            DatabaseCommunicationException;

    /**
     * Get certain rent by id from database
     * @param rentId Receives the rent id
     * @return {@link RentDetailsDto}
     * @throws RentNotFoundException if rent is not found
     */
    RentDetailsDto getRentById(long rentId) throws RentNotFoundException;


    /**
     * Gets list of rents from database with pagination
     *
     * @param pagination the page and the number of elements per page
     * @return {@link Paginated<RentDetailsDto>}
     */
    Paginated<RentDetailsDto> getRentsList(Pageable pagination);


    /**
     * Update Rent with certain id
     * Can only update:
     *          carId
     *          expectedBeginDate
     *          expectedEndDate
     * @param rentId Receives rent id to update
     * @param createOrUpdateRentDto {@link CreateOrUpdateRentDto}
     * @return {@link RentDetailsDto}
     * @throws RentNotFoundException if rent is not found
     * @throws CarNotFoundException if car is not found
     * @throws UserNotFoundException if user is not found
     * @throws CarAlreadyRentedException if car is already rented in given dates
     * @throws DatabaseCommunicationException if database connection isn't established
     */
    RentDetailsDto updateRentDetails(long rentId, CreateOrUpdateRentDto createOrUpdateRentDto)
            throws RentNotFoundException,
            CarNotFoundException,
            UserNotFoundException,
            CarAlreadyRentedException,
            InvalidRentStatusException,
            DatabaseCommunicationException;


    /**
     * Delete rent
     * @param rentId Receives the rent id
     * @throws CarNotFoundException if car is not found
     * @throws InvalidRentStatusException if car is already delivered
     */
    void deleteRent(long rentId) throws CarNotFoundException,InvalidRentStatusException;

    /**
     * Deliver car to client
     * Updates car availability and rent's begin date
     * @param rentId Receives the rent id
     * @return {@link RentDetailsDto}
     * @throws RentNotFoundException if rent is not found
     * @throws DatabaseCommunicationException if database connection isn't established
     */
    RentDetailsDto deliverCar(long rentId) throws RentNotFoundException,DatabaseCommunicationException;

    /**
     * Return car to house
     * Updates car availability, rent's end date and rent's final price
     * @param rentId Receives the rent id
     * @return {@link RentDetailsDto}
     * @throws RentNotFoundException if rent is not found
     * @throws DatabaseCommunicationException if database connection isn't established
     */
    RentDetailsDto returnCar(long rentId) throws RentNotFoundException,DatabaseCommunicationException;


}
