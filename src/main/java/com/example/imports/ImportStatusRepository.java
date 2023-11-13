package com.example.imports;

import com.example.imports.model.ImportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface ImportStatusRepository extends JpaRepository<ImportStatus, Long> {

    @Transactional
    @Modifying
    @Query("update ImportStatus is set is.status=com.example.imports.model.ImportStatus$Status.PROCESSING, is.startTime = ?2 where is.id = ?1")
    void updateToProcessing(long importStatusId, LocalDateTime now);

    @Transactional
    @Modifying
    @Query("update ImportStatus is set is.processed = ?2 where is.id = ?1")
    void updateProcess(long importStatusId, int elements);


    @Transactional
    @Modifying
    @Query("update ImportStatus is set is.status = com.example.imports.model.ImportStatus$Status.SUCCESS where is.id = ?1")
    void updateToDone(long importStatusId);


    @Async("processUpdateExecutor")
    @Transactional
    @Modifying
    @Query("update ImportStatus is set is.status = com.example.imports.model.ImportStatus$Status.FAILED, is.failedReason = ?2 where is.id = ?1")
    void updateToFailed(long importStatusId, String failedReason);


}
