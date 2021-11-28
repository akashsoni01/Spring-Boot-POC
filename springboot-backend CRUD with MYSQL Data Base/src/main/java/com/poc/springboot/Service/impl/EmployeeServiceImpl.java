package com.poc.springboot.Service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.poc.springboot.Exception.ResourceNotFoundException;
import com.poc.springboot.Model.Employee;
import com.poc.springboot.Repository.EmployeeRepository;
import com.poc.springboot.Service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	private EmployeeRepository employeeRepository;
	
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}

	@Override
	public Employee saveEmployee(Employee employee) {
		// TODO Auto-generated method stub
		return employeeRepository.save(employee);
	}

	@Override
	public List<Employee> getAllEmployee() {
		// TODO Auto-generated method stub
		return employeeRepository.findAll();
	}

	@Override
	public Employee findById(long id) {
		// TODO Auto-generated method stub
		/*
		Optional<Employee> employee = employeeRepository.findById(id);
		if (employee.isPresent()){
			return employee.get();
		}else {
			throw new ResourceNotFoundException("Employee", "Id", employee);
		} 
		 * */
		
		// or use lambda function
		
		return employeeRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Employee", "Id", id));
	}

	@Override
	public Employee updateEmployeeById(Employee employee, long id) {
		// TODO Auto-generated method stub
		Employee existingEmployee = employeeRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Employee", "Id", id));
		existingEmployee.setFirstName(employee.getFirstName());
		existingEmployee.setLastName(employee.getLastName());
		existingEmployee.setEmail(employee.getEmail());
		employeeRepository.save(existingEmployee);
		return existingEmployee;
	}

	@Override
	public Employee deleteEmployeeById(long id) {
		// TODO Auto-generated method stub
		Employee existingEmployee = employeeRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Employee", "Id", id));
		employeeRepository.delete(existingEmployee);
		return existingEmployee;
	}	
	
	
}
