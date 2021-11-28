# Spring-Boot-POC






Setup Workspace
Create new starter project 
Add spring web, Lombok, Spring data jpa, mysql driver 
Optional on error - to forcing the build https://stackoverflow.com/questions/6021899/eclipse-maven-multiple-annotations-found-at-this-line/18800625
Optional on JRE - select project=> option(right click)=>  configure build path => Library => set jdk to one that you selected at project initialization
Configure mysql db 
Open application.properties file and add datasource and hibernate dialect properties 
spring.datasource.url=jdbc:mysql://localhost:3306/ems?useSSL=false
spring.datasource.username=root
spring.datasource.password=root
Hibernate properties 
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
Create, create-drop
spring.jpa.hibernate.ddl-auto=update
Jdbc driver 
Jdbc url 
Jdbc username 
Jdbc paw
Create package structure for your workspace 
Controller
Exception
Model
Repository
Service
Service.impl
Optional - Remember Controllers created outside the main package won’t work. So create all packages inside your main package e.g. (com.test.Main)
Run query on MySQL Workbench create database ems; Refresh and check the db is created in ems or not
Configure in spring boot project in application.properties and add /ems in datasource.url 
Configure mysql db 
Open application.properties file and add datasource and hibernate dialect properties 
spring.datasource.url=jdbc:mysql://localhost:3306/ems?useSSL=false
spring.datasource.username=root
spring.datasource.password=root@1234
#Hibernate properties 
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
#Create, create-drop
spring.jpa.hibernate.ddl-auto=update
Jdbc driver 
Jdbc url 
Jdbc username 
Jdbc paw
Now run your project and check everything work fine - Successfully setup 

Create JPA Entity

Create a model named Employee.java
@Data - lombok  => Add lombok class notation with a class that will create a Getter Setter automatically. Or create source from constructors, getter and setters short key 1, lombok reduce boilerplate code like getter, setter, constructor, toString ...
@Entity - javax.persistence - from persistence class that specify that class is an entity, jpa entity
@Table - javax.persistence - this annotation have property name that will define the table name, if not then it will automatically take the class name 
Id
Use @Id to create primary key 
Use @GenerateValue(strategy = GenerationType.IDENTITY) to mek it auto generated id of long type.
Lets define 
Firstname - now add column details for other fieldss
@Column(name= “first_name”, nullable = false)
Do this for other type as well 
@Column(name= “email”)
@Column(name= “last_name”)
Hibernate will create these properties automatically. Let’s run and see if the table is created in MySql or not. Open workbench tap on home and  tap on your sql workspace 
Done - we have successful created our employee jpa entity 

package com.poc.springboot.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="employee")
public class Employee{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="first_name", nullable = false)
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="email")
	private String email;

	
	
	public Employee() {
		super();
	}

	public Employee(long id, String firstName, String lastName, String email) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	

}

Create Repository
Name the interface EmoployeeRepository extends JPARepository<Employee, Long>
Theory - 
Reqpo is responsible to our CRUD operations
This is a interface extends to our JPARepository 
Spring Data JPA internally provides @Repository annotation so we don’t need to add @repository annotation to Employee Repository interface 
@transactional is also defined in jap repo 


Create Exception 
Create Exception class in Exception package name it ResourceNotFoundException that extends RuntimeException
Resource not found exception 
extends RuntimeException
Add annotation @ExceptionStatus(value = HttpStatus.NOT_FOUND)
private static final long serialVersionUID = 1L;
Add property private String resourceName,fieldName, Object fieldValue;
Generate constructors for all the fields and add default message with format
super(String.format(“%s not found with %s : ‘%s’”,resourceName, fieldName, fieldValue));
Generate getters 
Done 

package com.poc.springboot.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	private String resourceName,fieldName;
	private Object fieldValue;
	public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
		super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Object getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}
	
	
}

Create Service
Create new interface in Service package name EmployeeService - don’t forget to change package from com.poc.springboot.Service.impl to com.poc.springboot.Service
Create new class in Service.impl package name EmployeeServiceImpl
EmployeeServiceImpl Implement EmployeeService
@Service annotation EmployeeServiceImpl
Define a method in interface EmployeeService -  Employee saveEmployee(Employee employee);
Implement the method in class EmployeeServiceImpl
Theory 
In Employee impl - There are two type of dependency injection 
1. Setter based dependency injection 
2. Constructor based dependency injection 
We will use constructor base constructor based dependency 
Spring 4.3 if a class configured as a spring bean has only one constructor, @autowired can be omitted and spring will use that constructor and inject all necessary dependency 
Add auto generated method stub in impl file 
Create private EmployeeRepository employeeRepository; 
Create its constructor
Call save method from repository in override method and return Employee.


Create Controller

Create class EmployeeController in controller package 
Add @RestController annotation 
Theory 
RestController is a convenient annotation that combines @controller and @ResponseBody, which eliminates the need to annotation every request handling method of the controller class with the @Response/body annotation.
Create property for service - private EmployeeService employeeService; 
Create constructor 
Create endpoint method that will hold request and response paths - saveEmployee
Use return type as ResponseEntity - to add status and header automatically 
Add @postmaping and @RequestBody to this @postMapping public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) { 
return new object of ResponseEntity have two param service.save(employee), code as CREATED}


package com.poc.springboot.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poc.springboot.Model.Employee;
import com.poc.springboot.Service.EmployeeService;

@RestController
public class EmployeeController {
	private EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}
	
	@PostMapping("/save")
	public ResponseEntity<Employee> saveEmployee(Employee employee){
		return new ResponseEntity<Employee>(employeeService.saveEmployee(employee), HttpStatus.CREATED);
	}

}


PostMan 
Testing url 
http://localhost:8080/api/mobile/employee/test


http://localhost:8080/api/mobile/employee/save

Create api post type 
body raw type and JSON
Create body 
{
   "fristName":"akash",
   "lastName":"soni",
   "email":"akashsoni@gmail.com"
}

Hit api 
And hit 



{
   "timestamp": "2021-11-28T07:46:58.485+00:00",
   "status": 404,
   "error": "Not Found",
   "path": "/api/mobile/employee/save"
}

Further API creation Step 
Create Service 

Create service impl

Goto controller add method and path 

Done 


Get employee by id example 

Create Service 
	Employee findById(long id);


Create service impl
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

Goto controller add method and path 
	@GetMapping("{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long id){		
		return new ResponseEntity<Employee>(employeeService.findById(id), HttpStatus.OK);
	}


Done 
Update Employee by id

	Employee updateEmployeeById(Employee employee, long id);


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


	
	@PutMapping("{id}")
	public ResponseEntity<Employee> updateEmployeeById(@RequestBody Employee employee, @PathVariable("id") long id){
		return new ResponseEntity<Employee>(employeeService.updateEmployeeById(employee,id), HttpStatus.OK);
	}


Delete Employee by id
	Employee deleteEmployeeById(long id);

	@Override
	public Employee deleteEmployeeById(long id) {
		// TODO Auto-generated method stub
		Employee existingEmployee = employeeRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Employee", "Id", id));
		employeeRepository.delete(existingEmployee);
		return existingEmployee;
	}	
	
	@DeleteMapping("{id}")
	public ResponseEntity<Employee> deleteEmployeeById(@PathVariable("id") long id){		
		return new ResponseEntity<Employee>(employeeService.deleteEmployeeById(id), HttpStatus.OK);
	}









