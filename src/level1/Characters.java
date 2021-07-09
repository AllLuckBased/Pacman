package level1;

import level0.Direction;
import level0.Pacman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;

public class Characters extends JPanel {
    public static final int GAME_SPEED = 50;

    private final Maze maze;
    private final Timer gameTimer = new Timer();

    private final Pacman pacman = new Pacman(15, 9, Direction.UP, false);

    private final ReentrantLock lock = new ReentrantLock();
    private final TimerTask gameThread = new TimerTask() {
        @Override
        public void run() {
            lock.lock();
            if (maze.canMove(pacman.getX(), pacman.getY(), pacman.getMovementDirection(), pacman.getMovementDirection()))
                pacman.move();
            lock.unlock();
            repaint();
        }
    };

    public Characters(Maze maze) {
        this.maze = maze;

        getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "Start Game");
        getActionMap().put("Start Game", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameTimer.scheduleAtFixedRate(gameThread, 0, 1000/GAME_SPEED);
            }
        });

        getInputMap().put(KeyStroke.getKeyStroke("UP"), "UpEvent");
        getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "DownEvent");
        getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "LeftEvent");
        getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "RightEvent");
        getInputMap().put(KeyStroke.getKeyStroke('w'), "UpEvent");
        getInputMap().put(KeyStroke.getKeyStroke('s'), "DownEvent");
        getInputMap().put(KeyStroke.getKeyStroke('a'), "LeftEvent");
        getInputMap().put(KeyStroke.getKeyStroke('d'), "RightEvent");
        getActionMap().put("UpEvent", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(maze.canMove(pacman.getX(), pacman.getY(), Direction.UP, pacman.getMovementDirection()))
                    pacman.setMovementDirection(Direction.UP);
            }
        });
        getActionMap().put("DownEvent", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(maze.canMove(pacman.getX(), pacman.getY(), Direction.DOWN, pacman.getMovementDirection()))
                    pacman.setMovementDirection(Direction.DOWN);
            }
        });
        getActionMap().put("LeftEvent", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(maze.canMove(pacman.getX(), pacman.getY(), Direction.LEFT, pacman.getMovementDirection()))
                    pacman.setMovementDirection(Direction.LEFT);
            }
        });
        getActionMap().put("RightEvent", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(maze.canMove(pacman.getX(), pacman.getY(), Direction.RIGHT, pacman.getMovementDirection()))
                    pacman.setMovementDirection(Direction.RIGHT);
            }
        });

        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        lock.lock();
        if (pacman.getHeight() != maze.getCurrentTileHeight()) {
            int yRow = (pacman.getY() / pacman.getHeight()) * maze.getCurrentTileHeight();
            int rowOffset = (pacman.getY() % pacman.getHeight()) * (maze.getCurrentTileHeight() / pacman.getHeight());
            pacman.setY(yRow + rowOffset);

            pacman.setHeight(maze.getCurrentTileHeight());
        }
        if (pacman.getWidth() != maze.getCurrentTileWidth()) {
            int xRow = (pacman.getX() / pacman.getWidth()) * maze.getCurrentTileWidth();
            int columnOffset = (pacman.getX() % pacman.getWidth()) * (maze.getCurrentTileWidth() / pacman.getWidth());
            pacman.setX(xRow + columnOffset);

            pacman.setWidth(maze.getCurrentTileWidth());
        }
        lock.unlock();
        g.drawImage(pacman.getCurrentImage(), pacman.getX(), pacman.getY(), pacman.getWidth(), pacman.getHeight(), null);
    }
}