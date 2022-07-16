package com.deep.employeeservice.service;

import java.util.List;

import com.deep.employeeservice.entity.Employee;


public interface EmployeeService {
	
	public Employee addEmployee(Employee employee);
	public List<Employee> getAllEmployees();
	public Employee updateEmployeeById(Integer id,Employee emp);
	public String deleteEmployeeById(Integer id);
}
