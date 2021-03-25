package com.school.mindera.rentacar.command.car;

import com.school.mindera.rentacar.enumerators.CarBrands;
import com.school.mindera.rentacar.enumerators.CarSegment;
import com.school.mindera.rentacar.enumerators.EngineType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * CreateOrUpdateCarDto mainly used to store car info when creating or updating cars
 */
@Data
@Builder
public class CreateOrUpdateCarDto {
    private CarBrands brand;
    private String modelDescription;
    private EngineType engineType;
    private CarSegment carSegment;
    private Date dateOfPurchase;
    private String plate;
}
