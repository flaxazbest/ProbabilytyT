import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        long n, p, k;
        double deca = max / 100.0, per = 0.0;
        System.out.println("Max = " + max);
        long start = System.currentTimeMillis();
        long finish;
        for (long i=0; i<max; i++) {
            if (i%10000 == 1) {
                App.log.info(i + "checks done;  " + list.size() + " spaces");
                finish = System.currentTimeMillis();
                k = (finish - start) / 1000;
                String stat = "Elapsed " + myToTime(k) + " Finished after ";
                k = ( k * (max - i) ) / i;
                stat = stat + myToTime(k);
                App.log.info(stat);
            }
            if (i > per) {
                per += deca;
                App.log.info(String.format("%.2f",per/max*100.0) + "% passed..." + list.size() + " spaces");
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
                App.log.warn(s);
                list.add(s);
            }
        }

        return list;
    }

    private String myToTime(long ms) {
        long d = ms/86400;
        ms -= d * 86400;
        long h = ms/3600;
        ms -= h * 3600;
        long m = ms / 60;
        ms -= m*60;
        return String.format("%03d:%02d:%02d:%02d",d,h,m,ms);
    }

}