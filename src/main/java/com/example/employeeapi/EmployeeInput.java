package com.example.employeeapi;


import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
