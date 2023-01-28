package com.example.employeeapi;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class EmployeeService {

    private final EmployeeRepository employeeRepository;

    void create(EmployeeInput input) {
        employeeRepository.save(
                new Employee(
                        input.getFirstName(),
                        input.getLastName(),
                        input.getDepartment(),
                        input.getEmail(),
                        input.getPhoneNumber(),
                        input.getHireDate(),
                        input.getSalary()
                )
        );
    }

    void deleteById(UUID id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Employee with id: %s not found", id)));
        employeeRepository.delete(employee);
    }

    List<EmployeeOutput> findAll(EmployeeSearchCriteria employeeSearchCriteria) {
        return employeeRepository.findAll(createSpecification(employeeSearchCriteria))
                .stream()
                .map(emp -> new EmployeeOutput(
                        emp.getId(),
                        emp.getFirstName(),
                        emp.getLastName(),
                        emp.getDepartment(),
                        emp.getEmail(),
                        emp.getPhoneNumber(),
                        emp.getHireDate(),
                        emp.getSalary()
                ))
                .toList();
    }

    EmployeeOutput getById(UUID id) {
        return employeeRepository.findById(id)
                .map(emp -> new EmployeeOutput(
                        emp.getId(),
                        emp.getFirstName(),
                        emp.getLastName(),
                        emp.getDepartment(),
                        emp.getEmail(),
                        emp.getPhoneNumber(),
                        emp.getHireDate(),
                        emp.getSalary()
                ))
                .orElseThrow(() -> new NotFoundException(String.format("Employee with id: %s not found", id)));
    }

    @Transactional
    public void update(UUID id, EmployeeInput input) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Employee with id: %s not found", id)));
        employee.update(
                input.getFirstName(),
                input.getLastName(),
                input.getDepartment(),
                input.getEmail(),
                input.getPhoneNumber(),
                input.getHireDate(),
                input.getSalary()
        );
    }

    private Specification<Employee> createSpecification(EmployeeSearchCriteria criteria) {
        Specification<Employee> specification = Specification.where(null);
        if (criteria.getPhoneNumber().isPresent()) {
            specification = specification.and(hasPhoneNumber(criteria.getPhoneNumber().get()));
        }
        if (criteria.getEmail().isPresent()) {
            specification = specification.and(hasEmail(criteria.getEmail().get()));
        }
        if (criteria.getFirstName().isPresent()) {
            specification = specification.and(hasFirstName(criteria.getFirstName().get()));
        }
        if (criteria.getLastName().isPresent()) {
            specification = specification.and(hasLastName(criteria.getLastName().get()));
        }
        if (criteria.getDepartment().isPresent()) {
            specification = specification.and(hasDepartment(criteria.getDepartment().get()));
        }
        if (criteria.getLastName().isPresent()) {
            specification = specification.and(hasLastName(criteria.getLastName().get()));
        }
        if (criteria.getHireDateMin().isPresent()) {
            specification = specification.and(hireDateGreaterThanOrEqualTo(criteria.getHireDateMin().get()));
        }
        if (criteria.getHireDateMax().isPresent()) {
            specification = specification.and(hireDateLessThanOrEqualTo(criteria.getHireDateMax().get()));
        }
        if (criteria.getSalaryMin().isPresent()) {
            specification = specification.and(salaryGreaterThanOrEqualTo(criteria.getSalaryMin().get()));
        }
        if (criteria.getSalaryMax().isPresent()) {
            specification = specification.and(salaryLessThanOrEqualTo(criteria.getSalaryMax().get()));
        }
        return specification;
    }

    private Specification<Employee> hireDateGreaterThanOrEqualTo(LocalDate hireDateMin) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("hireDate"), hireDateMin);
    }

    private Specification<Employee> hireDateLessThanOrEqualTo(LocalDate hireDateMax) {
        return (root, query, builder) -> builder.lessThanOrEqualTo(root.get("hireDate"), hireDateMax);
    }

    private Specification<Employee> salaryGreaterThanOrEqualTo(BigDecimal salaryMin) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("salary"), salaryMin);
    }

    private Specification<Employee> salaryLessThanOrEqualTo(BigDecimal salaryMax) {
        return (root, query, builder) -> builder.lessThanOrEqualTo(root.get("salary"), salaryMax);
    }

    private Specification<Employee> hasFirstName(String firstName) {
        return (root, query, builder) -> builder.equal(root.get("firstName"), firstName);
    }

    private Specification<Employee> hasLastName(String lastName) {
        return (root, query, builder) -> builder.equal(root.get("lastName"), lastName);
    }

    private Specification<Employee> hasDepartment(String department) {
        return (root, query, builder) -> builder.equal(root.get("department"), department);
    }

    private Specification<Employee> hasEmail(String email) {
        return (root, query, builder) -> builder.equal(root.get("email"), email);
    }

    private Specification<Employee> hasPhoneNumber(String phoneNumber) {
        return (root, query, builder) -> builder.equal(root.get("phoneNumber"), phoneNumber);
    }
}
