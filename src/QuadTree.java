import java.util.Vector;

public class QuadTree {
    private final static int capacity = 10;
    private Vector<Point> points;
    private final QuadTree[] kids;
    private int amountOfPoints = 0;
    public static int sumOfAllPoints = 0;

    private final Point origin;
    private final Dimension dimension;
    private final Boundaries boundaries;

    private final int divideDepth;
    private boolean isDivided;
    private final static int maxDepth = 5;

    public QuadTree(int divideDepth, Point origin, Dimension dimension) {
        this.kids = new QuadTree[4];
        this.isDivided = false;
        this.divideDepth = divideDepth;

        this.points = new Vector<>();

        this.dimension = dimension;
        this.origin = origin;
        this.boundaries = new Boundaries(new Point(origin.x - dimension.width / 2, origin.y - dimension.height / 2), new Dimension(dimension.width, dimension.height));
    }

    public void insert(Point point) {
        if (!boundaries.contains(point)) {
            return;
        }

        if (isDivided) {
            insertToKid(point);
        } else {
            if (shouldBeDivided()) {
                divide();
                relocatePoints();
                insertToKid(point);
            } else {
                points.add(point);
                amountOfPoints++;
                sumOfAllPoints++;
            }
        }
    }

    private void insertToKid(Point point) {
        for (QuadTree kid : kids) {
            kid.insert(point);
        }
    }

    public boolean contains(Point point) {
        if (!boundaries.contains(point)) {
            return false;
        }

        if (isDivided) {
            return kidsContain(point);
        } else {
            return parentContains(point);
        }
    }

    private boolean parentContains(Point point) {
        for (Point existingPoint : points) {
            if (existingPoint.equals(point)) {
                return true;
            }
        }
        return false;
    }

    private boolean kidsContain(Point point) {
        for (QuadTree kid : kids) {
            if (kid.contains(point)) {
                return true;
            }
        }
        return false;
    }

    public void remove(Point point) {
        if (!boundaries.contains(point)) {
            return;
        }

        if (isDivided) {
            for (QuadTree kid : kids) {
                kid.remove(point);
            }
        } else {
            if (points.contains(point)) {
                points.remove(point);
                amountOfPoints--;
                sumOfAllPoints--;
            }

            if (amountOfPoints == 0) {
                removeKids();
            }
        }

    }

    private boolean shouldBeDivided() {
        return points.size() >= capacity && divideDepth <= maxDepth;
    }

    private void divide() {
        kids[0] = new QuadTree(divideDepth + 1, new Point(origin.x - dimension.width / 4, origin.y - dimension.height / 4), new Dimension(dimension.width / 2, dimension.height / 2));
        kids[1] = new QuadTree(divideDepth + 1, new Point(origin.x - dimension.width / 4, origin.y + dimension.height / 4), new Dimension(dimension.width / 2, dimension.height / 2));
        kids[2] = new QuadTree(divideDepth + 1, new Point(origin.x + dimension.width / 4, origin.y + dimension.height / 4), new Dimension(dimension.width / 2, dimension.height / 2));
        kids[3] = new QuadTree(divideDepth + 1, new Point(origin.x + dimension.width / 4, origin.y - dimension.height / 4), new Dimension(dimension.width / 2, dimension.height / 2));

        isDivided = true;
    }

    private void removeKids() {
        kids[0] = null;
        kids[1] = null;
        kids[2] = null;
        kids[3] = null;

        isDivided = false;
    }

    private void relocatePoints() {
        for (Point point : points) {
            for (QuadTree kid : kids) {
                kid.insert(point);
            }
        }
        points.clear();
    }

    public static int getSum()
    {
        return sumOfAllPoints;
    }
}
