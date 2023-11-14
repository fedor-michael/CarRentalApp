package com.example.rent.model;

import com.example.car.model.Car;
import com.example.user.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Entity
@Table(name = "rents")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@Where(clause = "deleted = false")
@SQLDelete(sql = "update rents set deleted = true where id = ?1")
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    @Enumerated(EnumType.STRING)
    private RentStatus status;
    @ManyToOne
    @JoinColumn(name = "carId")
    private Car car;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    private Integer startMileage;
    private Integer endMileage;
    private boolean deleted;

    @Override
    public String toString() {
        return "Rent{" +
                "id=" + id +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", active=" + status +
                ", car=" + car.getBrand() + " " + car.getModel() + " " + car.getRegistration() +
                ", user=" + user.getName() + " " + user.getSurname() +
                ", startMileage=" + startMileage +
                ", finishMileage=" + endMileage +
                ", deleted=" + deleted +
                '}';
    }


}
