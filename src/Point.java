public class Point
{
    public double x;
    public double y;

    public Point()
    {
        this.x = 0;
        this.y = 0;
    }

    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object second)
    {
        if (this == second)
        {
            return true;
        }

        if (!(second instanceof Point))
        {
            return false;
        }

        return Double.compare(x, ((Point) second).x) == 0
                && Double.compare(y, ((Point) second).y) == 0;
    }

}
