package Lect3.inheritance;

public class BoxHeight extends Box {
    double height;
    public BoxHeight(){
        this.height=-1;
    }

    public BoxHeight(double l, double h, double w, double height) {
        super(l, h, w);
        this.height = height;
    }
}
