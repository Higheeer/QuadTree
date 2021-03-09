import java.util.Vector;

public class QuadTree {
    private final int capacity;
    private final Point origin;
    private final Dimension dimensions;
    private final Rectangle boundaries;
    public QuadTree[] kids;
    private final Vector<Point> points;
    private boolean isDivided;

    public QuadTree(Point origin, Dimension dimensions) {
        kids = new QuadTree[4];
        kids[0] = null;
        kids[1] = null;
        kids[2] = null;
        kids[3] = null;

        isDivided = false;

        capacity = 10;
        points = new Vector<>();

        this.dimensions = dimensions;
        this.origin = origin;

        boundaries = new Rectangle();
        boundaries.origin.x = origin.x - dimensions.width / 2;
        boundaries.origin.y = origin.y - dimensions.height / 2;
        boundaries.width = dimensions.width;
        boundaries.height = dimensions.height;
    }

    public void insert(Point point) {
        if (boundaries.contains(point)) {
            if (!isDivided) {
                if (points.size() < capacity) {
                    points.add(point);
                } else {
                    divide();
                    relocatePoints();
                    insert(point);
                }
            } else {
                for (QuadTree i : kids) {
                    i.insert(point);
                }
            }
        }
    }

    private void divide() {
        kids[0] = new QuadTree(new Point(origin.x / 2, origin.y / 2), new Dimension(dimensions.width / 2, dimensions.height / 2));
        kids[1] = new QuadTree(new Point(origin.x / 2, origin.y + origin.y / 2), new Dimension(dimensions.width / 2, dimensions.height / 2));
        kids[2] = new QuadTree(new Point(origin.x + (origin.x / 2), origin.y + origin.y / 2), new Dimension(dimensions.width / 2, dimensions.height / 2));
        kids[3] = new QuadTree(new Point(origin.x + (origin.x / 2), origin.y / 2), new Dimension(dimensions.width / 2, dimensions.height / 2));

        isDivided = true;
    }

    private void relocatePoints() {
        if (isDivided) {
            for (Point x : points) {
                for (QuadTree y : kids) {
                    y.insert(x);
                }
            }
            points.clear();
            points.setSize(0);
        }
    }

    public void display() {
        System.out.println("Parent Origin: " + origin.x + ' ' + origin.y + ' ' + points);

        if (isDivided) {
            for (QuadTree i : kids) {
                i.display();
            }
        }
    }
}
