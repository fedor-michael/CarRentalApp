package com.example.imports.model;

import java.time.LocalDateTime;

public record ImportStatusDto(long id, String entity, LocalDateTime submitTime, LocalDateTime startTime,
                              String fileName, ImportStatus.Status status, int processed, String failedReason) {

    public static ImportStatusDto fromEntity(ImportStatus importStatus) {
        return new ImportStatusDto(importStatus.getId(),
                importStatus.getEntity().toString(),
                importStatus.getSubmitTime(),
                importStatus.getStartTime(),
                importStatus.getFileName(),
                importStatus.getStatus(),
                importStatus.getProcessed(),
                importStatus.getFailedReason());
    }

}