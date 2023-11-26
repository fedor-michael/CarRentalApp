package com.example.model.user;

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
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"rents"})
@Builder
@Where(clause = "deleted = false")
@SQLDelete(sql = "update users set deleted = true where id = ?1")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String personId;
    private Long phoneNumber;
    private String email;
    private boolean deleted;

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private Set<Rent> rents = new HashSet<>();

}
