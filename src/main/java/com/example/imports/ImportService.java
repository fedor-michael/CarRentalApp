package com.example.imports;

import com.example.car.importvalidation.CarValidator;
import com.example.exception.EntityNotFoundException;
import com.example.imports.exception.FileUploadException;
import com.example.imports.exception.InvalidInsertArgumentsException;
import com.example.imports.model.ImportStatus;
import com.example.imports.model.ImportStatusDto;
import com.example.rent.importvalidation.RentValidator;
import com.example.user.importvalidation.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static com.example.imports.model.ImportStatus.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImportService {

    private static final int UPDATE_CONSTANT = 10_000;

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert carInsert;
    private final SimpleJdbcInsert rentInsert;
    private final SimpleJdbcInsert userInsert;
    private final Set<CarValidator> carValidators;
    private final Set<RentValidator> rentValidators;
    private final Set<UserValidator> userValidators;
    private final ImportStatusRepository importStatusRepository;

    public ImportStatusDto saveNewImport(String fileName, EntityType entity) {
        ImportStatus importStatus = importStatusRepository.saveAndFlush(new ImportStatus(fileName, entity));
        return ImportStatusDto.fromEntity(importStatus);
    }

    @SneakyThrows
    @Transactional
    @Async("importExecutor")
    public void uploadCsvToDb(byte[] file, long importStatusId, EntityType entity) {
        log.info("importing " + entity.toString().toLowerCase());
        importStatusRepository.updateToProcessing(importStatusId, LocalDateTime.now());
        AtomicInteger counter = new AtomicInteger(0);
        AtomicLong start = new AtomicLong(System.currentTimeMillis());

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((new ByteArrayInputStream(file))))) {
            bufferedReader
                    .lines()
                    .parallel()
                    .map(line -> line.split(","))
                    .map(args -> {
                        if (entity.equals(EntityType.CAR)) {
                            return toCarParameters(args);
                        } else if (entity.equals(EntityType.RENT)) {
                            return toRentParameters(args);
                        } else if (entity.equals(EntityType.USER)) {
                            return toUserParameters(args);
                        }
                        throw new RuntimeException("Entity " + entity + " is not in scope.");
                    })
                    .peek(map -> {
                        if (entity.equals(EntityType.CAR)) {
                            validateCarInsertArguments(map);
                        } else if (entity.equals(EntityType.RENT)) {
                            validateRentInsertArguments(map);
                        } else if (entity.equals(EntityType.USER)) {
                            validateUserInsertArguments(map);
                        }
                    })
                    .peek(line -> logTimeAndUpdateProcess(counter, start, importStatusId))
                    .forEach(v -> {
                        if (entity.equals(EntityType.CAR)) {
                            carSaveJdbc(v);
                        } else if (entity.equals(EntityType.RENT)) {
                            rentSaveJdbc(v);
                        } else if (entity.equals(EntityType.USER)) {
                            userSaveJdbc(v);
                        }
                    });
        } catch (Exception e) {
            importStatusRepository.updateProcess(importStatusId, counter.get());
            importStatusRepository.updateToFailed(importStatusId, ("Failed area in file's line " + counter.get()));
            throw new FileUploadException(e.getMessage());
        }

        importStatusRepository.updateProcess(importStatusId, counter.get());
        importStatusRepository.updateToDone(importStatusId);
    }

    private void carSaveJdbc(Map<String, Object> carParams) {  //todo przekminić cały update
        // car ma wypożyczenia
        //todo przekminić kwarente ale może akurat będzie działać
        long generatedCarId = carInsert.executeAndReturnKey(carParams).longValue();
//        jdbcTemplate.update("insert into authors_books (author_id, book_id) values (?, ?)", 1, generatedBookId);
//        jdbcTemplate.update("insert into authors_books (author_id, book_id) values (?, ?)", 2, generatedBookId);
    }

    private void rentSaveJdbc(Map<String, Object> rentParams) {
        // rent ma car i user
        long generatedBookId = rentInsert.executeAndReturnKey(rentParams).longValue();
        //todo przekminić kwarente bo raczej nie bedzie działać samo
//        jdbcTemplate.update("insert into authors_books (author_id, book_id) values (?, ?)", 1, generatedBookId);
//        jdbcTemplate.update("insert into authors_books (author_id, book_id) values (?, ?)", 2, generatedBookId);
    }

    private void userSaveJdbc(Map<String, Object> userParams) {
        // user ma wypożyczenia
        long generatedBookId = userInsert.executeAndReturnKey(userParams).longValue();
        //todo przekminić kwarente ale może akurat będzie działać
//        jdbcTemplate.update("insert into authors_books (author_id, book_id) values (?, ?)", 1, generatedBookId);
//        jdbcTemplate.update("insert into authors_books (author_id, book_id) values (?, ?)", 2, generatedBookId);
    }

    private Map<String, Object> toCarParameters(String[] args) {
        return Map.of("vin", args[0],
                "year", new BigDecimal(args[1]),
                "brand", args[2],
                "model", args[3],
                "mileage", new BigDecimal(args[4]),
                "registration", args[5],
                "available", args[6],
                // todo przypominajka: tu nie daje żadnych wypożyczeń
                "deleted", false);
    }

    private Map<String, Object> toRentParameters(String[] args) {
        return Map.of("datefrom", args[0],
                "dateto", args[1],
                "status", args[2],
                "carid", new BigDecimal(args[3]),
                "userid", new BigDecimal(args[4]),
                "startmileage", new BigDecimal(args[5]),
                "endmileage", new BigDecimal(args[6]),
                "deleted", false);
    }

    private Map<String, Object> toUserParameters(String[] args) {
        return Map.of("name", args[0],
                "surname", args[1],
                "personid", args[2],
                "phonenumber", new BigDecimal(args[3]),
                "email", new BigDecimal(args[4]),
                // todo przypominajka: tu nie daje żadnych wypożyczeń
                "deleted", false);
    }

    private void logTimeAndUpdateProcess(AtomicInteger counter, AtomicLong start, long importStatusId) {
        int elements = counter.incrementAndGet();
        if (elements % UPDATE_CONSTANT == 0) {
            log.info("Processed: {} in {} ms", elements, (System.currentTimeMillis() - start.get()));
            start.set(System.currentTimeMillis());
            importStatusRepository.updateProcess(importStatusId, elements);
        }
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
        return ImportStatusDto.fromEntity(importStatusRepository.findById(id).orElseThrow());
    }

}
