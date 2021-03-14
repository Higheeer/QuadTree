import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashSet;

public class QuadTree extends JPanel {
    private final int capacity;
    private final LinkedHashSet<Point> points;

    private final Point origin;
    private final Dimension dimensions;
    private final Rectangle boundaries;

    public QuadTree[] kids;
    private boolean isDivided;

    public QuadTree(Point origin, Dimension dimensions) {
        kids = new QuadTree[4];
        isDivided = false;

        capacity = 5;
        points = new LinkedHashSet<>();

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
    }

    public void paint(Graphics graphic) {
        for (Point point : points) {
            graphic.drawRect((int) point.x, (int) point.y, 1, 1);
        }

        if (isDivided) {
            graphic.setColor(Color.BLACK);
            graphic.drawLine((int) (origin.x - dimensions.width / 2), (int) origin.y, (int) (origin.x + dimensions.width / 2), (int) origin.y);
            graphic.drawLine((int) origin.x, (int) (origin.y - dimensions.height / 2), (int) origin.x, (int) (origin.y + dimensions.height / 2));

            for (QuadTree kid : kids) {
                graphic.setColor(Color.RED);
                kid.paint(graphic);
            }
        }
    }
}
