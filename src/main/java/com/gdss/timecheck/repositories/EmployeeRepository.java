package com.gdss.timecheck.repositories;

import com.gdss.timecheck.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    Employee findByPis(String pis);

}
