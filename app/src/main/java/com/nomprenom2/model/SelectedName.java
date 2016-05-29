package com.nomprenom2.model;


public class SelectedName {
    String name = null;
    boolean selected = false;

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

    public boolean isSelected(){
        return this.selected;
    }

    public void setSelected(boolean selected){
        this.selected = selected;
    }
}
