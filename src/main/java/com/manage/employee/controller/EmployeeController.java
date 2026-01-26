package com.manage.employee.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.manage.employee.dto.EmployeeDto;
import com.manage.employee.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Tag(
        name = "REST API for EMS",
        description = "CRUD API FOR EMS"
)
@RestController
@RequestMapping("api/employees")
public class EmployeeController {

    //Field Dependency Injection - Constructor injection is preferred
    @Autowired
    private EmployeeService employeeService;

    @Operation(
            summary = "CREATE API",
            description = "CREATING A EMPLOYEE"
    )
    @ApiResponse(
            responseCode = "201",
            description = "EMPLOYEE CREATED"
    )
    @PostMapping("create")
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody @Valid EmployeeDto employee){
        //System.out.println("createEmployee "+employee);
        EmployeeDto createdEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @Operation(
            summary = "RETRIEVE API",
            description = "RETRIEVE A EMPLOYEE"
    )
    @ApiResponse(
            responseCode = "200",
            description = "EMPLOYEE FOUND"
    )
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(){
        List<EmployeeDto> employeeList = employeeService.getAllEmployees();
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    @Operation(
            summary = "RETRIEVE API",
            description = "RETRIEVE ALL EMPLOYEES"
    )
    @ApiResponse(
            responseCode = "200",
            description = "EMPLOYEES FOUND"
    )
    @GetMapping("get/{id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable Long id){
        EmployeeDto employee = employeeService.getEmployee(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @Operation(
            summary = "UPDATE API",
            description = "UPDATE A EMPLOYEE"
    )
    @ApiResponse(
            responseCode = "200",
            description = "EMPLOYEE UPDATED"
    )
    @PutMapping("update/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody @Valid EmployeeDto employee,@PathVariable Long id){
        employee.setId(id);
        EmployeeDto updatedEmployee = employeeService.updateEmployee(employee);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    @Operation(
            summary = "DELETE API",
            description = "DELETE A EMPLOYEE"
    )
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>("Employee deleted with id: "+id, HttpStatus.OK);
    }

    @Operation(
            summary = "DELETE API",
            description = "DELETE ALL EMPLOYEES"
    )
    @DeleteMapping("deleteAll")
    public ResponseEntity<String> deleteAllEmployees(){
        employeeService.deleteAllEmployees();
        return new ResponseEntity<>("All employees have been deleted", HttpStatus.OK);
    }

//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){
//        ErrorDetails errorDetails = new ErrorDetails(
//                LocalDateTime.now(),
//                exception.getMessage(),
//                webRequest.getDescription(true),
//                "USER_NOT_FOUND"
//        );
//        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
//    }

}
