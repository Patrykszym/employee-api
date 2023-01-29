package com.example.employeeapi;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Value
class EmployeeOutput {
    UUID id;
    String firstName;
    String lastName;
    String department;
    String email;
    String phoneNumber;
    LocalDate hireDate;
    BigDecimal salary;
}
