package Lect3.inheritance;

import Lect4.Access;

public class Box {
    private double l;
    double h;
    double w;
    public Box(){
        this.h=-1;
        this.l=-1;
        this.w=-1;
    }
    //cube
    Box(double side){
//        super(); //object class
    this.w=side;
    this.l=side;
    this.h=side;
    }
    Box(double l,double h,double w){
        this.l=l;
        this.h=h;
        this.w=w;
    }

    Box(Box old){
        this.h=old.h;
        this.l=old.l;
        this.w=old.w;

    }

//    public static void main(String[] args) {
//        Access a=new Access(34,"hi");
//        a.arr;
//    }
    public void infor(){
        System.out.println("Running the box");
    }
}


