package com.example.warehousing;

class ModuleOrder {

    private String orderID;
    private String clothingID;
    private int count;

    public ModuleOrder(String orderID,String clothingID,String count){
        setOrderID(orderID);
        setClothingID(clothingID);
        setCount(Integer.parseInt(count));
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getClothingID() {
        return clothingID;
    }

    public void setClothingID(String clothingID) {
        this.clothingID = clothingID;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
