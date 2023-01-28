GET http://localhost:8080/api/employees?salaryMin=60000&salaryMax=76000&hireDateMax=2021-06-01
GET http://localhost:8080/api/employees/c1a8d8f9-b5e1-4a9c-88c2-f6f9033f9a7b
POST http://localhost:8080/api/employees
{
"firstName": "Jan",
"lastName": "Kowalski",
"department": "HR",
"email": "jan@jan.com",
"phoneNumber": "555-555-5556",
"hireDate": "2022-06-01",
"salary": 22222.10
}

PUT http://localhost:8080/api/employees/{id}
{
"firstName": "Janina",
"lastName": "Kowalska",
"department": "IT",
"email": "janina@test.com",
"phoneNumber": "555-522-5556",
"hireDate": "2022-05-22",
"salary": 22222.15
}
