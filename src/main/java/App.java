public class App {

    public static void main(String[] args) {
        Omega O = new Omega(6);
        System.out.println(O);
        Event A = new Event("A", O, 1,2,4,5,3,6);
        System.out.println(A);
        Event B = A.getOppositeEvent();
        System.out.println(B);
        Event C = B.getOppositeEvent();
        System.out.println(C);
    }

}
