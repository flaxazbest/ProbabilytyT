public class Event {

    private String description = null;
    private boolean[] elements = null;
    private double p;
    private Omega omega;

    public Event(String description, Omega o) {
        this.description = description;
        this.omega = o;
    }

    public Event(String description, Omega o, int... no) {
        if (no.length == o.size()) {
            this.description = o.getOmega().getDescription();
            this.elements = o.getOmega().getElements();
            this.omega = o;
        }
        else {
            this.description = description;
            this.omega = o;
            if (no.length != 0) {
                elements = new boolean[o.size()];
                for (int i = 0; i < no.length; i++)
                    elements[no[i] - 1] = true;
            }
        }
    }

    private double calc() {
        if (p < 0) {
            p = 0.0;
            for (int i = 0; i < omega.size(); i++) {
                if (elements[i]) {
                    if (omega.getProbabilities()[i] < 0.0) {
                        p = -1;
                        return p;
                    }
                    p += omega.getProbabilities()[i];
                }
            }
        }
        return p;
    }

    public Event(String description, Omega omega, boolean[] elements) {
        this.elements = elements;
        this.omega = omega;
        this.description = description;
    }

    public Event getOppositeEvent() {
        if (this.equals(omega.getOmega()))
            return omega.getEmpty();
        if (this.equals(omega.getEmpty()))
            return omega.getOmega();
        boolean[] tmp = new boolean[omega.size()];
        for (int i=0; i<omega.size(); i++) {
            tmp[i] = !elements[i];
        }
        return new Event("~"+description, omega, tmp);
    }

    public Event add(Event e) {
        boolean[] tmp = new boolean[omega.size()];
        if (e.equals(omega.getEmpty())) {
            for (int i=0; i<omega.size();i++)
                tmp[i] = elements[i];
        }
        else if (this.equals(omega.getEmpty())) {
            for (int i=0; i<omega.size();i++)
                tmp[i] = e.getElements()[i];
        }
        else {
            for (int i = 0; i < omega.size(); i++) {
                tmp[i] = elements[i] || e.getElements()[i];
            }
        }
        if (isFull(tmp))
            return omega.getOmega();
        return new Event(description+'+'+e.getDescription(), omega, tmp);
    }

    public Event intersect(Event e) {
        if (e.equals(omega.getEmpty()) || this.equals(omega.getEmpty())) {
            return omega.getEmpty();
        }
        else {
            boolean[] tmp = new boolean[omega.size()];
            for (int i = 0; i < omega.size(); i++) {
                tmp[i] = elements[i] && e.getElements()[i];
            }
            if (isEmpty(tmp))
                return omega.getEmpty();
            return new Event(description+'*'+e.getDescription(), omega, tmp);
        }
    }

    public Event sub(Event e) {
        if (this.equals(omega.getEmpty()))
            return omega.getEmpty();
        boolean[] tmp = new boolean[omega.size()];
        for (int i=0; i<omega.size();i++)
            tmp[i] = elements[i];
        if (!e.equals(omega.getEmpty())) {
            for (int i=0; i<omega.size();i++)
                if (e.getElements()[i])
                    tmp[i] = false;
        }
        if (isEmpty(tmp))
            return omega.getEmpty();
        return new Event(description+'-'+e.getDescription(), omega, tmp);
    }

    public boolean isPresent(int no) {
        return elements != null && elements[no];
    }

    @Override
    public String toString() {
        if (elements == null) {
            return "Empty = {}";
        }
        StringBuilder sb = new StringBuilder(description + " = {");
        boolean flag = false;
        for (int i=0; i<elements.length-1; i++)
            if (elements[i]) {
                if (flag)
                    sb.append("\", ");
                flag = true;
                sb.append("\"").append(omega.getName(i));
            }
        if (elements[elements.length-1]) {
            if (flag)
                sb.append("\", ");
            sb.append("\"").append(omega.getName(elements.length - 1));
        }
        sb.append("\"}");
        return sb.toString();
    }

    private boolean isEmpty(boolean[] arr) {
        for (int i=0; i<arr.length; i++)
            if (arr[i]) return false;
        return true;
    }

    private boolean isFull(boolean[] arr) {
        for (int i=0; i<arr.length; i++)
            if (!arr[i]) return false;
        return true;
    }

    public boolean equals(Event obj) {
        if (elements == null && obj.getElements() == null)
            return true;
        if (elements == null)
            return false;
        for (int i=0; i<omega.size(); i++)
            if (elements[i] != obj.isPresent(i))
                return false;
        return true;
    }

    public String getDescription() {
        return description;
    }

    public boolean[] getElements() {
        return elements;
    }

    public Omega getOmega() {
        return omega;
    }
}