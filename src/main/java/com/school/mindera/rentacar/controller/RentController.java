package com.school.mindera.rentacar.controller;

import com.school.mindera.rentacar.model.Rent.CreateOrUpdateRentDto;
import com.school.mindera.rentacar.model.Rent.RentDateDto;
import com.school.mindera.rentacar.model.Rent.RentDetailsDto;
import com.school.mindera.rentacar.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("rent")
public class RentController {

    @Autowired
    RentService rentService;

    @PostMapping("/{carId}/{userId}")
    public ResponseEntity<RentDetailsDto> createRent(@Valid @RequestBody CreateOrUpdateRentDto createOrUpdateRentDto,
                                                     @PathVariable long carId,
                                                     @PathVariable long userId) {
        RentDetailsDto rentDetails = rentService.addNewRent(createOrUpdateRentDto,carId,userId);
        return new ResponseEntity<>(rentDetails, HttpStatus.CREATED);
    }

    @GetMapping("/{rentId}")
    public ResponseEntity<RentDetailsDto> getRentById(@PathVariable long rentId) {
        RentDetailsDto rentDetails = rentService.getRentById(rentId);
        return new ResponseEntity<>(rentDetails, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RentDetailsDto>> getRentsList() {
        List<RentDetailsDto> rentList = rentService.getAllRents();
        return new ResponseEntity<>(rentList, HttpStatus.OK);
    }

    @PutMapping("/{rentId}")
    public ResponseEntity<RentDetailsDto> updateRent(@Valid @PathVariable long rentId,
                                                   @RequestBody CreateOrUpdateRentDto createOrUpdateRentDto) {
        RentDetailsDto rentDetailsDto = rentService.updateRentDetails(rentId, createOrUpdateRentDto);
        return new ResponseEntity<>(rentDetailsDto, HttpStatus.OK);
    }

    @PatchMapping("/{rentId}/begin")
    public ResponseEntity<RentDetailsDto> updateRentBeginDate(@Valid @PathVariable long rentId,
                                                              @RequestBody RentDateDto rentDateDto){
        RentDetailsDto rentDetailsDto = rentService.updateRentBeginDate(rentId, rentDateDto);
        return new ResponseEntity<>(rentDetailsDto, HttpStatus.OK);
    }

    @PatchMapping("/{rentId}/end")
    public ResponseEntity<RentDetailsDto> updateRentEndDate(@Valid @PathVariable long rentId,
                                                              @RequestBody RentDateDto rentDateDto){
        RentDetailsDto rentDetailsDto = rentService.updateRentEndDate(rentId, rentDateDto);
        return new ResponseEntity<>(rentDetailsDto, HttpStatus.OK);
    }

    @DeleteMapping("/{rentId}")
    public ResponseEntity deleteRent(@PathVariable long rentId) {
        rentService.deleteRent(rentId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
