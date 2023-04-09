package com.lafoot.StudentProj.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;
    private String name;
    private int age;

    @JsonManagedReference
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "student")
    private List<Address> addresses;
}
