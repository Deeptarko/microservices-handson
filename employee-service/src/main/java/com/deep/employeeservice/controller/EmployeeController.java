package com.deep.employeeservice.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.deep.employeeservice.entity.Employee;
import com.deep.employeeservice.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeService empService;
	
	
	
	@PostMapping("/save")
	public ResponseEntity<Employee> saveEmployee(@RequestBody Employee emp) {
		
		Employee empSaved=empService.addEmployee(emp);
		return new ResponseEntity<Employee>(empSaved, HttpStatus.CREATED);
		
	}
	
	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> getAllEmployees(){
		List<Employee>empList=empService.getAllEmployees();
		return new ResponseEntity<List<Employee>>(empList, HttpStatus.ACCEPTED);
		
	}
	
	@PutMapping("/{empId}")
	public ResponseEntity<Employee>updateEmployeeById(@PathVariable Integer empId,@RequestBody Employee emp){
		Employee updatedEmployee=empService.updateEmployeeById(empId,emp);
		return new ResponseEntity<Employee>(updatedEmployee, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{empId}")
	public ResponseEntity<String>deleteEmployeeById(@PathVariable Integer empId){
		
		String res=empService.deleteEmployeeById(empId);
		return new ResponseEntity<String>(res, HttpStatus.ACCEPTED);
	}

}
