package com.school.mindera.rentacar.model.Rent;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
public class RentDateDto {
    @NotNull(message="This date can't be null")
    private Date date;
}
