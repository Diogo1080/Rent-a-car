package com.school.mindera.rentacar.enumerators;

import java.math.BigDecimal;

/**
 * An enumerator for car segments
 */
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

    /**
     * Gets name of segment
     * @return segment name in String format
     */
    public String getName() {
        return name;
    }

    /**
     * Gets daily price of segment
     * @return daily price in BigDecimal
     */
    public BigDecimal getDailyPrice() {
        return dailyPrice;
    }
}
