import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class Poring implements Runnable {
    private JFrame fr;
    private JLabel image, count;
    private Random random;
    private final int index;
    private int x, y, vibrateRate;
    private boolean alive;
    private GUI game;

    public Poring(int index) {
        this(index, 15);
    }

    public Poring(int index, int vibrateRate) {
        this.index = index;
        this.vibrateRate = vibrateRate;
        this.init();
    }

    public void init() {
        this.game = GUI.getInstance();
        this.fr = new JFrame();
        this.image = new JLabel();
        this.image.setIcon(new ImageIcon("assets/poring.png"));
//        this.count = new JLabel();
//        this.count.setText("" + index);
        this.fr.setTitle("Poring #" + this.index);
        this.random = new Random();

        this.fr.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                die();
            }
        });
        this.fr.setResizable(false);
        this.fr.setDefaultCloseOperation(fr.DO_NOTHING_ON_CLOSE);
        this.fr.setLayout(new FlowLayout());
        this.fr.getContentPane().add(image);
//        this.fr.getContentPane().add(count);
        this.fr.pack();
    }

    public void run() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.random = new Random();
        this.x = this.random.nextInt((int) dimension.getWidth() - fr.getWidth());
        this.y = this.random.nextInt((int) dimension.getHeight() - fr.getHeight());
        this.fr.setLocation(x, y);
        this.fr.setVisible(true);
        this.alive = true;
        this.vibrate();
    }

    public void vibrate() {
        try {
            while (this.alive) {
                Thread.sleep(125l);
                this.random = new Random();
                this.x += this.random.nextInt(this.vibrateRate) * (this.random.nextDouble() > 0.5 ? -1 : 1);
                this.y += this.random.nextInt(this.vibrateRate) * (this.random.nextDouble() > 0.5 ? -1 : 1);
                this.x = setBoundaryX(this.x);
                this.y = setBoundaryY(this.y);
                this.fr.setLocation(this.x, this.y);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void spawn() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public void die() {
        try {
            this.alive = false;
            this.fr.setVisible(false);
            this.game.setScore(game.getScore() + 1);
            Thread.sleep(1000l); // to make sure thread would stop from running
            Spawner.getInstance().getPoring(this.index - 1).spawn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int setBoundaryX(int x) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        return (int) Math.max(0, Math.min(x, dimension.getWidth() - this.fr.getWidth()));
    }

    public int setBoundaryY(int y) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        return (int) Math.max(0, Math.min(y, dimension.getHeight() - this.fr.getHeight()));
    }

    public boolean isInsideScreen() {
        return this.isInsideScreen(this.x, this.y);
    }

    public boolean isInsideScreen(int x, int y) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        boolean left = x >= 0;
        boolean right = x + this.fr.getWidth() <= dimension.getWidth();
        boolean top = y >= 0;
        boolean bottom = y + this.fr.getHeight() <= dimension.getHeight();
        return left && right && top && bottom;
    }
}
