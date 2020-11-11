package com.company;
//public Object clone()throws CloneNotSupportedException{
//        return (Employee)super.clone();
//    }
public class Orders {
    private int start;
    private int end;
    private int value;
    private int id;
    public Orders(int start, int end, int value){
        this.start = start;
        this.end = end;
        this.value = value;
    }
    public Orders(int start, int end, int value, int id){
        this.start = start;
        this.end = end;
        this.value = value;
        this.id = id;
    }
    public int getStart(){
        return start;
    }
    public int getEnd(){
        return end;
    }
    public int getValue(){
        return value;
    }
    public int getID(){return id;}
    public String toString(){
        return "Poczatek: " + start + ",koniec: " + end + ", wartość: " + value;
    }
    public void giveID(int id){
        this.id = id;
    }
}

