package com.example.employeeapi;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
class EmployeeInput {
    @NotBlank
    String firstName;
    @NotBlank
    String lastName;
    @NotBlank
    String department;
    @NotBlank
    @Email
    String email;
    @NotBlank
    String phoneNumber;
    @NotNull
    LocalDate hireDate;
    @NotNull
    BigDecimal salary;
}
