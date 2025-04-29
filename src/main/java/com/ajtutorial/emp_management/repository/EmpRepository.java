package com.ajtutorial.emp_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ajtutorial.emp_management.domain.Emp;

@Repository
public interface EmpRepository extends JpaRepository<Emp, Long> {

}