package Lect3.inheritance;

public class Main {

    public static void main(String[] args) {
//        Box box=new Box(2,3,4);
//
//        System.out.println(box.l+" "+box.w+" "+box.h);
//        BoxWeight box3=new BoxWeight();
//        BoxWeight box4=new BoxWeight(2,3,4,8);
//        System.out.println(box3.weight + " "+ box3.weight);
    Box box5=new BoxWeight(2,3,4,8);
        System.out.println(box5.w);

        //there are many variables in both parent and child classes
        //if u are given access to variables that are in the ref type i.e BoxWeight
        //hence,you should have access to weight variable
        //this also means ,that the ones you are trying to access should be initialized
        //but the obj is itself the type parent class,how will you call the constructor of child class
        //this is why error
//        BoxWeight box6 =new Box(2,3,4);
//        System.out.println(box6);
    }
}