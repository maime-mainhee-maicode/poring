import java.util.TimerTask;

class PoringTask extends TimerTask {
    private Poring poring;

    public PoringTask(Poring poring) {
        this.poring = poring;
        this.poring.run();
    }

    @Override
    public void run() {
        synchronized (this.poring) {
            if (this.poring.isAlive()) {
                this.poring.vibrate();
            } else {
                this.cancel();
            }
        }
    }
}