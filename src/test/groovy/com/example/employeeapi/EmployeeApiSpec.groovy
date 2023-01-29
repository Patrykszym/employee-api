package com.example.employeeapi

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import java.time.LocalDate

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [EmployeeApi])
class EmployeeApiSpec extends Specification {
    @TestConfiguration
    static class StubConfig {
        DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

        @Bean
        EmployeeService registrationService() {
            return detachedMockFactory.Stub(EmployeeService)
        }
    }
    @Autowired
    EmployeeService employeeService = Mock(EmployeeService)
    @Autowired
    MockMvc mockMvc

    def "create should call service and return created"() {
        given:
        EmployeeInput employee = new EmployeeInput(
                "Jan",
                "Kowalski",
                "HR",
                "jan@jan.com",
                "555-555-5556",
                LocalDate.parse("2022-06-01"),
                BigDecimal.valueOf(22222.10)
        )
        String requestBody = """
                {
                    "firstName": "Jan",
                    "lastName": "Kowalski",
                    "department": "HR",
                    "email": "jan@jan.com",
                    "phoneNumber": "555-555-5556",
                    "hireDate": "2022-06-01",
                    "salary": 22222.10
                }
            """
        employeeService.create(employee)

        when:
        def result = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))

        then:
        employeeService.create(employee)
        result.andExpect(status().isCreated())
    }

    def "deleteById should call service and return no content"() {
        given:
        UUID id = UUID.randomUUID()
        employeeService.deleteById(id)

        when:
        def result =  mockMvc.perform(delete("/api/employees/${id}"))

        then:
        employeeService.deleteById(id)
        result.andExpect(status().isNoContent())
    }

    def "findAll should call service and return ok"() {
        given:
        List<EmployeeOutput> employees = []
        employeeService.findAll() >> employees

        when:
        def result = mockMvc.perform(get("/api/employees"))

        then:
        employeeService.findAll()
        result.andExpect(status().isOk())
    }

    def "getById_shouldCallServiceAndReturnOk"() {
        given:
        UUID id = UUID.randomUUID()
        EmployeeOutput employee = new EmployeeOutput(id, "John", "Doe", "IT", "johndoe@example.com", "555-555-5555", LocalDate.now(), BigDecimal.valueOf(50000))
        employeeService.getById(id) >> employee

        when:
        def result = mockMvc.perform(get("/api/employees/${id}"))

        then:
        employeeService.getById(id)
        result.andExpect(status().isOk())
    }

    def "update should call service and return no content"() {
        given:
        UUID id = UUID.randomUUID()
        EmployeeInput employee = new EmployeeInput(
                "Jan",
                "Kowalski",
                "HR",
                "jan@jan.com",
                "555-555-5556",
                LocalDate.parse("2022-06-01"),
                BigDecimal.valueOf(22222.10)
        )
        String requestBody = """
                {
                    "firstName": "Jan",
                    "lastName": "Kowalski",
                    "department": "HR",
                    "email": "jan@jan.com",
                    "phoneNumber": "555-555-5556",
                    "hireDate": "2022-06-01",
                    "salary": 22222.10
                }
            """
        employeeService.update(id, employee)

        when:
        def result = mockMvc.perform(put("/api/employees/${id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))

        then:
        employeeService.update(id, employee)
        result.andExpect(status().isNoContent())
    }
}
