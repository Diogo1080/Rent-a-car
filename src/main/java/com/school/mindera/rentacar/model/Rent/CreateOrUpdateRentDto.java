package com.school.mindera.rentacar.model.Rent;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CreateOrUpdateRentDto {
    private Date expectedBeginDate;
    private Date expectedEndDate;
    private double expectedPrice;
    private Date beginDate;
    private Date endDate;
    private double finalPrice;
}
