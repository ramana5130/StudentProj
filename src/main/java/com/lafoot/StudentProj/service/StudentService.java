package com.lafoot.StudentProj.service;

import com.lafoot.StudentProj.exception.DuplicateRecordException;
import com.lafoot.StudentProj.model.Student;

import java.util.List;

public interface StudentService {
    public Student saveStudent(Student student) throws DuplicateRecordException;

    public List<Student> fetchAll();

    public Student fetchOneStudent(Long sId);

    public Student updateStudent(Student student, Long sId);

    public String deleteStudentbyId(Long sId);
}
