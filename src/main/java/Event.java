public class Event {

    private String description = null;
    private int[] elements;
    private Omega omega;

    public Event(String description, Omega o, int... no) {
        this.description = description;
        this.omega = o;
        if (no.length != 0)  {
            elements = new int[no.length];
            for (int i=0; i<no.length; i++)
                elements[i] = omega.get(no[i]-1);
        }
    }

    public Event getOppositeEvent() {
        if (this.equals(omega.EMPTY))
            return omega.OMEGA;
        int n = omega.size() - elements.length;
        if (n == 0)
            return omega.EMPTY;
        int[] tmp = new int[n];
        int k = 0;
        for (int i=0; i<omega.size(); i++) {
            int j=0;
            for (; j<elements.length; j++) {
                if (omega.get(i) == elements[j])
                    break;
            }
            if (j == elements.length)
                tmp[k++] = i+1;
        }
        return new Event("~"+description, omega, tmp);
    }

    @Override
    public String toString() {
        if (elements == null || elements.length == 0)
            return description;
        StringBuilder sb = new StringBuilder(description + " = {");
        for (int i=0; i<elements.length-1; i++)
            sb.append("\"").append(elements[i]).append("\", ");
        sb.append("\"").append(elements[elements.length-1]).append("\"}");
        return sb.toString();
    }
}
