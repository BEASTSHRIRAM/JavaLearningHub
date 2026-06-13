package Lect4;

public class Subclass extends Access{
    public Subclass(int num,String age){
        super(num,age);
    }

    public static void main(String[] args) {
        Subclass sub=new Subclass(34,"19");
        System.out.println(sub instanceof Access);
        System.out.println(sub.getClass());

    }
}
