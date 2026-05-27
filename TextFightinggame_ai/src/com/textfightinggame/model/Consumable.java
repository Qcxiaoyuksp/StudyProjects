package com.textfightinggame.model;

public class Consumable {
    private final String name;
    private final int num;

    public Consumable(String name, int num) {
        this.name = name;
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public int getNum() {
        return num;
    }
}

