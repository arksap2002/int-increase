import java.util.ArrayList;

public class Lists {

    public static void main(String[] args) {
        ArrayList<Integer> /* BigInteger */ arrayList = new ArrayList<>();
        int[] /* BigInteger */ a = new int[10];
        int intNumber = 8;
        int /* BigInteger */ bigIntNumber = 8;
        int[] /* BigInteger */ b = new int[bigIntNumber + 1];
        for (int /* BigInteger */ i = 0; i < a.length; i++) {
            a[i] = i;
        }
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }
        for (int /* BigInteger */ i = 0; i < 100; i++) {
            arrayList.add(i);
        }
        for (int i = 0; i < 100; i++) {
            arrayList.add(i);
        }
        arrayList.remove(intNumber);
        arrayList.remove(bigIntNumber);

        for (int /* BigInteger */ i = 0; i < arrayList.size(); i++) {
            System.out.println(arrayList.get(i));
        }
    }
}
