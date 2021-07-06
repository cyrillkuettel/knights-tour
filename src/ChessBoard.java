import javax.swing.JFrame;


import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.JPanel;

public final class ChessBoard extends JPanel {


    private Graphics g1;
    private final int BOARD_LEN;

    private List<Square> theWalkedPath;
    private Stack<Square> stack;


    public ChessBoard(int board_len) {
        this.theWalkedPath = new ArrayList<>();
        this.theWalkedPath = Arrays.asList(new Square(0, 0), new Square(2, 1), new Square(0, 2),
                              new Square(1, 0), new Square(3, 1), new Square(5, 0),
                              new Square(7, 1), new Square(6, 3), new Square(7, 5),
                              new Square(6, 7), new Square(4, 6), new Square(2, 7),
                              new Square(0, 6), new Square(1, 4), new Square(2, 6),
                              new Square(0, 7), new Square(1, 5), new Square(0, 3),
                              new Square(1, 1), new Square(3, 0), new Square(5, 1),
                              new Square(7, 0), new Square(6, 2), new Square(7, 4),
                              new Square(6, 6), new Square(4, 7), new Square(3, 5),
                              new Square(1, 6), new Square(3, 7), new Square(5, 6),
                              new Square(7, 7), new Square(6, 5), new Square(5, 7),
                              new Square(7, 6), new Square(6, 4), new Square(7, 2),
                              new Square(6, 0), new Square(4, 1), new Square(2, 0),
                              new Square(0, 1), new Square(1, 3), new Square(0, 5),
                              new Square(1, 7), new Square(3, 6), new Square(5, 5),
                              new Square(3, 4), new Square(2, 2), new Square(4, 3),
                              new Square(2, 4), new Square(1, 2), new Square(0, 4),
                              new Square(2, 5), new Square(3, 3), new Square(5, 4),
                              new Square(7, 3), new Square(6, 1), new Square(4, 2),
                              new Square(2, 3), new Square(4, 4), new Square(5, 2),
                              new Square(4, 0), new Square(3, 2), new Square(5, 3),
                              new Square(4, 5));

        this.stack = new Stack<>();
        this.stack.addAll(theWalkedPath);


        /*
        TODO:
            Could map this for the range 8 -> 600
         */
        BOARD_LEN = 75 * board_len;

    }


    public final void paint(Graphics gp) {
        g1 = gp;
        Color saddlebrown = new Color(139, 69, 19);
        Color chocolateBrown = new Color(210, 105, 30);


        g1.setColor(saddlebrown);
        g1.fillRect(50, 50, BOARD_LEN, BOARD_LEN);
        for (int x = 50; x <= BOARD_LEN; x += 150) {
            for (int y = 50; y <= BOARD_LEN; y += 150) {

                g1.clearRect(x, y, 75, 75);
            }
        }


        for (int x = 125; x <= BOARD_LEN; x += 150) {
            for (int y = 125; y <= BOARD_LEN; y += 150) {
                g1.clearRect(x, y, 75, 75);
            }
        }
        // gp.setColor(Color.BLACK);
        // gp.drawRect(50,50,600, 600); // square around the board


        int offset = 30;
        for (int i = 50; i < BOARD_LEN; i += 75) {
            dot(i + offset, 50 + offset, 10, 10);
        }


        Collections.reverse(stack);

        while (!stack.isEmpty()) {
            Square sq = stack.pop();
           // System.out.println(sq);

        }

    }


    public final void line(int x1, int y1, int x2, int y2) {


        Graphics2D g2d = (Graphics2D) g1.create();
        g2d.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setColor(Color.BLACK);
        g2d.drawLine(x1, y1, x2, y2);


    }


    public final void dot(int x1, int y1, int x2, int y2) {
        Graphics2D g2d = (Graphics2D) g1.create();
        g2d.setColor(Color.BLACK);
        g2d.fillOval(x1, y1, x2, y2);
    }


    public static void main(String[] args) {

        JFrame jf = new JFrame();
        jf.setSize(700, 700);
        jf.setTitle("Draw Chess Board");
        jf.getContentPane().add(new ChessBoard(8));
        jf.setLocationRelativeTo(null);
        jf.setBackground(Color.WHITE);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }
}