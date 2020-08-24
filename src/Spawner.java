import java.util.*;

public class Spawner {

    private static ArrayList<Poring> porings;
    private static Spawner instance;


    public Spawner() {
        this(new ArrayList<Poring>());
    }

    public Spawner(ArrayList<Poring> porings) {
        porings = (ArrayList<Poring>) porings.clone();
        instance = this;
    }

    public static Spawner getInstance() {
        if (instance == null) instance = new Spawner();
        return instance;
    }

    public Spawner createSpawner(int amount) {
        if (porings != null && porings.size() > 0) {
            porings.clear();
        } else {
            porings = new ArrayList<Poring>();
        }

        for (int i = 1; i <= amount; i++) {
            porings.add(new Poring(i));
        }
        return instance;
    }

    public Poring getPoring(int index) {
        return porings.get(index);
    }

    public ArrayList<Poring> getPorings() {
        return porings;
    }
}
