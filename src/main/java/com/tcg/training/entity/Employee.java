package com.tcg.training.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@IdClass(EmployeeId.class)
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
	@EqualsAndHashCode.Include
	@Id
	@Column(name = "company_id")
	private Long companyId;
	@EqualsAndHashCode.Include
	@Id
	@Column(name = "employee_number")
	private Long employeeNumber;

	@Column(name = "name")
	private String name;

}
