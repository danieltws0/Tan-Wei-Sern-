package com.example.asus.diceit;

public class dice {
    private long id;
    private String randnum;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return randnum;
    }

    public void setNumber(String randnum) {
        this.randnum = randnum;
    }

    //Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString(){
        return randnum;
    }
}
