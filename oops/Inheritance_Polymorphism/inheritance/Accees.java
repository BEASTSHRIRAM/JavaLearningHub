package Lect3.inheritance;

import Lect4.Access;

public class Accees extends Access {
    public Accees(int num, String name) {
        super(num, name);
    }
    public static void main(String[] args) {
        Accees n=new Accees(34,"hi");
        System.out.println(n.name);

//        Access n = new Access(34, "hi");
//        System.out.println(n.name);   // ❌Not works
//
//        Why?
//
//                Because you're accessing via Access object reference.
//
//        Even though you're in subclass file,
//        you're not accessing through inherited member.
    }
}
