import javax.swing.JFrame;


import java.awt.*;
import javax.swing.JPanel;

public class ChessBoard extends JPanel{

private final int BOARD_LEN;

    public ChessBoard(int board_len) {
        BOARD_LEN = board_len;
    }


    public void paint(Graphics gp) {
        Color saddlebrown = new Color(139,69,19);
        Color chocolateBrown = new Color(210,105,30);


        gp.setColor(saddlebrown);
        gp.fillRect(50, 50, 600, 600);
        for(int x=50; x<=600; x+=150)
        {
            for(int y=50; y<=600; y+=150)
            {
                gp.clearRect(x, y, 75, 75);
            }
        }


        for(int x=125; x<=600; x+=150) {
            for(int y=125; y<=600; y+=150)
            {
                gp.clearRect(x, y, 75, 75);
            }
        }
        // gp.setColor(Color.BLACK);
       // gp.drawRect(50,50,600, 600); // square around the board

        Graphics2D g2d = (Graphics2D) gp.create();
        g2d.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setColor(Color.BLACK);
        g2d.drawLine(0,0, 20, 35);
    }




    public static void main(String[] args) {

        JFrame jf=new JFrame();
        jf.setSize(700, 700);
        jf.setTitle("Draw Chess Board");
        jf.getContentPane().add(new ChessBoard(8));
        jf.setLocationRelativeTo(null);
        jf.setBackground(Color.WHITE);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }
}