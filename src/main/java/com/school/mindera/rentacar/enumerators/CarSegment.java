package com.school.mindera.rentacar.enumerators;

import java.math.BigDecimal;

public enum CarSegment {
    SMALL("Small car", new BigDecimal("30")),
    SMALL_VAN("Small van", new BigDecimal("40")),
    FAMILY("Family car", new BigDecimal("50")),
    VAN("Commercial van", new BigDecimal("60")),
    PREMIUM("Premium car", new BigDecimal("120"));

    private String name;
    private BigDecimal dailyPrice;

    CarSegment(String name, BigDecimal pricePerDay) {
        this.name = name;
        this.dailyPrice = pricePerDay;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getDailyPrice() {
        return dailyPrice;
    }
}
