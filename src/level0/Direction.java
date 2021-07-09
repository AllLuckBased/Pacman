package level0;

import javax.swing.*;
import java.awt.*;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    public int dx, dy;
    Image pacmanOpen, pacmanClosed = new ImageIcon(Pacman.class.getResource("Graphics/PacmanClosed.png")).getImage();

    static {
        UP.dx = 0; UP.dy = -1; UP.pacmanOpen = new ImageIcon(Pacman.class.getResource("Graphics/PacmanUp.png")).getImage();
        DOWN.dx = 0; DOWN.dy = 1; DOWN.pacmanOpen = new ImageIcon(Pacman.class.getResource("Graphics/PacmanDown.png")).getImage();
        LEFT.dx = -1; LEFT.dy = 0; LEFT.pacmanOpen = new ImageIcon(Pacman.class.getResource("Graphics/PacmanLeft.png")).getImage();
        RIGHT.dx = 1; RIGHT.dy = 0; RIGHT.pacmanOpen = new ImageIcon(Pacman.class.getResource("Graphics/PacmanRight.png")).getImage();
    }
}
