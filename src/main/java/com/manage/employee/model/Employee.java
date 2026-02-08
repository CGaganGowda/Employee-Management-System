package com.manage.employee.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;
    @Column(nullable = false)
    private Long salary;
    @Column(nullable = false)
    private String dept;
    @Column(nullable = false, unique = true)
    private String email;
}
