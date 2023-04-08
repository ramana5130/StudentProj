package com.lafoot.StudentProj.service;

import com.lafoot.StudentProj.exception.StudentNotFoundException;
import com.lafoot.StudentProj.model.Address;
import com.lafoot.StudentProj.model.Student;
import com.lafoot.StudentProj.repo.AddressRepository;
import com.lafoot.StudentProj.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository repository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Student saveStudent(Student student) {
        List<Address> addresses = student.getAddresses();
        for (Address address : addresses) {
            address.setStudent(student);
        }
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
        /*Student existigStudent = repository.findById(sId).orElseThrow(() -> new StudentNotFoundException("Student with id " + sId + "not found"));
        existigStudent.setName(student.getName());
        existigStudent.setAge(student.getAge());
        //existigStudent.setAddresses(student.getAddresses());

        List<Address> existingAddresses = existigStudent.getAddresses();
        List<Address> updatedAddresses = student.getAddresses();

        for (Address updatedAddress : updatedAddresses) {
            Optional<Address> optionalExistingAddress = addressRepository.findById(updatedAddress.getAddressId());
            if (optionalExistingAddress.isPresent()) {
                Address existingAddress = optionalExistingAddress.get();
                existingAddress.setCity(updatedAddress.getCity());
                existingAddress.setCountry(updatedAddress.getCountry());
                existingAddress.setStudent(existigStudent);
                existingAddresses.add(existingAddress);
            }
        }

        // Remove any Address entities that are no longer associated with the Student
        List<Address> removedAddresses = new ArrayList<>();
        for (Address existingAddress : existingAddresses) {
            boolean isPresent = false;
            for (Address updatedAddress : updatedAddresses) {
                if (existingAddress.getAddressId().equals(updatedAddress.getAddressId())) {
                    isPresent = true;
                    break;
                }
            }
            if (!isPresent) {
                removedAddresses.add(existingAddress);
            }
        }
        existingAddresses.removeAll(removedAddresses);

        existigStudent.setAddresses(existingAddresses);*/

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
