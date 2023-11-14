package com.example.imports.service.impl;

import com.example.car.importvalidation.CarValidator;
import com.example.exception.UnsupportedOperationException;
import com.example.imports.ImportStatusRepository;
import com.example.imports.exception.FileUploadException;
import com.example.imports.exception.InvalidInsertArgumentsException;
import com.example.imports.model.ImportStatus;
import com.example.imports.model.ImportStatusDto;
import com.example.rent.importvalidation.RentValidator;
import com.example.user.importvalidation.UserValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.example.imports.model.ImportStatus.EntityType;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImportService implements com.example.imports.service.ImportService {

    private final SimpleJdbcInsert carInsert;
    private final SimpleJdbcInsert rentInsert;
    private final SimpleJdbcInsert userInsert;
    private final ImportStatusRepository importStatusRepository;
    private final Set<CarValidator> carValidators;
    private final Set<RentValidator> rentValidators;
    private final Set<UserValidator> userValidators;

    public ImportStatusDto saveNewImport(String fileName, EntityType entity) {
        ImportStatus importStatus = importStatusRepository.saveAndFlush(new ImportStatus(fileName, entity));
        return ImportStatusDto.fromEntity(importStatus);
    }

    @SneakyThrows
    @Transactional
    public void uploadCsvToDb(byte[] file, long importStatusId, EntityType entityType) {
        log.info("importing " + entityType.toString().toLowerCase());
        //importStatusRepository.updateToProcessing(importStatusId, LocalDateTime.now());
        AtomicInteger counter = new AtomicInteger(0);
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((new ByteArrayInputStream(file))))) {
            bufferedReader
                    .lines()
                    .parallel()
                    .map(line -> line.split(","))
                    .map(args -> {
                                if (entityType == EntityType.CAR) {
                                    return toCarParameters(args);
                                } else if (entityType == EntityType.RENT) {
                                    return toRentParameters(args);
                                } else if (entityType == EntityType.USER) {
                                    return toUserParameters(args);
                                } else {
                                    throw new UnsupportedOperationException("ENTITY_NOT_SUPPORTED");
                                }
                            }
                    )
                    .peek(v -> counter.incrementAndGet())
                    .forEach(args -> {
                                if (entityType == EntityType.CAR) {
                                    validateCarInsertArguments(args);
                                    carInsert.execute(args);
                                } else if (entityType == EntityType.RENT) {
                                    validateRentInsertArguments(args);
                                    rentInsert.execute(args);
                                } else {
                                    validateUserInsertArguments(args);
                                    userInsert.execute(args);
                                }
                            }
                    );
        } catch (Exception e) {
            importStatusRepository.updateToFailed(importStatusId, ("Failed area in file's line " + counter.get()));
            throw new FileUploadException(e.getMessage());
        }
        importStatusRepository.updateToDone(importStatusId);
    }

    private Map<String, Object> toCarParameters(String[] args) {
        return Map.of("vin", args[0],
                "productionYear", Integer.parseInt(args[1]),
                "brand", args[2],
                "model", args[3],
                "mileage", Integer.parseInt(args[4]),
                "registration", args[5],
                "isAvailable", Boolean.getBoolean(args[6])
        );
    }

    private Map<String, Object> toRentParameters(String[] args) {
        return Map.of("datefrom", args[0],
                "dateto", args[1],
                "status", args[2],
                "carid", new BigInteger(args[3]),
                "userid", new BigInteger(args[4]),
                "startmileage", Integer.parseInt(args[5]),
                "endmileage", Integer.parseInt(args[6])
        );
    }

    private Map<String, Object> toUserParameters(String[] args) {
        return Map.of("name", args[0],
                "surname", args[1],
                "personid", args[2],
                "phonenumber", new BigInteger(args[3]),
                "email", args[4]
        );
    }

    private void validateCarInsertArguments(Map<String, Object> parameters) {
        List<String> errorCodes = carValidators.stream().map(v -> v.validate(parameters))
                .filter(r -> !r.isEmpty())
                .toList();
        if (!errorCodes.isEmpty())
            throw new InvalidInsertArgumentsException(errorCodes.stream().collect(Collectors.joining(",")));
    }

    private void validateRentInsertArguments(Map<String, Object> parameters) {
        List<String> errorCodes = rentValidators.stream().map(v -> v.validate(parameters))
                .filter(r -> !r.isEmpty())
                .toList();
        if (!errorCodes.isEmpty())
            throw new InvalidInsertArgumentsException(errorCodes.stream().collect(Collectors.joining(",")));
    }

    private void validateUserInsertArguments(Map<String, Object> parameters) {
        List<String> errorCodes = userValidators.stream().map(v -> v.validate(parameters))
                .filter(r -> !r.isEmpty())
                .toList();

        if (!errorCodes.isEmpty())
            throw new InvalidInsertArgumentsException(errorCodes.stream().collect(Collectors.joining(",")));
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
    public ImportStatusDto findById(long id) {
        return ImportStatusDto.fromEntity(importStatusRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

}
