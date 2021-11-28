package com.poc.springboot.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poc.springboot.Model.Employee;
import com.poc.springboot.Service.EmployeeService;

@RestController
@RequestMapping("/api/mobile/employees")
public class EmployeeController {
	private EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}
	
	@GetMapping("test")
	public ResponseEntity<String> saveEmployee(){
		return new ResponseEntity<String>("Working", HttpStatus.OK);
	}

	@PostMapping("save")
	public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee){		
		return new ResponseEntity<Employee>(employeeService.saveEmployee(employee), HttpStatus.CREATED);
	}
	

	@GetMapping
	public List<Employee> getEmployee(){
		return employeeService.getAllEmployee();

	}
	
	@GetMapping("{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long id){		
		return new ResponseEntity<Employee>(employeeService.findById(id), HttpStatus.OK);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Employee> updateEmployeeById(@RequestBody Employee employee, @PathVariable("id") long id){
		return new ResponseEntity<Employee>(employeeService.updateEmployeeById(employee,id), HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Employee> deleteEmployeeById(@PathVariable("id") long id){		
		return new ResponseEntity<Employee>(employeeService.deleteEmployeeById(id), HttpStatus.OK);
	}
	
}
