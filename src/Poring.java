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
        this.init();
    }

    private static ImageIcon getPoringIcon() {
        if (poringIcon == null)
            poringIcon = new ImageIcon("assets/poring.png");

        return poringIcon;
    }

    public void init() {
        this.game = GUI.getInstance();
        this.fr = new JFrame();
        this.image = new JLabel();
        this.image.setIcon(getPoringIcon());
        this.count = new JLabel();
        this.count.setText("" + index);
        this.random = new Random();

        this.fr.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                fr.dispose();
                game.setScore(game.getScore() + 1);
            }
        });
        this.fr.setResizable(false);
        this.fr.setDefaultCloseOperation(fr.DO_NOTHING_ON_CLOSE);
        this.fr.setLayout(new FlowLayout());
        this.fr.getContentPane().add(image);
        this.fr.getContentPane().add(count);
        this.fr.setVisible(true);
    }


    public void run() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.random = new Random();
        this.fr.pack();
        x = this.random.nextInt((int) dimension.getWidth() - fr.getWidth());
        y = this.random.nextInt((int) dimension.getHeight() - fr.getHeight());
        this.fr.setLocation(x, y);
        this.vibrate();
    }

    public void vibrate() {
        try {
            while (true) {
                Thread.sleep(125l);
                x += this.random.nextInt(20) * this.random.nextDouble() > 0.5 ? 1 : -1;
                y += this.random.nextInt(20) * this.random.nextDouble() > 0.5 ? 1 : -1;
                x = setBoundaryX(x);
                y = setBoundaryY(y);
                fr.setLocation(x, y);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isInsideScreen(int x, int y) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        boolean left = x >= 0;
        boolean right = x + this.fr.getWidth() <= dimension.getWidth();
        boolean top = y >= 0;
        boolean bottom = y + this.fr.getHeight() <= dimension.getHeight();
        return left && right && top && bottom;
    }

    public int setBoundaryX(int x) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        return (int) Math.max(0, Math.min(x, dimension.getWidth() - (x + this.fr.getWidth())));
    }

    public int setBoundaryY(int y) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        return (int) Math.max(0, Math.min(y, dimension.getHeight() - (x + this.fr.getHeight())));
    }
}
