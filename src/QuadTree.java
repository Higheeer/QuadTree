import java.util.Vector;

public class QuadTree
{
    private final static int capacity = 10;
    private final Vector<Point> points;
    private final QuadTree[] kids;

    private final Point origin;
    private final Dimension dimension;
    private final Boundaries boundaries;

    private final int divideLevel;
    private boolean isDivided;
    private final static int maxDepth = 5;

    public QuadTree(int divideLevel, Point origin, Dimension dimension)
    {
        this.kids = new QuadTree[4];
        this.isDivided = false;
        this.divideLevel = divideLevel;

        this.points = new Vector<>();

        this.dimension = dimension;
        this.origin = origin;
        this.boundaries = new Boundaries(new Point(origin.x - dimension.width / 2, origin.y - dimension.height / 2), new Dimension(dimension.width, dimension.height));
    }

    public void insert(Point point)
    {
        if (!boundaries.contains(point))
        {
            return;
        }

        if (isDivided)
        {
            insertToKid(point);
        }
        else
        {
            if (shouldBeDivided())
            {
                divide();
                relocatePoints();
                insertToKid(point);
            }
            else
            {
                insertToParent(point);
            }
        }
    }

    private void insertToParent(Point point)
    {
        points.add(point);
    }

    private void insertToKid(Point point)
    {
        for (QuadTree kid : kids)
        {
            kid.insert(point);
        }
    }

    private boolean shouldBeDivided()
    {
        return points.size() >= capacity && divideLevel <= maxDepth;
    }

    private void divide()
    {
        kids[0] = new QuadTree(divideLevel + 1, new Point(origin.x - dimension.width / 4, origin.y - dimension.height / 4), new Dimension(dimension.width / 2, dimension.height / 2));
        kids[1] = new QuadTree(divideLevel + 1, new Point(origin.x - dimension.width / 4, origin.y + dimension.height / 4), new Dimension(dimension.width / 2, dimension.height / 2));
        kids[2] = new QuadTree(divideLevel + 1, new Point(origin.x + dimension.width / 4, origin.y + dimension.height / 4), new Dimension(dimension.width / 2, dimension.height / 2));
        kids[3] = new QuadTree(divideLevel + 1, new Point(origin.x + dimension.width / 4, origin.y - dimension.height / 4), new Dimension(dimension.width / 2, dimension.height / 2));

        isDivided = true;
    }

    private void relocatePoints()
    {
        for (Point point : points)
        {
            for (QuadTree kid : kids)
            {
                kid.insert(point);
            }
        }
        points.clear();
    }
}
