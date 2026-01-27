package com.manage.employee.repo;

import com.manage.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    //2 Custom methods for validating Employee email (name - optional --- for partners). Org email has to be unique.
    Optional<Employee> findByEmail(String email);
    Optional<Employee> findByName(String name);
}
