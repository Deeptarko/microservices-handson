package com.deep.employeeservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deep.employeeservice.entity.Employee;
import com.deep.employeeservice.repository.EmployeeRepository;
import com.deep.employeeservice.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeRepository empRepository;

	@Override
	public Employee addEmployee(Employee employee) {
		return empRepository.save(employee);
	}

	@Override
	public List<Employee> getAllEmployees() {
		return empRepository.findAll();
	}

	@Override
	public Employee updateEmployeeById(Integer id,Employee emp) {
		Optional<Employee> empSaved=empRepository.findById(id);
		if(empSaved.isPresent()) {
			
			Employee empUpdated=empSaved.get();
			empUpdated.setName(emp.getName());
			empUpdated.setDepartment(emp.getDepartment());
			return empRepository.save(empUpdated);
			
		}else {
			throw new IllegalArgumentException("Invalid Username");
		}
	}

	@Override
	public String deleteEmployeeById(Integer id) {
		empRepository.deleteById(id);
		return "Employee Deleted Successfully";
	}
	
	
	
}
