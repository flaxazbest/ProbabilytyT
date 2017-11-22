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

        int max = (int) Math.pow(2, size());
        for (int i=0; i<max; i++) {
            for (int j=0; j<size(); j++) {
                int bits =
            }
        }



        return result;
    }

    private boolean isBit(int number, int no) {
        return (no & 0x1) == 1;
    }

}