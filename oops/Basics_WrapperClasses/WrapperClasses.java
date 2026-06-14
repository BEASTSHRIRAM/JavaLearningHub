public class WrapperClasses {
    public static void main(String[] args) {
        //converting a character to primitive like using it like an object
        int a=10;
        int b=20;
        Integer num=45;
        swap(a, b);
        System.out.println(a+ " "+b);
        final int bonus=2;//cannot be modified
        //this wont swap because Integer is a final class
        final A sriram =new A("Kunal Kushwaha");
        sriram.name="other name";
        //when a non primitive is final ,you cannot reassign it
  //      sriram =new A("new object");

    }
    static void swap(int a,int b){
        int temp=a;
        a=b;
        b=temp;
    }
}
class A{
    final int num=23;//because it says it is not initialezed and fional variables have to be initialized 
    //always initialize it while declaring it
    //final guarantees that this immutability ,only when intstance variables are primitive datatypes not refernece types
    String name;
    public A(String name){
        this.name=name;
    }
}
