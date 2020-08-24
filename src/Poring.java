import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Poring implements Runnable {
    private JFrame fr;
    private JLabel image, count;
    private Random random;
    private final int index;
    private int x, y, vibrateRate;
    private boolean alive;
    private Timer timer;
    private GUI game;

    public Poring(int index) {
        this(index, 15);
    }

    public Poring(Poring poring) {
        this(poring.index, poring.vibrateRate);
    }

    public Poring(int index, int vibrateRate) {
        this.index = index;
        this.vibrateRate = vibrateRate;
        this.timer = new Timer();
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
    }

    public void vibrate() {
        this.vibrate(this.vibrateRate);
    }

    public void vibrate(int vibrateRate) {
        this.random = new Random();
        this.x += this.random.nextInt(vibrateRate) * (this.random.nextDouble() > 0.5 ? -1 : 1);
        this.y += this.random.nextInt(vibrateRate) * (this.random.nextDouble() > 0.5 ? -1 : 1);
        this.x = setBoundaryX(this.x);
        this.y = setBoundaryY(this.y);
        this.fr.setLocation(this.x, this.y);
    }

    public void spawn() {
        PoringTask task = new PoringTask(this);
        this.timer.schedule(task, 100, 100);
    }

    public void die() {
        try {
            this.alive = false;
            this.fr.setVisible(false);
            this.game.setScore(game.getScore() + 1);

            this.timer.purge();
            this.timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Spawner.getInstance().getPoring(index - 1).spawn();
                }
            }, 1000l);
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

    public boolean isAlive() {
        return this.alive;
    }

    public Poring clone() {
        return new Poring(this);
    }
}
