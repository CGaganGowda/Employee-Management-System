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

        Employee emp = modelMapper.map(employeeDto, Employee.class);
        Optional<Employee> optionalEmployee = employeeRepo.findByEmail(emp.getEmail());
        //System.out.println(optionalEmployee);
        if(optionalEmployee.isPresent()){
            throw new EmailAlreadyExistsException("Email Already Exists");
        }
        Employee savedEmployee = employeeRepo.save(emp);
        return modelMapper.map(savedEmployee, EmployeeDto.class);
    }


    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employeeList = employeeRepo.findAll();
        return employeeList.stream()
                .map((emp) -> modelMapper.map(emp, EmployeeDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto getEmployee(Long id) {
        Employee emp = employeeRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee","id",id)
        );
        return modelMapper.map(emp,EmployeeDto.class);
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeDto employee) {
        Employee emp = modelMapper.map(employee, Employee.class);

        employeeRepo.findById(emp.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", emp.getId())
        );

        emp.setDept(employee.getDept());
        emp.setEmail(employee.getEmail());
        emp.setName(employee.getName());
        emp.setSalary(employee.getSalary());
        Employee savedEmployee = employeeRepo.save(emp);
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
