package com.school.mindera.rentacar.command.rent;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * rentDetailsDto mainly used to respond with rent details
 */

@Data
@Builder
public class RentDetailsDto {
    private long id;
    private long userId;
    private long carId;
    private Date expectedBeginDate;
    private Date expectedEndDate;
    private BigDecimal expectedPrice;
    private Date beginDate;
    private Date endDate;
    private BigDecimal finalPrice;
}
