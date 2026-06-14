package Lect3.inheritance;

public class BoxLength extends Box {
    double length;
    public BoxLength(){
        this.length=-1;
    }

    public BoxLength(double l, double h, double w, double length) {
        super(l, h, w);
        this.length = length;
    }
}
