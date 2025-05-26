package com.shop.entity;

public enum Category {
    PC("PC"),
    SMARTPHONE("스마트폰"),
    LAPTOP("노트북");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 