package com.school.mindera.rentacar.command.Rent;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
/**
 * CreateOrUpdateRentDto mainly used to store rent info when creating or updating rents
 */
@Data
@Builder
public class CreateOrUpdateRentDto {
    @NotNull(message="Car id can't be null")
    private long carId;
    @NotNull(message="User id can't be null")
    private long userId;
    @NotNull(message="Expected Begin Date can't be null")
    private Date expectedBeginDate;
    @NotNull(message="Expected End Date can't be null")
    private Date expectedEndDate;
    private BigDecimal expectedPrice;
}
