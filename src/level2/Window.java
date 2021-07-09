package level2;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    public static final Dimension MINIMUM_WINDOW_SIZE = new Dimension(400, 500);

    public Window() {
        JPanel contentPane = (JPanel) getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(Color.BLACK);

        Level level = new Level();
        add(level);
        pack();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(MINIMUM_WINDOW_SIZE);
        setVisible(true);
    }
}