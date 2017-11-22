import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Omega {

    private boolean[] elements;
    private double[] probabilities;
    private String[] names;

    private static Event OMEGA = null;
    private static Event EMPTY = null;

    public Omega(int size) {
        elements = new boolean[size];
        probabilities = new double[size];
        names = new String[size];
        for (int i=0; i<size; i++) {
            probabilities[i] = -1;
            elements[i] = true;
            names[i] = String.valueOf(i+1);
        }
        EMPTY = new Event("Empty", this);
        OMEGA = new Event("Omega", this, getElements());
    }

    public void setProbability(int no, double p) {
        probabilities[no] = p;
    }

    public Event getEmpty() {
        if (EMPTY == null)
            EMPTY = new Event("Empty", this);
        return EMPTY;
    }

    public Event getOmega() {
        if (OMEGA == null)
            OMEGA = new Event("Omega", this, getElements());
        return OMEGA;
    }

    private boolean[] getElements() {
        return elements;
    }

    public Omega(String... names) {
        elements = new boolean[names.length];
        this.names = names;
        for (int i=0; i<names.length; i++)
            elements[i] = true;
    }

    public int size() {
        return elements.length;
    }

    public double[] getProbabilities() {
        return probabilities;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("OMEGA = {");
        for (int i=0; i<elements.length-1; i++)
            sb.append("\"").append(names[i]).append("\"; ");
        sb.append("\"").append(names[elements.length-1]).append("\"}");
        return sb.toString();
    }

    public String getName(int i) {
        return names[i];
    }

    public List<Space> getAllSpaces() {
        LinkedList<Space> result = new LinkedList<Space>();
/*
        int max = (int) Math.pow(2, size());
        int n, p;
        for (int i=0; i<max; i++) {
            Space s = new Space(this);
            n = i; p = 1;
            while (n > 0) {
                if ((n & 0x1) == 1) {
                    s.addEvent(new Event(String.valueOf(p), this, p));
                }
                n >>= 1;
                p++;
            }
            s.generateAll();
            boolean toAdd = true;
            for (Space space: result) {
                if (space.equals(s)) {
                    toAdd = false;
                    break;
                }
            }
            if (toAdd) {
                s.setNumber(result.size());
                result.add(s);
            }
        }*/
        return result;
    }

    public LinkedList<Space> getAll() {
        LinkedList<Space> list = new LinkedList<Space>();

        Space big = new Space(this);
        for (int i=0; i<size(); i++) {
            big.addEvent(new Event(String.valueOf(i+1), this, i+1));
        }
        big.generateAll();
        ArrayList<Event> allEvents = new ArrayList<Event>(big.getEvents());

        long max = (long) Math.pow(2, Math.pow(2, size()));
        long n, p;
        double deca = max / 100.0, per = 0.0;
        System.out.println("Max = " + max);
        for (long i=0; i<max; i++) {
            if (i%100000 == 0) {
                System.out.println(i + "checks done;  " + list.size() + " spaces");
            }
            if (i > per) {
                per += deca;
                System.out.println( String.format("%.2f",per/max*100.0) + "% passed..." + list.size() + " spaces");
            }

            Space s = new Space(this);
            n = i; p = 0;
            while (n > 0) {
                if ((n & 0x1) == 1) {
                    s.addEvent(allEvents.get((int)p));
                }
                n >>= 1;
                p++;
            }
            s.generateAll();
            boolean toAdd = true;
            for (Space space: list) {
                if (space.equals(s)) {
                    toAdd = false;
                    break;
                }
            }
            if (toAdd) {
                s.setNumber(list.size());
                list.add(s);
            }
        }

        return list;
    }

}