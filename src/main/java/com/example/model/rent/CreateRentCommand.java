package com.example.model.rent;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateRentCommand {

    @NotNull(message = "DATE_FROM_NOT_NULL")
    private LocalDate dateFrom;

    @NotNull(message = "DATE_TO_NOT_NULL")
    private LocalDate dateTo;

    @NotBlank(message = "STATUS_NOT_NULL")
    private String status;

    @NotNull(message = "CAR_NOT_NULL")
    @Min(value = 0, message = "CAR_ID_NOT_NEGATIVE")
    private Long carId;

    @NotNull(message = "USER_NOT_NULL")
    @Min(value = 0, message = "USER_ID_NOT_NEGATIVE")
    private Long userId;

    @NotNull(message = "END_MILEAGE_NOT_NULL")
    @Min(value = 0, message = "END_MILEAGE_NOT_LESS_THAN_ZERO")
    private Integer endMileage;

    @AssertTrue(message = "DATE_FROM_BIGGER_THEN_DATE_TO")
    private boolean isDateFromBeforeDateTo() {
        return dateFrom.isBefore(dateTo) || dateFrom.isEqual(dateTo);
    }

}
