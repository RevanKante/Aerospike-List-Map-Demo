package com.micronaut.repositories;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.BatchDelete;
import com.aerospike.client.BatchRecord;
import com.aerospike.client.Key;
import com.aerospike.mapper.tools.AeroMapper;
import com.github.javafaker.Faker;
import com.micronaut.configuration.AeroMapperConfiguration;
import com.micronaut.entities.Department;
import com.micronaut.entities.Employee;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Singleton
public class EmployeeRepository {

    @Inject
    AeroMapperConfiguration mapper;

    //AeroMapper mapper1 = mapper.getMapper();
    public String addEmployee(Employee employee) {
        String result;
        try {
            Random random = new Random();

            Employee newEmployee = new Employee();
            newEmployee.setId(random.nextInt(1000));
            newEmployee.setAge(employee.getAge());
            newEmployee.setName(employee.getName());
            newEmployee.setContactNum(employee.getContactNum());
            newEmployee.setEmail(employee.getEmail());
            newEmployee.setSalary(employee.getSalary());
            newEmployee.setJoiningDate(employee.getJoiningDate());
            newEmployee.setDepartment(employee.getDepartment());

            mapper.getMapper().save(newEmployee);
            result = "Employee created successfully id is "+newEmployee.getId();
        }
        catch (Exception e) {
            e.printStackTrace();
            result = "Failed to register";
        }
        return  result;
    }

    public List<Employee> getEmployees() {
        return mapper.getMapper().scan(Employee.class);
    }

    public String updateDetails(int id, Employee employee) {
        Employee emp = getEmpById(id);
        if (emp != null) {
            emp.setId(id);
            emp.setAge(employee.getAge());
            emp.setName(employee.getName());
            emp.setContactNum(employee.getContactNum());
            emp.setEmail(employee.getEmail());
            emp.setSalary(employee.getSalary());
            emp.setJoiningDate(employee.getJoiningDate());
            emp.setDepartment(employee.getDepartment());

            mapper.getMapper().save(emp);

            return "Employee information updated successfully ";
        } else {
            return "Please enter correct id, update failed";
        }
    }

    public String deleteById(int id) {
        String result = "";
        try {
            Employee employee = this.getEmpById(id);
            if (employee != null) {
                mapper.getMapper().delete(Employee.class,id);
                result = "Employee having ID : " +id+" deleted successfully";
            }
            else {
                result = "Failed to delete employee, Please enter valid ID ";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            result = "Failed to delete employee, Please enter valid ID ";
        }
        return result;
    }

    public Employee getEmpById(int id) {
        return mapper.getMapper().read(Employee.class, id);
    }

    public String bulkInsert() {
        AeroMapper mapper1 = mapper.getMapper();
        Faker faker = new Faker();
        Random random = new Random();

        Format format = new SimpleDateFormat("dd-MM-yyyy");

        for (int i=1; i <= 100; i++) {
            String email = faker.name().firstName()+"@gmail.com";
            String departmentName = faker.job().field();
            List<String> contactNos = Arrays.asList(faker.phoneNumber().subscriberNumber(10), faker.phoneNumber().subscriberNumber(10), faker.phoneNumber().subscriberNumber(10));

            Employee employee = new Employee(faker.name().firstName(), ThreadLocalRandom.current().nextInt(19, 48), ThreadLocalRandom.current().nextDouble(27000, 95000) , faker.date().birthday(1,15), email, contactNos, new Department(1, departmentName));
            employee.setId(i);
            mapper1.save(employee);
        }
        return "Added records";
    }

    public String bulkDelete() {
        AerospikeClient client = mapper.getClient();
        String result;
        try {
            Key [] keys = new Key[101];
            for (int i=1; i <= 100; i++) {
                keys[i] = new Key("test", "mapset", i);
            }
            List<BatchRecord> records = new ArrayList<BatchRecord>();
            for (int j=1; j <= 100; j++) {
                records.add(new BatchDelete(new Key("test", "Employee",j)));
            }
            client.operate(null, records);
            result = "Records deleted successfully";
        }
        catch (Exception e) {
            result = "Failed to delete records";
        }
        return result;
    }
}
