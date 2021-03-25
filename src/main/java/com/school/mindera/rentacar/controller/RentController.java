package com.school.mindera.rentacar.controller;

import com.school.mindera.rentacar.command.rent.CreateOrUpdateRentDto;
import com.school.mindera.rentacar.command.rent.RentDetailsDto;
import com.school.mindera.rentacar.service.RentServiceImp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Rent Controller provides end points for CRUD operations on rents
 */
@RestController
@RequestMapping("rent")
public class RentController {

    private final RentServiceImp rentService;
    private static Logger LOGGER = LogManager.getLogger(RentController.class);

    public RentController(RentServiceImp rentService) {
        this.rentService = rentService;
    }

    /**
     * Create rent
     * @param createOrUpdateRentDto {@link CreateOrUpdateRentDto}
     * @return {@link RentDetailsDto}
     */
    @PostMapping
    public ResponseEntity<RentDetailsDto> createRent(@Valid @RequestBody CreateOrUpdateRentDto createOrUpdateRentDto) {
        LOGGER.info("Request to create new rent - {}", createOrUpdateRentDto);
        RentDetailsDto rentDetails = rentService.addNewRent(createOrUpdateRentDto);

        LOGGER.info("Retrieving created rent with info - {}",rentDetails);
        return new ResponseEntity<>(rentDetails, HttpStatus.CREATED);
    }

    /**
     * Get a rent with certain id
     * @param rentId Receives rent id
     * @return {@link RentDetailsDto}
     */
    @GetMapping("/{rentId}")
    public ResponseEntity<RentDetailsDto> getRentById(@PathVariable long rentId) {
        LOGGER.info("Request to get rent of id - {}", rentId);
        RentDetailsDto rentDetails = rentService.getRentById(rentId);

        LOGGER.info("Retrieving rent with info - {}",rentDetails);
        return new ResponseEntity<>(rentDetails, HttpStatus.OK);
    }

    /**
     * Gets all rents from database
     * @return a list of {@link RentDetailsDto}
     */
    @GetMapping
    public ResponseEntity<List<RentDetailsDto>> getRentsList() {
        LOGGER.info("Request to get all rents");
        List<RentDetailsDto> rentList = rentService.getAllRents();

        LOGGER.info("Retrieving all rents with info - {}",rentList);
        return new ResponseEntity<>(rentList, HttpStatus.OK);
    }

    /**
     * Updates a rent with certain Id
     * @param rentId Receives rent id
     * @param createOrUpdateRentDto {@link CreateOrUpdateRentDto}
     * @return {@link RentDetailsDto}
     */
    @PutMapping("/{rentId}")
    public ResponseEntity<RentDetailsDto> updateRent( @PathVariable long rentId,
                                                      @Valid @RequestBody CreateOrUpdateRentDto createOrUpdateRentDto) {
        LOGGER.info("Request to update rent of id - {} - with information - {}", rentId,createOrUpdateRentDto);
        RentDetailsDto rentDetailsDto = rentService.updateRentDetails(rentId, createOrUpdateRentDto);

        LOGGER.info("Retrieving updated rent with info - {}",rentDetailsDto);
        return new ResponseEntity<>(rentDetailsDto, HttpStatus.OK);
    }

    /**
     * Deliver car to client
     * @param rentId Receives rent id
     * @return {@link RentDetailsDto}
     */
    @PatchMapping("/{rentId}/liftCar")
    public ResponseEntity<RentDetailsDto> deliverCar( @PathVariable long rentId){
        LOGGER.info("Request to update beginDate of rent of id - {}",rentId);
        RentDetailsDto rentDetailsDto = rentService.deliverCar(rentId);

        LOGGER.info("Retrieving lifted rent with info - {}",rentDetailsDto);
        return new ResponseEntity<>(rentDetailsDto, HttpStatus.OK);
    }

    /**
     * Return car to house
     * @param rentId Receives rent id
     * @return {@link RentDetailsDto}
     */
    @PatchMapping("/{rentId}/returnCar")
    public ResponseEntity<RentDetailsDto> returnCar(@Valid @PathVariable long rentId){
        LOGGER.info("Request to update endDate of rent of id - {}",rentId);
        RentDetailsDto rentDetailsDto = rentService.returnCar(rentId);

        LOGGER.info("Retrieving finalised rent with info - {}",rentDetailsDto);
        return new ResponseEntity<>(rentDetailsDto, HttpStatus.OK);
    }

    /**
     * Deletes Rent with certain id
     * @param rentId Receives rent id
     * @return Ok if deleted
     */
    @DeleteMapping("/{rentId}")
    public ResponseEntity deleteRent(@PathVariable long rentId) {
        LOGGER.info("Request to delete Rent of id - {}",rentId);
        rentService.deleteRent(rentId);

        LOGGER.info("Responding if delete was successful or not");
        return new ResponseEntity(HttpStatus.OK);
    }
}
