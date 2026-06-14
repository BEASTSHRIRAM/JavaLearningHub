package Lect3.inheritance;

public class BoxWeight extends Box {
    double weight;
    public BoxWeight(){
        this.weight=-1;
    }

    public BoxWeight(double l, double h, double w, double weight) {
        super(l, h, w);//what is this ? it says call the parent class constructor
        //used to initialize values present in parent class
        this.weight = weight;
    }
    BoxWeight(BoxWeight other) {
        super(other);
        this.weight = other.weight;
    }
}
