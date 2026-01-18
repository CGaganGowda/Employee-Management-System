package com.manage.employee.dto;

import com.manage.employee.model.Employee;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    //--------------------VALIDATIONS-----------------------------
    //Validation Bean Annotations

    private Long id;

    @NotEmpty(message = "User Name should not be null or Empty")
    @Size(min = 1, max = 30)
    private String name;

    @NotNull
    @Min(1)
    @Positive
    private Long salary;
    @NotEmpty(message = "User Dept should not be null or Empty")
    private String dept;
    @Email(message = "Invalid Email ID")
    @NotEmpty(message = "User email should not be null or Empty")
    private String email;


//    public EmployeeDto(Employee employee) {
//        this.id = employee.getId();
//        this.name = employee.getName();
//        this.salary = employee.getSalary();
//        this.dept = employee.getDept();
//        this.email = employee.getEmail();
//    }
}
