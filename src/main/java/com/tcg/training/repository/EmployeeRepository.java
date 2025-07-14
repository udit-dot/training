package com.tcg.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcg.training.entity.Employee;
import com.tcg.training.entity.EmployeeId;

public interface EmployeeRepository extends JpaRepository<Employee, EmployeeId> {

}
