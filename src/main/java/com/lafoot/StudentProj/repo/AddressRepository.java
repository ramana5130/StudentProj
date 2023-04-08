package com.lafoot.StudentProj.repo;

import com.lafoot.StudentProj.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
