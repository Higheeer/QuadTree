import java.util.Vector;

public class QuadTree {
    private final int capacity;
    private final Vector<Point> points;

    private final Point origin;
    private final Dimension dimensions;
    private final Rectangle boundaries;

    public QuadTree[] kids;
    private boolean isDivided;

    public QuadTree(Point origin, Dimension dimensions) {
        kids = new QuadTree[4];
        isDivided = false;

        capacity = 10;
        points = new Vector<>();

        this.dimensions = dimensions;
        this.origin = origin;

        boundaries = new Rectangle(new Point(origin.x - dimensions.width / 2, origin.y - dimensions.height / 2), new Dimension(dimensions.width, dimensions.height));
    }

    public void insert(Point point) {
        if (boundaries.contains(point)) {
            if (isDivided) {
                insertToKid(point);
            } else {
                if (exceededCapacity()) {
                    divide();
                    relocatePoints();
                    insertToKid(point);
                } else {
                    insertToParent(point);
                }
            }
        }
    }

    private void insertToParent(Point point) {
        points.add(point);
    }

    private void insertToKid(Point point) {
        for (QuadTree kid : kids) {
            kid.insert(point);
        }
    }

    private boolean exceededCapacity() {
        return points.size() >= capacity;
    }

    private void divide() {
        kids[0] = new QuadTree(new Point(origin.x - dimensions.width / 4, origin.y - dimensions.height / 4), new Dimension(dimensions.width / 2, dimensions.height / 2));
        kids[1] = new QuadTree(new Point(origin.x - dimensions.width / 4, origin.y + dimensions.height / 4), new Dimension(dimensions.width / 2, dimensions.height / 2));
        kids[2] = new QuadTree(new Point(origin.x + dimensions.width / 4, origin.y + dimensions.height / 4), new Dimension(dimensions.width / 2, dimensions.height / 2));
        kids[3] = new QuadTree(new Point(origin.x + dimensions.width / 4, origin.y - dimensions.height / 4), new Dimension(dimensions.width / 2, dimensions.height / 2));

        isDivided = true;
    }

    private void relocatePoints() {
        for (Point point : points) {
            for (QuadTree kid : kids) {
                kid.insert(point);
            }
        }
        points.clear();
        points.setSize(0);
    }

    public void display() {
        System.out.println(points);
        if (isDivided) {
            for (QuadTree i : kids) {
                i.display();
            }
        }
    }
}
