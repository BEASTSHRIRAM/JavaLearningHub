package Lect4;

public class ObjectDemo {
    int num;
    public ObjectDemo(int num){
        this.num=num;
    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public static void main(String[] args) {
        ObjectDemo obj = new ObjectDemo(34);
        ObjectDemo obj2 = obj;
        System.out.println(obj.hashCode());
        System.out.println(obj2.hashCode());

    }
}
