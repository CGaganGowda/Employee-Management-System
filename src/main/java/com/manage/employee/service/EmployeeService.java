package com.manage.employee.service;

import com.manage.employee.dto.EmployeeDto;
import com.manage.employee.model.Employee;

import java.util.List;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto employee);
    List<EmployeeDto> getAllEmployees();
    EmployeeDto getEmployee(Long id);
    EmployeeDto updateEmployee(EmployeeDto employee);
    void deleteEmployee(Long id);
    void deleteAllEmployees();
}
