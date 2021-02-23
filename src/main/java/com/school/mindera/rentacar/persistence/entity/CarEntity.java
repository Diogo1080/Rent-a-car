package com.school.mindera.rentacar.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cars")
public class CarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String  brand;

    @Column(nullable = false)
    private String modelDescription;

    @Column(nullable = false)
    private String engineType;

    @Column(nullable = false)
    private String carSegment;

    @Column(nullable = false)
    private String year;

    @Column(nullable = false)
    private String plate;
}
