package com.example.user.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UpdateUserCommand {

    @NotNull(message = "ID_NOT_NULL")
    private Long id;

    @NotBlank(message = "NAME_NOT_BLANK")
    @Size(min = 1, max = 100, message = "NAME_WRONG_SIZE")
    @Pattern(regexp = "[A-Z][a-z]+", message = "NAME_WRONG_FORMAT")
    private String name;

    @NotBlank(message = "SURNAME_NOT_BLANK")
    @Size(min = 1, max = 100, message = "SURNAME_WRONG_SIZE")
    @Pattern(regexp = "[A-Z][a-z]+", message = "SURNAME_WRONG_FORMAT")
    private String surname;

    @NotBlank(message = "PERSON_ID_NOT_BLANK")
    private String personId;

    @Min(value = 0, message = "PHONE_NUMBER_NOT_NEGATIVE")
    private Long phoneNumber;

    @Pattern(regexp = ".+@.+", message = "EMAIL_WRONG_FORMAT")
    private String email;

    private Set<Long> rentID;
}
