package BrickBreaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class BoardGenerator {
    public int board[][];
    public int BrickWidth;
    public int BrickHight;

    public  BoardGenerator(int row ,int col){
        board = new int[row][col];

        for(int [] mapD : board){
            for(int j = 0; j < board[0].length;j++){
                mapD[j] = 1;
            }
        }
        BrickHight = 150 / row;
        BrickWidth = 540 / col;
    }


    public void draw(Graphics2D g){
        for(int i = 0 ;i < board.length;i++){
            for(int j = 0;j < board[0].length;j++){
                if(board[i][j] > 0){
                    g.setColor(Color.red);
                    g.fillRect(j * BrickWidth + 80,i * BrickHight + 50,BrickWidth,BrickHight);
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.BLACK);
                    g.drawRect(j * BrickWidth + 80,i * BrickHight + 50,BrickWidth,BrickHight);
                }
            }
        }
    }

    public void setBrickvalue(int value ,int row ,int col){
        board[row][col] = value;
    }

}
