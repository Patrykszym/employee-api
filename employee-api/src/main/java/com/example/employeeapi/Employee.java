package com.example.employeeapi;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private final UUID id;
    private String firstName;
    private String lastName;
    private String department;
    private String email;
    private String phoneNumber;
    private LocalDate hireDate;
    private BigDecimal salary;

    Employee(
            String firstName,
            String lastName,
            String department,
            String email,
            String phoneNumber,
            LocalDate hireDate,
            BigDecimal salary
    ) {
        this.id = null;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.hireDate = hireDate;
        this.salary = salary;
    }

    void update(
            String firstName,
            String lastName,
            String department,
            String email,
            String phoneNumber,
            LocalDate hireDate,
            BigDecimal salary
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.hireDate = hireDate;
        this.salary = salary;
    }
}
