package com.kakaopay.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Agency {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="institute_name")
    private String name;


    @Column(name="institute_code")
    private String code;


    public Agency(String institute_name, String institute_code){
        this.name = institute_name;
        this.code = institute_code;
    }
}
