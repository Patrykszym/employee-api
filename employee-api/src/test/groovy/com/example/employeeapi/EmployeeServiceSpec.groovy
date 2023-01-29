package com.example.employeeapi

import org.springframework.test.util.ReflectionTestUtils
import spock.lang.Specification

import java.time.LocalDate
import java.time.Month

class EmployeeServiceSpec extends Specification {
    private final EmployeeRepository employeeRepository = Mock(EmployeeRepository)
    private final EmployeeService employeeService = new EmployeeService(employeeRepository)

    def "create method should save employee to the repository"() {
        given:
        EmployeeInput input = new EmployeeInput(
                "John",
                "Doe",
                "Engineering",
                "john.doe@example.com",
                "555-555-5555",
                LocalDate.of(2020, Month.JANUARY, 1),
                BigDecimal.valueOf(100000)
        )

        when:
        employeeService.create(input)

        then:
        1 * employeeRepository.save(new Employee(
                "John",
                "Doe",
                "Engineering",
                "john.doe@example.com",
                "555-555-5555",
                LocalDate.of(2020, Month.JANUARY, 1),
                BigDecimal.valueOf(100000)
        ))
    }

    def "deleteById method should delete employee from the repository"() {
        given:
        UUID id = UUID.randomUUID()
        Employee employee = new Employee(
                "John",
                "Doe",
                "Engineering",
                "john.doe@example.com",
                "555-555-5555",
                LocalDate.of(2020, Month.JANUARY, 1),
                BigDecimal.valueOf(100000)
        )
        employeeRepository.findById(id) >> Optional.of(employee)

        when:
        employeeService.deleteById(id)

        then:
        1 * employeeRepository.delete(employee)
    }

    def "deleteById method should throw NotFoundException when employee not found"() {
        given:
        UUID id = UUID.randomUUID()
        employeeRepository.findById(id) >> Optional.empty()

        when:
        employeeService.deleteById(id)

        then:
        thrown(NotFoundException)
    }

    def "getById method should return employee from repository by id"() {
        given:
        UUID id = UUID.randomUUID()
        Employee employee = new Employee(
                "Jan",
                "Kowalski",
                "HR",
                "jan@jan.com",
                "555-555-5556",
                LocalDate.parse("2022-06-01"),
                BigDecimal.valueOf(22222.10)
        )
        ReflectionTestUtils.setField(employee, "id", id)
        employeeRepository.findById(id) >> Optional.of(employee)

        when:
        EmployeeOutput employeeOutput = employeeService.getById(id)

        then:
        employeeOutput.getId() == id
        employeeOutput.getFirstName() == "Jan"
        employeeOutput.getLastName() == "Kowalski"
        employeeOutput.getDepartment() == "HR"
        employeeOutput.getEmail() == "jan@jan.com"
        employeeOutput.getPhoneNumber() == "555-555-5556"
        employeeOutput.getHireDate() == LocalDate.parse("2022-06-01")
        employeeOutput.getSalary() == BigDecimal.valueOf(22222.10)
        employeeRepository.findById(id)
    }

    def "getById method should throw NotFoundException when employee not found"() {
        given:
        UUID id = UUID.randomUUID()
        employeeRepository.findById(id) >> Optional.empty()

        when:
        employeeService.getById(id)

        then:
        thrown(NotFoundException)
    }

    def "update method should find employee in the repository"() {
        given:
        UUID id = UUID.randomUUID()
        EmployeeInput input = new EmployeeInput(
                "John",
                "Doe",
                "Engineering",
                "john.doe@example.com",
                "555-555-5555",
                LocalDate.of(2020, Month.JANUARY, 1),
                BigDecimal.valueOf(100000)
        )
        Employee employee = new Employee(
                "John",
                "Doe",
                "Engineering",
                "john.doe@example.com",
                "555-555-5555",
                LocalDate.of(2020, Month.JANUARY, 1),
                BigDecimal.valueOf(100000)
        )
        ReflectionTestUtils.setField(employee, "id", id)
        employeeRepository.findById(id) >> Optional.of(employee)

        when:
        employeeService.update(id, input)

        then:
        employeeRepository.findById(id)
    }


}
