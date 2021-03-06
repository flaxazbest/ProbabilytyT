import java.util.LinkedList;

public class Space {

    private int number;
    private Omega omega;
    private LinkedList<Event> events;

    public Space(Omega omega) {
        this(omega, 1);
    }

    public Space(Omega omega, int number) {
        this.omega = omega;
        events = new LinkedList<Event>();
        this.number = number;
        generateAll();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Space space = (Space) o;

        if (!omega.equals(space.omega)) return false;
        if (events.size() != space.events.size()) return false;
        for (Event e: events) {
            boolean in = false;
            for (Event se: space.events) {
                in |= (e.equals(se));
            }
            if (!in) return false;
        }
        return true;
    }

    public boolean addEvent(Event e) {
        if (!contain(e)) {
            events.add(e);
            return true;
        }
        return false;
    }

    public void generateAll() {
        boolean changes;
        do {
            changes = false;
            changes |= rule1();
            changes |= rule2();
            changes |= rule3();
        }
        while (changes);
    }

    private boolean rule1() {
        if (! contain(omega.getOmega())) {
            events.add(omega.getOmega());
            return true;
        }
        return false;
    }

    private boolean rule2() {
        boolean changes = false;
        int n = events.size();
        for (int i=0; i<n; i++) {
            Event ne = events.get(i).getOppositeEvent();
            if (!contain(ne)) {
                events.add(ne);
                changes = true;
            }
        }
        return changes;
    }

    private boolean rule3() {
        boolean changes = false;
        int n = events.size();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                Event e = events.get(i).add(events.get(j));
                changes |= addEvent(e);
            }
        }
        return changes;
    }

    private boolean contain(Event e) {
        for (Event ev: events)
            if (ev.equals(e)) return true;
        return false;
    }

    public LinkedList<Event> getEvents() {
        return events;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\nS" + number + " = {\n");
        for (Event e: events)
            sb.append("\t").append(e).append("\n");
        sb.append("}");
        sb.append("size " + events.size() + "\n\n");
        return sb.toString();
    }

}
