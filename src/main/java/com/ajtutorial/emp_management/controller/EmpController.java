package com.ajtutorial.emp_management.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajtutorial.emp_management.domain.Emp;
import com.ajtutorial.emp_management.repository.EmpRepository;

@RestController
@RequestMapping("/emp")
public class EmpController {

	@Autowired
	private EmpRepository employeeRepository;

	@GetMapping("/getAll")
	public List<Emp> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@GetMapping("/getById/{id}")
	public ResponseEntity<Emp> getEmployeeById(@PathVariable(value = "id") Long employeeId) throws Exception {
		Emp employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new Exception("Employee not found for this id :: " + employeeId));
		return ResponseEntity.ok().body(employee);
	}

	@Transactional
	@PostMapping("/create")
	public Emp createEmployee(@RequestBody Emp employee) {
		return employeeRepository.save(employee);
	}

	@PutMapping("/updateById/{id}")
	public ResponseEntity<Emp> updateEmployee(@PathVariable(value = "id") Long employeeId,
			@RequestBody Emp employeeDetails) throws Exception {
		Emp employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new Exception("Employee not found for this id :: " + employeeId));

		employee.setEmailId(employeeDetails.getEmailId());
		employee.setLastName(employeeDetails.getLastName());
		employee.setFirstName(employeeDetails.getFirstName());
		final Emp updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}

	@DeleteMapping("/deleteById/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId) throws Exception {
		Emp employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new Exception("Employee not found for this id :: " + employeeId));

		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
