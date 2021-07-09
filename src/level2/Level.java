package level2;

import level1.Characters;
import level1.Maze;

import javax.swing.*;
import java.awt.*;

public class Level extends JLayeredPane {

    public Level() {
        Maze maze = new Maze(21, 19, 1);
        Characters characterLayer = new Characters(maze);

        setLayout(new LayeredLayout());
        add(characterLayer, 0); add(maze, 1);
    }
}

class LayeredLayout implements LayoutManager {
    @Override
    public void addLayoutComponent(String name, Component comp) { }

    @Override
    public void removeLayoutComponent(Component comp) { }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        int width = 0, height = 0;
        for(Component c : parent.getComponents()) {
            Dimension d = c.getPreferredSize();
            width = Math.max(width, d.width);
            height = Math.max(height, d.height);
        }

        return new Dimension(width, height);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return new Dimension(0, 0);
    }

    @Override
    public void layoutContainer(Container parent) {
        for(Component c : parent.getComponents())
            c.setSize(parent.getSize());
    }
}