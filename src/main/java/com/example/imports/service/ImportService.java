package com.example.imports.service;

import com.example.imports.model.ImportStatus;
import com.example.imports.model.ImportStatusDto;

import java.util.Map;

public interface ImportService {

    ImportStatusDto saveNewImport(String fileName, ImportStatus.EntityType entity);

    void uploadCsvToDb(byte[] file, long importStatusId, ImportStatus.EntityType entityType);

    ImportStatusDto findById(long id);

}
