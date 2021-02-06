package com.myutils.utils;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Employee {
	
	private Integer id;
	
	private String empName;
	
	private String phone;
	
	private String unitName;
	
	private String workType;
	
	private BigDecimal salary;
}
