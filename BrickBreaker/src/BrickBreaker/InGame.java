package BrickBreaker;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InGame extends JPanel implements ActionListener, KeyListener {

    private boolean ingame = false;
    private int score = 0;
    private int delay = 8;
    private int totalBricks = 21;
    private Timer timer;
    private int px = 310;
    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;

    private BoardGenerator board;

    public InGame(){
        board = new BoardGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0,0,692,592);

        board.draw((Graphics2D) g);

        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 590, 30);

        g.setColor(Color.yellow);
        g.fillRect(px, 550, 100, 8);

        //for ball
        g.setColor(new Color(191, 84, 217, 142));
        g.fillOval(ballposX,ballposY,20,20);

        if(ballposY > 570){
            ingame = false;
            ballXdir = 0;
            ballYdir = 0;

            g.setColor(Color.WHITE);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("    BrickBreaker.Game Over Score: " + score, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("   Press Enter to Restart", 190, 340);
        }

        if(totalBricks == 0){
            ingame = false;
            ballYdir = -2;
            ballXdir = -1;
            g.setColor(Color.WHITE);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("    BrickBreaker.Game Over: "+score,190,300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("   Press Enter to Restart", 190, 340);
        }
        g.dispose();

    }






    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if(ingame) {
            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(px, 550, 100, 8))) {
                ballYdir = -ballYdir;
            }

            A:
            for (int i = 0; i < board.board.length; i++) {
                for (int j = 0; j < board.board[0].length; j++) {
                    if (board.board[i][j] > 0) {
                        int brickx = j * board.BrickWidth + 80;
                        int bricky = i * board.BrickHight + 50;
                        int brickwidth = board.BrickWidth;
                        int brickhight = board.BrickHight;

                        Rectangle brickrect = new Rectangle(brickx, bricky, brickwidth, brickhight);
                        Rectangle ballrect = new Rectangle(ballposX, ballposY, 20, 20);

                        if (ballrect.intersects(brickrect)) {
                            board.setBrickvalue(0, i, j);
                            totalBricks--;
                            score += 5;
                            if (ballposX + 19 <= brickrect.x || ballposX + 1 >= brickrect.x + brickwidth) {
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }
            ballposX += ballXdir;
            ballposY += ballYdir;
            if (ballposX < 0) {
                ballXdir = -ballXdir;
            }
            if (ballposY < 0) {
                ballYdir = -ballYdir;
            }
            if (ballposX > 670) {
                ballXdir = -ballXdir;
            }

        }
        repaint();


    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (px >= 600) {
                px = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (px < 10) {
                px = 10;
            } else {
                moveLeft();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!ingame) {
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                score = 0;
                px = 310;
                totalBricks = 21;
                board = new BoardGenerator(3,7);

                repaint();
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void moveRight ()
    {
        ingame = true;
        px += 20;
    }
    public void moveLeft ()
    {
        ingame = true;
        px -= 20;
    }
}
