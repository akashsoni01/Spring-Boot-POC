package com.poc.springboot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poc.springboot.Model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
