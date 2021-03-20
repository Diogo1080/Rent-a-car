package com.school.mindera.rentacar.command.Car;

import com.school.mindera.rentacar.enumerators.CarBrands;
import com.school.mindera.rentacar.enumerators.CarSegment;
import com.school.mindera.rentacar.enumerators.EngineType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * CarDetailsDto mainly used to respond with car details
 */
@Data
@Builder
public class CarDetailsDto {
    private long id;
    private CarBrands brand;
    private String modelDescription;
    private EngineType engineType;
    private CarSegment carSegment;
    private Date dateOfPurchase;
    private String plate;
    private BigDecimal dailyPrice;
    private boolean available;

}
