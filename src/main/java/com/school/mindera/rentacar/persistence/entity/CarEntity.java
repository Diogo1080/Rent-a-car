package com.school.mindera.rentacar.persistence.entity;

import com.school.mindera.rentacar.enumerators.CarBrands;
import com.school.mindera.rentacar.enumerators.CarSegment;
import com.school.mindera.rentacar.enumerators.EngineType;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cars")
public class CarEntity  extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private CarBrands brand;

    @Column(nullable = false)
    private String modelDescription;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private EngineType engineType;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private CarSegment carSegment;

    @Column(nullable = false)
    private Date dateOfPurchase;

    @Column(nullable = false, length = 8, unique = true)
    private String plate;

    @Column(nullable = false)
    private boolean available;

    @OneToMany(mappedBy = "carEntity")
    private List<RentEntity> rents;

}
