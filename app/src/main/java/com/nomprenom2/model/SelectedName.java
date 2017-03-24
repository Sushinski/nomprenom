package com.nomprenom2.model;


public class SelectedName {
    private String name = null;
    private boolean selected = false;

    public SelectedName(String name, boolean selected){
        super();
        this.name = name;
        this.selected = selected;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

}
