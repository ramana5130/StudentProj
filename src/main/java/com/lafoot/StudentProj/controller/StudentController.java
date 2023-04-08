package com.lafoot.StudentProj.controller;

import com.lafoot.StudentProj.model.Student;
import com.lafoot.StudentProj.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class StudentController {

    @Autowired
    private StudentService service;

    @PostMapping("/save")
    public ResponseEntity<String> saveStudent(@RequestBody Student student) {
        Student savedStudent = service.saveStudent(student);
        String response = "Student registered";
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/fetchall")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = service.fetchAll();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/fetchone/{sId}")
    public ResponseEntity<Student> getOneStudent(@PathVariable Long sId) {
        Student student = service.fetchOneStudent(sId);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PutMapping("/update/{sId}")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student, @PathVariable Long sId) {
        Student updatedStudent = service.updateStudent(student, sId);
        return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{sId}")
    public ResponseEntity<String> delete(@PathVariable Long sId) {
        String message = service.deleteStudentbyId(sId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
