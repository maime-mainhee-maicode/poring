import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI implements ActionListener {
    private JFrame fr;
    private JButton btn;
    private int count;
    private JTextArea scoreboard;
    private int score;
    private int time;
    private static GUI instance;
    
    public void init() {
        btn = new JButton("Start");
        scoreboard = new JTextArea("Score : 0");
        btn.addActionListener(this);
        fr = new JFrame("Poring Master");
        fr.add(scoreboard, BorderLayout.CENTER);
        fr.add(btn, BorderLayout.SOUTH);
        fr.setSize(400,400);
        fr.setDefaultCloseOperation(fr.EXIT_ON_CLOSE);
        fr.setVisible(true);
        fr.pack();
    }
    
    public static GUI getInstance() {
        if (instance == null){
            instance = new GUI();
        }
        return instance;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        this.scoreboard.setText("Score : " +score);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Start")) {
            try {
                while (time < 10) {
                    Poring box = new Poring(++count);
                    scoreboard.setText("Score : "+ score);
                    Thread thread = new Thread(box);
                    thread.start();
                        Thread.sleep(1000l);
                    time++;
                }
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }
}
