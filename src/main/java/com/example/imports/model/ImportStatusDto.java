package com.example.imports.model;

import java.time.LocalDateTime;

public record ImportStatusDto(long id, String entity, LocalDateTime submitTime,
                              String fileName, ImportStatus.Status status, String failedReason) {

    public static ImportStatusDto fromEntity(ImportStatus importStatus) {
        return new ImportStatusDto(importStatus.getId(),
                importStatus.getEntity().toString(),
                importStatus.getSubmitTime(),
                importStatus.getFileName(),
                importStatus.getStatus(),
                importStatus.getFailedReason());
    }

}