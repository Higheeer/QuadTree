public class Rectangle {
    public Point position;
    public Dimension dimensions;

    public Rectangle(Point position, Dimension dimensions) {
        this.position = position;
        this.dimensions = dimensions;
    }

    public boolean contains(Point point) {
        return point.x >= position.x &&
                point.x <= position.x + dimensions.width &&
                point.y >= position.y &&
                point.y <= position.y + dimensions.height;
    }
}
