package com.school.mindera.rentacar.enumerators;

public enum EngineType {
    PETROL("Petrol"),
    DIESEL("Diesel"),
    HYBRID("Hybrid"),
    PLUGIN_HYBRID("Plug-in Hybrid"),
    ELECTRIC("Electric");

    private String name;

    EngineType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
