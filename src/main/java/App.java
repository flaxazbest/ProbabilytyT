public class App {

    public static void main(String[] args) {
        Omega O = new Omega(6);
        Space space = new Space(O);
        Event A = new Event("A", O, 1);
        Event B = new Event("B", O, 2);
        Event C = new Event("C", O, 3);
        Event D = new Event("D", O, 4);
        Event E = new Event("E", O, 5);
        Event F = new Event("F", O, 6);
        space.addEvent(A);
        space.addEvent(B);
        space.addEvent(C);
        space.addEvent(D);
        space.addEvent(E);
        space.addEvent(F);
        space.generateAll();
        System.out.println(space);
    }

}
