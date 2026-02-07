package com.manage.employee.mapper;

import com.manage.employee.dto.EmployeeDto;
import com.manage.employee.model.Employee;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


//Can be replaced with a external dependency - ModelMapper

@Component
public class MapperClass {

    public static EmployeeDto maptoEmployeeDto(Employee employee){
        EmployeeDto employeeDto = new EmployeeDto(
                employee.getId(),
                employee.getName(),
                employee.getId(),
                employee.getDept(),
                employee.getEmail()
        );
        return employeeDto;
    }

    public static Employee maptoEmployee(EmployeeDto employeeDto){
        Employee employee = new Employee(
                employeeDto.id(),
                employeeDto.name(),
                employeeDto.salary(),
                employeeDto.dept(),
                employeeDto.email()
        );
        return employee;
    }


//    public static List<EmployeeDto> maptoListEmployeeDto(List<Employee> employeeList){
//        List<EmployeeDto> employeeDtoList = employeeList.stream()
//                .map(EmployeeDto::new)
//                .collect(Collectors.toList());
//        return employeeDtoList;
//    }

}
