public class Rectangle {
    public Point origin;
    public int width, height;

    public Rectangle() {
        this.origin = new Point(0, 0);
        this.width = 0;
        this.height = 0;
    }

    public boolean contains(Point point) {
        return point.x >= origin.x &&
                point.x <= origin.x + width &&
                point.y >= origin.y &&
                point.y <= origin.y + height;
    }
}
