package Lect3.Polymorphism;

public class Circle extends Shapes{

    //this will run when ovj of circle is created
    //hence it is overriding the parent method
    @Override //this is called annotations
    void area(){
        System.out.println("area is pi*r*r");
    }
}
