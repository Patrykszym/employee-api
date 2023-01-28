package com.example.employeeapi;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
class EmployeeApi {

    private final EmployeeService employeeService;

    @PostMapping
    public void create(@RequestBody @Valid EmployeeInput input) {
        employeeService.create(input);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        employeeService.deleteById(id);
    }

    @GetMapping
    public List<EmployeeOutput> findAll(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) LocalDate hireDateMin,
            @RequestParam(required = false) LocalDate hireDateMax,
            @RequestParam(required = false) BigDecimal salaryMin,
            @RequestParam(required = false) BigDecimal salaryMax
    ) {
        return employeeService.findAll(
                new EmployeeSearchCriteria(
                        firstName,
                        lastName,
                        department,
                        email,
                        phoneNumber,
                        hireDateMin,
                        hireDateMax,
                        salaryMin,
                        salaryMax
                )
        );
    }

    @GetMapping("/{id}")
    public EmployeeOutput getById(@PathVariable UUID id) {
        return employeeService.getById(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable UUID id, @Valid @RequestBody EmployeeInput input) {
        employeeService.update(id, input);
    }
}
