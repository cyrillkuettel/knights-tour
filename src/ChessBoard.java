import javax.swing.JFrame;


import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class ChessBoard extends JPanel{

private final int BOARD_LEN;

    public ChessBoard(int board_len) {
        BOARD_LEN = board_len;
    }


    public void paint(Graphics gp)
    {

        gp.fillRect(50, 50, 600, 600);
        for(int x=50; x<=600; x+=150)
        {
            for(int y=50; y<=600; y+=150)
            {
                gp.clearRect(x, y, 75, 75);
            }
        }


        for(int x=125; x<=600; x+=150)
        {
            for(int y=125; y<=600; y+=150)
            {
                gp.clearRect(x, y, 75, 75);
            }
        }
        gp.drawRect(50,50,600, 600);
    }


    public static void main(String[] args) {

        JFrame jf=new JFrame();
        jf.setSize(700, 700);
        jf.setTitle("Draw Chess Board");
        jf.getContentPane().add(new ChessBoard(board_len));
        jf.setLocationRelativeTo(null);
        jf.setBackground(Color.WHITE);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }
}