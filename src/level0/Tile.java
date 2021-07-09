package level0;

import javax.swing.*;
import java.awt.*;

public class Tile extends JLabel {
    public static final int PREF_LABEL_SIZE = 15;

    private final int row, column;
    public final boolean wall, topBorder, bottomBorder, leftBorder, rightBorder;

    private boolean smallPoint;

    public Tile(int row, int column, int tileData){
        this.row = row; this.column = column;

        wall = tileData == -1;
        topBorder = (tileData & 1) == 1;
        bottomBorder = (tileData & 2) == 2;
        leftBorder = (tileData & 4) == 4;
        rightBorder = (tileData & 8) == 8;
        smallPoint = (tileData & 16) == 16;

        setOpaque(false);
        setPreferredSize(new Dimension(PREF_LABEL_SIZE, PREF_LABEL_SIZE));
    }

    @Override
    public String toString() {
        if(wall) return "wall: " + row + ", " + column;
        else return "(" + column + ", " + row + ") U: " + !topBorder + " D: " + !bottomBorder + " L: " + !leftBorder + " R: " + !rightBorder;
    }

    public void consume() {
        smallPoint = false;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        int labelWidth = getWidth(), labelHeight = getHeight();
        if(wall){
            g.setColor(new Color(75, 119, 252));
            g.fillRect(0,0, labelWidth, labelHeight);
        } else {
            g.setColor(Color.ORANGE);
            if (smallPoint)
                g.fillOval(labelWidth / 2 - labelWidth / 8, labelHeight / 2 - labelHeight / 8, labelWidth / 4, labelHeight / 4);
        }

    }
}
