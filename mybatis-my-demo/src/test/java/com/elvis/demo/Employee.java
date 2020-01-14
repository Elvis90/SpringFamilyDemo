package com.elvis.demo;

import java.util.Objects;

/**
 * @author Elvis
 * @create 2020-01-13 11:19
 */
public class Employee {
    private String name;

    private Double salary;

    private Integer age;

    private Status status;

    private String dep;

    public Employee(String name, Double salary, Integer age, Status status,String dep) {
        this.name = name;
        this.salary = salary;
        this.age = age;
        this.status = status;
        this.dep = dep;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(name, employee.name) &&
                Objects.equals(salary, employee.salary) &&
                Objects.equals(age, employee.age) &&
                status == employee.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, salary, age, status);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", age=" + age +
                ", status=" + status +
                ", dep='" + dep + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public enum Status{
        FREE,BUSY,VACATION;
    }
}
