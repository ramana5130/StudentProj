package com.lafoot.StudentProj.service;

import com.lafoot.StudentProj.model.Student;

import java.util.List;

public interface StudentService {
    public Student saveStudent(Student student);

    public List<Student> fetchAll();

    public Student fetchOneStudent(Long sId);

    public Student updateStudent(Student student, Long sId);

    public String deleteStudentbyId(Long sId);
}
