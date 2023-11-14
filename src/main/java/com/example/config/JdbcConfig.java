package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

@Configuration
public class JdbcConfig {

    @Bean
    public SimpleJdbcInsert carInsert(JdbcTemplate jdbcTemplate) {

        return new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cars")
                .usingColumns("vin",
                        "productionYear",
                        "brand",
                        "model",
                        "mileage",
                        "registration",
                        "isAvailable")
                .usingGeneratedKeyColumns("id");
    }

    @Bean
    public SimpleJdbcInsert rentInsert(JdbcTemplate jdbcTemplate) {
        return new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("rents")
                .usingColumns("dateFrom",
                        "dateTo",
                        "status",
                        "carId",
                        "userId",
                        "startMileage",
                        "endMileage")
                .usingGeneratedKeyColumns("id");
    }

    @Bean
    public SimpleJdbcInsert userInsert(JdbcTemplate jdbcTemplate) {
        return new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingColumns("name",
                        "surname",
                        "personId",
                        "phoneNumber",
                        "email")
                .usingGeneratedKeyColumns("id");
    }

}
