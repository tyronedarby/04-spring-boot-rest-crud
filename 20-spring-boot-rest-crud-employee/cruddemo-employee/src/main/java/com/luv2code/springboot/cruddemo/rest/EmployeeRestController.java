package com.luv2code.springboot.cruddemo.rest;

import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springboot.cruddemo.dao.EmployeeDAO;
import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.service.EmployeeService;

import jakarta.annotation.PostConstruct;
import tools.jackson.databind.json.JsonMapper;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {
	
	private EmployeeService employeeService;
	private JsonMapper jsonMapper;
	
	// inject employee dao
	@Autowired
	public EmployeeRestController(EmployeeService theEmployeeService, JsonMapper theJsonMapper) {
		employeeService = theEmployeeService;
		jsonMapper = theJsonMapper;
	}
	
	
	// expose "/employees" and return a list of employees
	@GetMapping("/employees")
	public List<Employee> findAll(){
		return employeeService.findAll();
	}
	
	// add mapping for GET  /employees/{employeeID}
	
	@GetMapping("/employees/{employeeId}")
	public Employee getEmployee(@PathVariable int employeeId) {
		
		Employee theEmployee = employeeService.findById(employeeId);
		
		if(theEmployee == null) {
			throw new RuntimeException("Employee id not found - " + employeeId);
		}
		return theEmployee;
	}
	
	// add mapping for POST /employees - add new employee
	@PostMapping("/employees")
	public Employee addEmployee(@RequestBody Employee theEmployee) {
		
		theEmployee.setId(0);
		
		Employee dbEmployee = employeeService.save(theEmployee);
		
		return dbEmployee;
		
	}
	
	// add mapping for PUT / employees - update
	@PutMapping("/employees")
	public Employee updateEmployee(@RequestBody Employee theEmployee) {
		
		Employee dbEmployee = employeeService.save(theEmployee);
		
		return dbEmployee;
	}
	
	// add mapping for PATCH / employees - update
	@PatchMapping("/employees/{employeeId}")
	public Employee patchEmployee(@PathVariable int employeeId, 
								  @RequestBody Map<String, Object> patchPayload) {
		
		Employee tempEmployee = employeeService.findById(employeeId);
		
		if(tempEmployee == null) {
			throw new RuntimeException("Employee id not found -  " + employeeId);
		}
		
		if(patchPayload.containsKey("id")) {
			throw new RuntimeException("Employee id not allow in request body -  " + employeeId);
		}
		
		Employee patchedEmployee = jsonMapper.updateValue(tempEmployee, patchPayload);
		
		Employee dbEmployee = employeeService.save(patchedEmployee);
		
		return dbEmployee;
	}
	
	// add mapping for DELTE / employees - delete
	@DeleteMapping("/employees/{employeeId}")
	public String deleteEmployee(@PathVariable int employeeId) {
		
		Employee tempEmployee = employeeService.findById(employeeId);
		
		if(tempEmployee == null) {
			throw new RuntimeException("Employee id not found -  " + employeeId);
		}
		
		employeeService.deleteById(employeeId);
		
		return "Delete employee id - " + employeeId;
	}
}
