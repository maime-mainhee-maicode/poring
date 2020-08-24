import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class Poring implements Runnable {
    private JFrame fr;
    private JLabel image, count;
    private Random random;
    private final int index;
    private int x, y;
    private GUI game;
    private static ImageIcon poringIcon;

    public Poring(int index) {
        this.index = index;
    }

    private static ImageIcon getPoringIcon() {
        if (poringIcon == null)
            poringIcon = new ImageIcon("assets/poring.png");

        return poringIcon;
    }

    public void run() {
        game = new GUI().getInstance();
        fr = new JFrame();
        image = new JLabel();
        image.setIcon(getPoringIcon());
        count = new JLabel();
        count.setText("" + index);
        fr.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                fr.dispose();
                game.setScore(game.getScore() + 1);
            }
        });
        fr.setResizable(false);
        fr.setDefaultCloseOperation(fr.DO_NOTHING_ON_CLOSE);
        fr.setLayout(new FlowLayout());
        fr.getContentPane().add(image);
        fr.getContentPane().add(count);
        fr.setVisible(true);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        random = new Random();
        fr.pack();
        x = random.nextInt((int) dimension.getWidth() - fr.getWidth());
        y = random.nextInt((int) dimension.getHeight() - fr.getHeight());
        fr.setLocation(x, y);
        vibrate();
    }

    public void vibrate() {
        try {
            while (true) {
                Thread.sleep(100);
                x += random.nextInt(20);
                y += random.nextInt(20);
                fr.setLocation(x, y);
                fr.setLocation(x, y);
                Thread.sleep(100);
                x -= random.nextInt(10);
                y += random.nextInt(20);
                fr.setLocation(x, y);
                fr.setLocation(x, y);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
