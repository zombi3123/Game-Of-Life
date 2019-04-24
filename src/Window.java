import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Window extends JFrame {
    public Window(int x, int y) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(x, y);
        this.setVisible(true);
        this.setLayout(null);
        this.setResizable(true);
    }

}
