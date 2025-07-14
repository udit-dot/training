package com.tcg.training.entity;

import java.io.Serializable;

public class EmployeeId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long companyId;
	private Long employeeNumber;

	public EmployeeId() {
	}

	public EmployeeId(Long companyId, Long employeeNumber) {
		this.companyId = companyId;
		this.employeeNumber = employeeNumber;
	}

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof EmployeeId)) return false;
//        EmployeeId that = (EmployeeId) o;
//        return Objects.equals(companyId, that.companyId) &&
//               Objects.equals(employeeNumber, that.employeeNumber);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(companyId, employeeNumber);
//    }
}
