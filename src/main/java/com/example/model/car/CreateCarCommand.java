package com.example.model.car;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CreateCarCommand {

    @NotBlank(message = "VIN_NOT_BLANK")
    @Size(min = 1, max = 100, message = "VIN_WRONG_SIZE")
    private String vin;

    @NotNull(message = "PRODUCTION_YEAR_NOT_NULL")
    @Min(value = 1886, message = "PRODUCTION_YEAR_NOT_LESS_THAN_1886")
    private Integer productionYear;

    @NotBlank(message = "BRAND_NOT_BLANK")
    @Size(min = 1, max = 100, message = "BRAND_WRONG_SIZE")
    private String brand;

    @NotBlank(message = "MODEL_NOT_BLANK")
    @Size(min = 1, max = 100, message = "MODEL_WRONG_SIZE")
    private String model;

    @Min(value = 0, message = "VIN_NOT_LESS_THAN_ZERO")
    private Integer mileage;

    @NotBlank(message = "REGISTRATION_NOT_BLANK")
    @Size(min = 1, max = 100, message = "REGISTRATION_WRONG_SIZE")
    private String registration;

    private Boolean isAvailable;

    private Set<Long> rentsId;

}
