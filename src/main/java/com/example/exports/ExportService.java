package com.example.exports;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedWriter;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExportService {

    private final EntitiesService entitiesService;
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void exportFromDatabase(Writer writer, String entityName) {
        Class<?> entityClass = entitiesService.getEntity(entityName);
        writeToCsv(writer, entityClass);
    }

    @SneakyThrows
    public <T> void writeToCsv(Writer writer, Class<T> entityClass) {
        String tableName = entitiesService.extractTableName(entityClass)
                .orElseThrow(EntityNotFoundException::new);
        try (BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            String query = "SELECT * FROM " + tableName + " LIMIT ? OFFSET ? orderby id";
            jdbcTemplate.query(query, (RowCallbackHandler) rse -> processJdbcRows(bufferedWriter, rse));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    private void processJdbcRows(BufferedWriter bufferedWriter, ResultSet rse) {
        ResultSetMetaData metaData = rse.getMetaData();
        for (int i = 1; i < metaData.getColumnCount(); i++) {
            bufferedWriter.write(rse.getString(i) + ",");
        }
        bufferedWriter.newLine();
    }

}
