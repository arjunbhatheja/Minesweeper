import java.util.ArrayList;
import java.awt.event.*;
import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class minesweep {

    private class MineTile extends JButton {
        int r;
        int c;

        public MineTile(int r,int c){
            this.r = r;
            this.c = c;
        }
    }

    int tileSize = 70;
    int numRows = 8;
    int numCols = 8;
    int boardWidth = numCols * tileSize;
    int boardHeight = numRows * tileSize;


    JFrame frame = new JFrame("Minesweeper");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    MineTile[][] board = new MineTile[numRows][numCols];
    ArrayList<MineTile> mineList; 

    minesweep(){ 
        // frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setFont(new Font("Arial", Font.BOLD, 25));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Minesweeper");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(numRows, numCols));
        // boardPanel.setBackground(Color.blue);
        frame.add(boardPanel);

        for(int r = 0; r<numRows; r++){
             for(int c = 0; c < numCols; c++){
                MineTile tile = new MineTile(r, c);
                board[r][c] = tile;
                
                tile.setFocusable(false);
                tile.setMargin(new Insets(0, 0, 0, 0));
                tile.setFont(new Font("Arial Unicode MS", Font.PLAIN, 45));
 
                tile.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (gameOver) {
                            return;
                        }
                        MineTile tile = (MineTile) e.getSource();

                        //left click
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            if (tile.getText() == "") {
                                if (mineList.contains(tile)) {
                                    revealMines();
                                }
                                else {
                                    checkMine(tile.r, tile.c);
                                }
                            }
                        }
                        //right click
                        else if (e.getButton() == MouseEvent.BUTTON3) {
                            if (tile.getText() == "" && tile.isEnabled()) {
                                tile.setText("🚩");
                            }
                            else if (tile.getText() == "🚩") {
                                tile.setText("");
                            }
                        }
                    } 
                });

                boardPanel.add(tile);  
              }
        }
        frame.setVisible(true);
        setMines();
    } 
    void setMines(){
         mineList  = new ArrayList<MineTile>();

         mineList.add(board[2][2]);
         mineList.add(board[2][3])
    }

    void revealMines(){
        for(int i = 0; i<mineList.size(); i++){
            MineTile tile = mineList.get(i);
            tile.setText("💣");
        }
    }
    void checkMine(int r, int c){
        MineTile tile = board[r][c];
        tile.setEnabled(false);

        int minesFound = 0;

        minesFound += countMine(r-1,c-1); //top left
        minesFound += countMine(r-1,c); //top 
        minesFound += countMine(r-1,c+1); //top right
        minesFound += countMine(r,c-1); //left
        minesFound += countMine(r,c+1); //right
        minesFound += countMine(r+1,c-1); // bottom left
        minesFound += countMine(r+1,c); // bottom
        minesFound += countMine(r+1,c+1); // bottom right
    }
    int countMine(int r, int c){
        if(r<0 || r >= numRows || c<0 || c<=numCols){
            return 0;
        } 
        if(mineList.contains(board[r][c])){
            return 1;
        }
        return 0;
    }
}  
