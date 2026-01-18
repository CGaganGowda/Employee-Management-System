package com.manage.employee.service;

import com.manage.employee.dto.EmployeeDto;
import com.manage.employee.exception.EmailAlreadyExistsException;
import com.manage.employee.exception.NameAlreadyExistsException;
import com.manage.employee.exception.ResourceNotFoundException;
import com.manage.employee.model.Employee;
import com.manage.employee.repo.EmployeeRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepo  employeeRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        Optional<Employee> optionalEmployee = employeeRepo.findByEmail(employeeDto.getEmail());
        //System.out.println(optionalEmployee);
        if(optionalEmployee.isPresent()){
            throw new EmailAlreadyExistsException("Email Already Exists");
        }

//        if(optionalEmployee.isPresent()){
//            throw new NameAlreadyExistsException("Name Already Exists");
//        }

       // Employee emp = mapperClass.maptoEmployee(employeeDto);
        Employee emp = modelMapper.map(employeeDto, Employee.class);
        Employee savedEmployee = employeeRepo.save(emp);
        // EmployeeDto savedEmployeeDto = mapperClass.maptoEmployeeDto(savedEmployee);
        return modelMapper.map(savedEmployee, EmployeeDto.class);
    }


    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employeeList = employeeRepo.findAll();
        //List<EmployeeDto> employeeDtoList = mapperClass.maptoListEmployeeDto(employeeList);
//        List<EmployeeDto> employeeDtoList = employeeList.stream()
//                .map((emp) -> modelMapper.map(emp, EmployeeDto.class))
//                .collect(Collectors.toList());
//        return employeeDtoList;

        return employeeList.stream()
                .map((emp) -> modelMapper.map(emp, EmployeeDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto getEmployee(Long id) {
        Employee emp = employeeRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee","id",id)
        );
        //EmployeeDto empDto = mapperClass.maptoEmployeeDto(emp);
        return modelMapper.map(emp,EmployeeDto.class);
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeDto employee) {
        //Employee emp = mapperClass.maptoEmployee(employee);
        Employee emp = modelMapper.map(employee, Employee.class);

        employeeRepo.findById(emp.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", emp.getId())
        );

        emp.setDept(employee.getDept());
        emp.setEmail(employee.getEmail());
        emp.setName(employee.getName());
        emp.setSalary(employee.getSalary());
        Employee savedEmployee = employeeRepo.save(emp);
        //EmployeeDto savedEmployeeDto = mapperClass.maptoEmployeeDto(savedEmployee);
        return modelMapper.map(savedEmployee, EmployeeDto.class);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee e = employeeRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", id)
        );
        employeeRepo.deleteById(id);
    }

    @Override
    public void deleteAllEmployees() {
        employeeRepo.deleteAll();
    }

}
