package com.micronaut.controllers;

import com.micronaut.entities.Employee;
import com.micronaut.services.EmployeeService;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

import javax.validation.Valid;
import java.util.List;

@Controller("/employee")
public class EmployeeController {

    @Inject
    EmployeeService employeeService;

    @Post("/add")
    public String addEmployee(@Body Employee employee) {
       return employeeService.addEmployee(employee);
    }

    @Get("/getall")
    public List<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    @Get("/get/{id}")
    public Employee getById(@PathVariable int id) {
        return employeeService.getEmpById(id);
    }

    @Put("/update/{id}")
    public String updateDetails(@PathVariable int id, @Body Employee employee) {
        return employeeService.updateDetails(id,employee);
    }

    @Delete("/delete/{id}")
    public String deleteById(@PathVariable int id) {
        return employeeService.deleteById(id);
    }

    @Get("/bulkInsert")
    public String bulkInsert() {
        return employeeService.bulkInsert();
    }

    @Delete("/bulkdelete")
    public String bulkDelete() {
        return employeeService.bulkDelete();
    }
}
