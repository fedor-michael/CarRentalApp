package com.example.service;

import com.example.model.importstatus.ImportStatus;
import com.example.model.importstatus.ImportStatusDto;

public interface ImportService {

    ImportStatusDto saveNewImport(String fileName, ImportStatus.EntityType entity);

    void uploadCsvToDb(byte[] file, long importStatusId, ImportStatus.EntityType entityType);

    ImportStatusDto findById(long id);

}
