package com.tcg.training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcg.training.entity.Employee;
import com.tcg.training.entity.EmployeeId;
import com.tcg.training.repository.EmployeeRepository;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee saved = employeeRepository.save(employee);  // performs insert
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
    
    @GetMapping("/getEmployee/{companyId}/{employeeNumber}")
    public ResponseEntity<Employee> getEmployeeById(
            @PathVariable Long companyId,
            @PathVariable Long employeeNumber) {
        EmployeeId employeeId = new EmployeeId(companyId, employeeNumber);
        return employeeRepository.findById(employeeId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/deleteEmployee/{companyId}/{employeeNumber}")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable Long companyId,
            @PathVariable Long employeeNumber) {
        EmployeeId employeeId = new EmployeeId(companyId, employeeNumber);
        if (employeeRepository.existsById(employeeId)) {
            employeeRepository.deleteById(employeeId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

