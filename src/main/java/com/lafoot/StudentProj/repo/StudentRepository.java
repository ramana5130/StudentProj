package com.lafoot.StudentProj.repo;

import com.lafoot.StudentProj.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
