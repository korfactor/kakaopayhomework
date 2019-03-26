package com.kakaopay.homework.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="housefund")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
public class HouseFund {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private Integer year;
    @Column
    private Integer month;

    @Column
    private String bank;

    @Column
    private Long budget;


}
