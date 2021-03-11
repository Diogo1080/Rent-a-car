package com.school.mindera.rentacar.model.Car;

import com.school.mindera.rentacar.enumerators.CarBrands;
import com.school.mindera.rentacar.enumerators.CarSegment;
import com.school.mindera.rentacar.enumerators.EngineType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

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
