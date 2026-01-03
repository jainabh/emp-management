package com.ajtutorial.emp_management.controller;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/emp")
public class EmpController {

	@Autowired
	private EmpRepository employeeRepository;

	@GetMapping("/healthcheck")
	public String healthcheck() throws Exception{
		final byte[] bytes = InetAddress.getLocalHost().getAddress();
		final String address = IntStream.range(0, bytes.length).mapToObj(index -> bytes[index] & 0xff)
				.map(Number::toString).collect(Collectors.joining("."));
		return address;
	}
	
	@GetMapping("/healthcheck1")
	public String healthcheck1() throws Exception{
		final byte[] bytes = InetAddress.getLocalHost().getAddress();
		final String address = IntStream.range(0, bytes.length).mapToObj(index -> bytes[index] & 0xff)
				.map(Number::toString).collect(Collectors.joining("."));
		return address;
	}

	@GetMapping("/getAll")
	public List<Emp> getAllEmployees() {
		log.info("teststs" + LocalDateTime.now());
		return employeeRepository.findAll();
	}

	@GetMapping("/getById/{id}")
	public ResponseEntity<Emp> getEmployeeById(@PathVariable(value = "id") Long employeeId) throws Exception {
		log.info("teststs" + LocalDateTime.now());
		Emp employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new Exception("Employee not found for this id :: " + employeeId));
		return ResponseEntity.ok().body(employee);
	}

	@Transactional
	@PostMapping("/create")
	public Emp createEmployee(@RequestBody Emp employee) {
		log.info("teststs" + LocalDateTime.now());
		return employeeRepository.save(employee);
	}

	@PutMapping("/updateById/{id}")
	public ResponseEntity<Emp> updateEmployee(@PathVariable(value = "id") Long employeeId,
			@RequestBody Emp employeeDetails) throws Exception {
		log.info("teststs" + LocalDateTime.now());
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
		log.info("teststs" + LocalDateTime.now());
		Emp employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new Exception("Employee not found for this id :: " + employeeId));

		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
