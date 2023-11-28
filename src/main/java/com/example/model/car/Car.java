package com.example.model.car;

import com.example.model.rent.Rent;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cars")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"rents"})
@Builder
@Where(clause = "deleted = false")
@SQLDelete(sql = "update cars set deleted = true where id = ?1")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String vin;
    private Integer productionYear;
    private String brand;
    private String model;
    private Integer mileage;
    private String registration;
    private Boolean isAvailable;

    @OneToMany(mappedBy = "car")
    @Builder.Default
    private Set<Rent> rents = new HashSet<>();



}
