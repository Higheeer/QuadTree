public class Main {
    public static void main(String[] args) {
        QuadTree quadTree = new QuadTree(new Point(250, 250), new Dimension(500, 500));

        for (int i = 0; i < 30; i++) {
            quadTree.insert(new Point((int) (Math.random() * 500), (int) (Math.random() * 500)));
        }
        quadTree.display();
    }
}
