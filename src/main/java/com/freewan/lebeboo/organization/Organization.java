package com.freewan.lebeboo.organization;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.Objects;

@Table(name = "organization")
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Organization {
    @Id
    @SequenceGenerator(name = "organization_seq", sequenceName = "organization_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "organization_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "customer_account_id")
    private String customerAccountId;
    private String name;

    private String email;

    private String address;

    private String description;

    private String phoneNumber;

    private LocalDate createdAt;

    private boolean verified;
    @Column(length = 10000)
    private String logo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Organization organization = (Organization) o;
        return getId() != null && Objects.equals(getId(), organization.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
