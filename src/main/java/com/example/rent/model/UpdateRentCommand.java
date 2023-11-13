package com.example.rent.model;

import com.example.car.model.Car;
import com.example.user.model.User;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateRentCommand {

    @NotNull(message = "ID_NOT_NULL")
    private Long id;

    @NotNull(message = "DATE_FROM_NOT_NULL")
    private LocalDate dateFrom;

    @NotNull(message = "DATE_TO_NOT_NULL")
    private LocalDate dateTo;

    @NotBlank(message = "STATUS_NOT_NULL")
    private String status;

    @NotNull(message = "CAR_ID_NOT_NULL")
    @Min(value = 0, message = "CAR_ID_NOT_NEGATIVE")
    private Long carId;

    @NotNull(message = "USER_ID_NOT_NULL")
    @Min(value = 0, message = "USER_ID_NOT_NEGATIVE")
    private Long userId;

    @NotNull(message = "START_MILEAGE_NOT_NULL")
    @Min(value = 0, message = "START_MILEAGE_NOT_LESS_THAN_ZERO")
    private Integer startMileage;

    //@NotNull(message = "END_MILEAGE_NOT_NULL")
    @Min(value = 0, message = "END_MILEAGE_NOT_LESS_THAN_ZERO")
    private Integer endMileage;

    @AssertTrue(message = "DATE_FROM_BIGGER_THEN_DATE_TO") // todo uruchamiać to jakoś
    private boolean isDateFromBeforeDateTo() {
        return dateFrom.isBefore(dateTo) || dateFrom.isEqual(dateTo);
    }

    @AssertTrue(message = "START_MILEAGE_BIGGER_THEN_END_MILEAGE") // todo uruchamiać to jakoś
    private boolean isStartMileageBiggerThanEndMileage() {
        return startMileage < endMileage;
    }

}
