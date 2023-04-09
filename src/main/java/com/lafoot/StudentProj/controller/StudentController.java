package com.lafoot.StudentProj.controller;

import com.lafoot.StudentProj.exception.StudentNotFoundException;
import com.lafoot.StudentProj.model.Student;
import com.lafoot.StudentProj.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {

    @Autowired
    private StudentService service;

    @PostMapping("/")
    public ResponseEntity<String> saveStudent(@RequestBody Student student) {
        Student savedStudent = service.saveStudent(student);
        String response = String.format("Student with id %d is created ", savedStudent.getStudentId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<?> getAllStudents() {
        List<Student> students = service.fetchAll();
        if (students.size() <= 0) {
            return new ResponseEntity<>("Currently No Students are Registered...", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping(value = "/{sId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Student> getOneStudent(@PathVariable Long sId) {
        ResponseEntity<Student> response = null;
        try {
            Student student = service.fetchOneStudent(sId);
            response = new ResponseEntity<>(student, HttpStatus.OK);
        } catch (StudentNotFoundException ex) {
            ex.getMessage();
            throw ex;
        }
        return response;
    }

    @PutMapping(value = "/{sId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> updateStudent(@RequestBody Student student, @PathVariable Long sId) {
        Student updatedStudent = service.updateStudent(student, sId);
        return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{sId}", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> delete(@PathVariable Long sId) {
        String message = service.deleteStudentbyId(sId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
