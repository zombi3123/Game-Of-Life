import javax.swing.*;
        import java.awt.*;
        import java.awt.event.MouseEvent;
        import java.awt.event.MouseListener;
//JFrame Class
public class Window extends JFrame {

    public Window(int x, int y) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(x, y);
        this.setResizable(true);
        this.setLayout(null);
        getContentPane().setBackground(Color.BLACK);
    }

}
