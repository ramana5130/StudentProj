package com.lafoot.StudentProj.service;

import com.lafoot.StudentProj.exception.DuplicateRecordException;
import com.lafoot.StudentProj.exception.StudentNotFoundException;
import com.lafoot.StudentProj.model.Address;
import com.lafoot.StudentProj.model.Student;
import com.lafoot.StudentProj.repo.AddressRepository;
import com.lafoot.StudentProj.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository repository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Student saveStudent(Student student) throws DuplicateRecordException {

        if (repository.existsByStudentId(student.getStudentId())) {
            throw new DuplicateRecordException("Student with ID " + student.getStudentId() + " already exists");
        } else {
            return repository.save(student);
        }
    }

    @Override
    public List<Student> fetchAll() {
        return repository.findAll();
    }

    @Override
    public Student fetchOneStudent(Long sId) {
        Student student = repository.findById(sId).orElseThrow(() -> new StudentNotFoundException("Student with id " + sId + " not found"));
        return student;
    }

    @Override
    public Student updateStudent(Student student, Long sId) {
        Student existigStudent = repository.findById(sId).orElseThrow(() -> new StudentNotFoundException("Student with id " + sId + "not found"));
        existigStudent.setName(student.getName());
        existigStudent.setAge(student.getAge());
        //existigStudent.setAddresses(student.getAddresses());

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
                existingAddress.setStudent(existigStudent);
            } else {
                updatedAddress.setStudent(existigStudent);
                existingAddresses.add(updatedAddress);
            }
        }
        existigStudent.setAddresses(existingAddresses);
        return repository.save(existigStudent);
    }

    @Override
    public String deleteStudentbyId(Long sId) {
        Student student = repository.findById(sId).orElseThrow(() -> new StudentNotFoundException("Student with id " + sId + " not found"));
        repository.delete(student);
        return String.format("Student with id %d is deleted ", sId);
    }
}
