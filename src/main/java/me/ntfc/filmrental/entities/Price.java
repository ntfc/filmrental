package me.ntfc.filmrental.entities;

public enum Price {
    BASIC(30.0),
    PREMIUM(40.0);

    private final double value;

    Price(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
