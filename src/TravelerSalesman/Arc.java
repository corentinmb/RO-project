package TravelerSalesman;

public class Arc {
    private int X;
    private int Y;

    public Arc(int X, int Y){
        this.X = X;
        this.Y = Y;
    }

    public int getX() {
        return this.X;
    }

    public Arc setX(int X) {
        this.X = X;
        return this;
    }

    public int getY() {
        return this.Y;
    }

    public Arc setY(int Y) {
        this.Y = Y;
        return this;
    }
}
