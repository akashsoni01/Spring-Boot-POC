package com.poc.springboot.Service;

import java.util.List;

import com.poc.springboot.Model.Employee;


public interface EmployeeService {
	Employee saveEmployee(Employee employee);
	List<Employee> getAllEmployee();
	Employee findById(long id);
	Employee updateEmployeeById(Employee employee, long id);
	Employee deleteEmployeeById(long id);
}
