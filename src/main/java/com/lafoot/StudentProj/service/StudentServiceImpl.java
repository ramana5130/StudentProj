package com.lafoot.StudentProj.service;

import com.lafoot.StudentProj.exception.StudentNotFoundException;
import com.lafoot.StudentProj.model.Student;
import com.lafoot.StudentProj.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository repository;

    @Override
    public Student saveStudent(Student student) {
        return repository.save(student);
    }

    @Override
    public List<Student> fetchAll() {
        return repository.findAll();
    }

    @Override
    public Student fetchOneStudent(Long sId) {
        return repository.findById(sId).get();
    }

    @Override
    public Student updateStudent(Student student, Long sId) {
        Student existigStudent = repository.findById(sId).orElseThrow(() -> new StudentNotFoundException("Student with id " + sId + "not found"));
        existigStudent.setName(student.getName());
        existigStudent.setAge(student.getAge());
        existigStudent.setAddresses(student.getAddresses());

        return repository.save(existigStudent);
    }

    @Override
    public String deleteStudentbyId(Long sId) {
        Student student = repository.findById(sId).orElseThrow(() -> new StudentNotFoundException("Student with id " + sId + "not found"));
        repository.delete(student);
        return "Student Deleted";
    }
}
