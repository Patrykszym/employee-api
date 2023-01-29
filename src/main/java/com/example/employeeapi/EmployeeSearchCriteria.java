package com.example.employeeapi;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Value
class EmployeeSearchCriteria {
    String firstName;
    String lastName;
    String department;
    String email;
    String phoneNumber;
    LocalDate hireDateMin;
    LocalDate hireDateMax;
    BigDecimal salaryMin;
    BigDecimal salaryMax;

    Optional<String> getFirstName() {
        return Optional.ofNullable(firstName);
    }

    Optional<String> getLastName() {
        return Optional.ofNullable(lastName);
    }

    Optional<String> getDepartment() {
        return Optional.ofNullable(department);
    }

    Optional<String> getEmail() {
        return Optional.ofNullable(email);
    }

    Optional<LocalDate> getHireDateMin() {
        return Optional.ofNullable(hireDateMin);
    }

    Optional<LocalDate> getHireDateMax() {
        return Optional.ofNullable(hireDateMax);
    }

    Optional<BigDecimal> getSalaryMin() {
        return Optional.ofNullable(salaryMin);
    }

    Optional<BigDecimal> getSalaryMax() {
        return Optional.ofNullable(salaryMax);
    }

    Optional<String> getPhoneNumber() {
        return Optional.ofNullable(phoneNumber);
    }
}
