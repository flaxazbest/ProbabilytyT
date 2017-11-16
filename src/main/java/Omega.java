public class Omega {

    private int[] elements;

    public final Event EMPTY = new Event("Empty", this);
    public final Event OMEGA;

    public Omega(int size) {
        elements = new int[size];
        for (int i=0; i<size; i++)
            elements[i] = i+1;
        OMEGA = new Event("Omega", this, elements);
    }
    public int size() {
        return elements.length;
    }

    public int get(int no) {
        return elements[no];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Omega = {");
        for (int i=0; i<elements.length-1; i++)
            sb.append("\"").append(elements[i]).append("\", ");
        sb.append("\"").append(elements[elements.length-1]).append("\"}");
        return sb.toString();
    }

}
