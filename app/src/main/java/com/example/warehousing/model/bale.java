package com.example.warehousing.model;

public class bale {

    private String id;
    private char rack;
    private int location;
    private int number;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public char getRack() {
        return rack;
    }

    public void setRack(char rack) {
        this.rack = rack;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
