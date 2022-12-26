package com.micronaut.services;

import com.micronaut.entities.Employee;
import com.micronaut.repositories.EmployeeRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class EmployeeService {

    @Inject
    EmployeeRepository repository;
    public String addEmployee(Employee employee) {
        return repository.addEmployee(employee);
    }

    public List<Employee> getEmployees() {
        return repository.getEmployees();
    }

    public Employee getEmpById(int id) {
        return repository.getEmpById(id);
    }

    public String updateDetails(int id, Employee employee) {
        return repository.updateDetails(id, employee);
    }

    public String deleteById(int id) {
        return repository.deleteById(id);
    }

    public String bulkInsert() {
        return repository.bulkInsert();
    }

    public String bulkDelete() {
        return repository.bulkDelete();
    }
}
