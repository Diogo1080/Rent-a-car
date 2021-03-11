package com.school.mindera.rentacar.persistence.entity;


import lombok.*;
import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rent")
public class RentEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carId", nullable = false)
    private CarEntity carEntity;

    @Column(nullable=false)
    private Date expectedBeginDate;

    @Column(nullable=false)
    private Date expectedEndDate;

    @Column(nullable=false)
    private double expectedPrice;

    private Date beginDate;

    private Date endDate;

    private double finalPrice;
}
