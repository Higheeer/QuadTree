import javax.swing.*;

public class Main extends JFrame {
    public Main() {
        setTitle("QuadTree");
        setSize(1075, 1075);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        Main mainFrame = new Main();

        QuadTree quadTree = new QuadTree(new Point(525, 525), new Dimension(1000, 1000));
        for (int i = 0; i < 8000; i++) {
            quadTree.insert(new Point((Math.random() * 1000) + 25, (Math.random() * 1000) + 25));
        }

        mainFrame.add(quadTree);
        mainFrame.setVisible(true);
    }
}
