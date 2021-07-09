package level0;

import java.awt.*;

import static level1.Characters.GAME_SPEED;

public class Pacman {
    // !!MAKE SURE: MOUTH_SPEED <= GAME_SPEED!!
    public static final int MOUTH_SPEED = 10;

    private int xPosition, yPosition, height, width;
    private Direction movementDirection;
    private boolean mouthClosed;

    public Pacman(int row, int column, Direction movementDirection, boolean mouthClosed) {
        height = Tile.PREF_LABEL_SIZE;
        width = Tile.PREF_LABEL_SIZE;

        xPosition = column*width;
        yPosition = row*height;

        this.movementDirection = movementDirection;
        this.mouthClosed = mouthClosed;
    }

    public Image getCurrentImage() {
        return mouthClosed ? movementDirection.pacmanClosed : movementDirection.pacmanOpen;
    }

    public int getX() { return xPosition; }
    public int getY() { return yPosition; }
    public void setX(int xPosition) { this.xPosition = xPosition; }
    public void setY(int yPosition) { this.yPosition = yPosition; }

    public int getHeight() { return height; }
    public int getWidth() { return width; }
    public void setHeight(int height) { this.height = height; }
    public void setWidth(int width) { this.width = width; }

    public Direction getMovementDirection() {
        return movementDirection;
    }
    public void setMovementDirection(Direction direction) {
        if(direction.dx == 0) xPosition += this.movementDirection.dx == 1 ? (width - (xPosition % width)) % width : -1 * (xPosition % width);
        if(direction.dy == 0) yPosition += this.movementDirection.dy == 1 ? (height - (yPosition % height)) % height : -1 * (yPosition % height);
        this.movementDirection = direction;
    }

    int mouthOpenCloseCounter = 0;
    public void move() {
        xPosition += movementDirection.dx;
        yPosition += movementDirection.dy;

        mouthOpenCloseCounter = (mouthOpenCloseCounter + 1) % (GAME_SPEED / MOUTH_SPEED);
        if(mouthOpenCloseCounter == 0 && movementDirection.dx != 0 || movementDirection.dy != 0)
            mouthClosed = !mouthClosed;
    }
}