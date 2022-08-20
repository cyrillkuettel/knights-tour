package backtracking;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import javax.swing.JPanel;

public final class ChessBoard extends JPanel {


    private Graphics g1;
    private final int BOARD_LEN;
    private final int BOARDSIZE; // 8

    private  List<Square> walkedPathListForTesting;
    private final Stack<Square> stack;

    public ChessBoard(Stack<Square> walkedPath, int board_len) {
        BOARDSIZE = board_len;
        BOARD_LEN = 75 * board_len;
        Stack<Square> walkedPathCopy = new Stack<>();
        walkedPathCopy.addAll(walkedPath);
        this.stack = walkedPathCopy;
        this.walkedPathListForTesting = null;
    }

    public ChessBoard(int board_len) {
        this.walkedPathListForTesting = new ArrayList<>();
        this.walkedPathListForTesting = Arrays.asList(new Square(0, 0), new Square(2, 1), new Square(0, 2),
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
        this.stack.addAll(walkedPathListForTesting);


        /*
        TODO:
            Could map this for the range 8 -> 600
         */
        BOARDSIZE = board_len;
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

        Collections.reverse(stack);
        int i = 0;
        Point[] points = new Point[BOARDSIZE * BOARDSIZE];

        for (Square  sq : stack) {
            points[i] = new Point((sq.getX()+1)*75+7, (sq.getY()+1)*75+7);
            if ( i > 0) {
                dot(points[i].x, points[i].y, 10, 10);
                int offset = 5;
                line(points[i-1].x+offset, points[i-1].y+offset, points[i].x+offset, points[i].y+offset);
            }
            i++;
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
        g2d.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setColor(Color.BLACK);
        g2d.fillOval(x1, y1, x2, y2);
    }


    public static int map(int number, int a1 , int a2, int b1, int b2) {
        return b1 + ((number - a1) *(b2-b1) )/ (a2-a1);

    }
    public static void main(String[] args) {

        JFrame jf = new JFrame();
        jf.setSize(700, 700);
        jf.setTitle("Knight's Tour");
        ChessBoard board = new ChessBoard(8);
        jf.getContentPane().add(board);
        jf.setLocationRelativeTo(null);
        jf.setBackground(Color.WHITE);
        jf.getContentPane().setBackground(Color.WHITE);


        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
        takePicture(board);
    }
    static void takePicture(JPanel panel) {
        BufferedImage img = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
        panel.print(img.getGraphics()); // or: panel.printAll(...);
        try {
            ImageIO.write(img, "jpg", new File("/home/cyrill/Documents/panel.jpg"));
            // ImageIO.write(img, "png", new File("/home/cyrill/Documents/panel.png"));
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}