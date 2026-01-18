package com.manage.employee.mapper;

import com.manage.employee.dto.EmployeeDto;
import com.manage.employee.model.Employee;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
                employeeDto.getId(),
                employeeDto.getName(),
                employeeDto.getSalary(),
                employeeDto.getDept(),
                employeeDto.getEmail()
        );
        return employee;
    }

//    public static List<EmployeeDto> maptoListEmployeeDto(List<Employee> employeeList){
//        List<EmployeeDto> employeeDto = employeeList.stream()
//                .map(
//                        employee -> {
//                            EmployeeDto dto = new EmployeeDto();
//                            dto.setId(employee.getId());
//                            dto.setName(employee.getName());
//                            dto.setSalary(employee.getSalary());
//                            dto.setDept(employee.getDept());
//                            dto.setEmail(employee.getEmail());
//                            return dto;
//                        }
//                )
//                .collect(Collectors.toList());
//        return employeeDto;
//    }

//    public static List<EmployeeDto> maptoListEmployeeDto(List<Employee> employeeList){
//        List<EmployeeDto> employeeDtoList = employeeList.stream()
//                .map(EmployeeDto::new)
//                .collect(Collectors.toList());
//        return employeeDtoList;
//    }

}
