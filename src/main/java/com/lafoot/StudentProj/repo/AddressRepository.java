package com.lafoot.StudentProj.repo;

import com.lafoot.StudentProj.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("SELECT a FROM Address a WHERE a.student.studentId = ?1")
    List<Address> findByStudentId(Long sId);
}
