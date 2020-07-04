package com.example.warehousing.init;

class Bale {

    private String ID;
    private String rack;
    private int location;
    private int count;

    public Bale(String rack,int location,String ID,int count){
        setRack(rack);
        setLocation(location);
        setID(ID);
        setCount(count);
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getRack() {
        return rack;
    }

    public void setRack(String rack) {
        this.rack = rack;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
