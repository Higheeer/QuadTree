import java.util.Random;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        QuadTree quadTree = new QuadTree(6, new Point(500, 500), new Dimension(1000, 1000));

        for (int x = 0; x < 10000; x++) {
            Vector<Point> points = new Vector<>();

            for (int i = 0; i < 14000; i++) {
                points.add(new Point((int) (Math.random() * 1000), (int) (Math.random() * 1000)));
            }

            for (int i = 0; i < 14000; i++) {
                quadTree.insert(points.elementAt(i));
            }

            for (int i = 0; i < 14000; i++) {
                quadTree.remove(points.elementAt(i));
            }

           // System.out.println(QuadTree.sumOfAllPoints);
        }
    }
}



