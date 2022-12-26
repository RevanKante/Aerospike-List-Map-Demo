package com.micronaut.entities;

import com.aerospike.mapper.annotations.AerospikeEmbed;
import com.aerospike.mapper.annotations.AerospikeKey;
import com.aerospike.mapper.annotations.AerospikeRecord;
import com.aerospike.mapper.annotations.AerospikeReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@AerospikeRecord(namespace = "test", set = "Employee")
public class Employee {

    @AerospikeKey
    private int id;

//    @NotBlank(message = "Name must be supplied")
    private String name;

//    @Min(18)
    private int age;


//    @NotNull(message = "Salary cannot be empty")
    private double salary;

//    @NotNull(message = "Please enter joining date")
    @JsonFormat(pattern = "dd-mm-yyyy")
    private Date joiningDate;

//    @NotBlank
    @Email(message = "Invalid Email Format")
    private String email;

    private List<String> contactNum;

    @AerospikeEmbed
//    @NotNull(message = "Please enter correct department details")
    private Department department;

    public Employee() {
    }

    public Employee(String name, int age, double salary, Date joiningDate, String email, List<String> contactNum, Department department) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.joiningDate = joiningDate;
        this.email = email;
        this.contactNum = contactNum;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getContactNum() {
        return contactNum;
    }

    public void setContactNum(List<String> contactNum) {
        this.contactNum = contactNum;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", joiningDate=" + joiningDate +
                ", email='" + email + '\'' +
                ", contactNum=" + contactNum +
                ", department=" + department +
                '}';
    }
}
