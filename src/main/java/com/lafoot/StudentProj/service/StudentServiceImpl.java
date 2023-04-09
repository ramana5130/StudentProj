package com.lafoot.StudentProj.service;

import com.lafoot.StudentProj.exception.StudentNotFoundException;
import com.lafoot.StudentProj.model.Address;
import com.lafoot.StudentProj.model.Student;
import com.lafoot.StudentProj.repo.AddressRepository;
import com.lafoot.StudentProj.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    //@Transactional
    public Student updateStudent(Long sId, Student student) {

        /*Student existingStudent = repository.findById(sId).orElseThrow(() -> new StudentNotFoundException("Student with id " + sId + " not found"));
        existingStudent.setName(student.getName());
        existingStudent.setAge(student.getAge());

        List<Address> updatedAddresses = student.getAddresses();
        List<Address> existingAddresses = existingStudent.getAddresses();

        for (Address updatedAddress : updatedAddresses) {
            Optional<Address> optionalExistingAddress = addressRepository.findById(updatedAddress.getAddressId());
            if (optionalExistingAddress.isPresent()) {
                // Update existing address with new information
                Address existingAddress = optionalExistingAddress.get();
                existingAddress.setCity(updatedAddress.getCity());
                existingAddress.setCountry(updatedAddress.getCountry());
            } else {
                // Add new address to list of existing addresses for the student
                updatedAddress.setStudent(existingStudent);
                existingAddresses.add(updatedAddress);
            }
        }
        existingStudent.setAddresses(existingAddresses);
        return repository.save(existingStudent);*/

        Student existingStudent = repository.findById(sId)
                .orElseThrow(() -> new StudentNotFoundException("Student with id " + sId + " not found"));

        existingStudent.setName(student.getName());
        existingStudent.setAge(student.getAge());


        List<Address> existingAddresses = addressRepository.findByStudentId(sId);
        List<Address> updatedAddresses = student.getAddresses();

        for (Address updatedAddress : updatedAddresses) {
            Optional<Address> optionalExistingAddress = existingAddresses.stream()
                    .filter(a -> a.getAddressId().equals(updatedAddress.getAddressId()))
                    .findFirst();

            if (optionalExistingAddress.isPresent()) {
                Address existingAddress = optionalExistingAddress.get();
                existingAddress.setCity(updatedAddress.getCity());
                existingAddress.setCountry(updatedAddress.getCountry());
                existingAddress.setStudent(existingStudent);
            } else {
                updatedAddress.setStudent(existingStudent);
                existingAddresses.add(updatedAddress);
            }
        }

        existingStudent.setAddresses(student.getAddresses());
        return repository.save(existingStudent);
    }

    @Override
    public String deleteStudentbyId(Long sId) {
        Student student = repository.findById(sId).orElseThrow(() -> new StudentNotFoundException("Student with id " + sId + "not found"));
        repository.delete(student);
        return "Student Deleted";
    }
}
