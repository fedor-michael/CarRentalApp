package com.example.imports.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Table(name = "importStatus")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
//@Where(clause = "deleted = false")
//@SQLDelete(sql = "update importStatus set deleted = true where id = ?1") // todo zmieni pewnie się przy PostreSQL

public class ImportStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private EntityType entity;
    private LocalDateTime submitTime;
    private LocalDateTime startTime;
    private String fileName;
    @Enumerated(EnumType.STRING)
    private Status status;
    private int processed;
    private String failedReason;

    public ImportStatus(String fileName, EntityType entity) {
        this.fileName = fileName;
        this.entity = entity;
        status = Status.NEW;
        submitTime = LocalDateTime.now();
        processed = 0;
    }

    public enum Status {
        NEW, PROCESSING, SUCCESS, FAILED
    }

    public enum EntityType {
        CAR, RENT, USER
    }
}
