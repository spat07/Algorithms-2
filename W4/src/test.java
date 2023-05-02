public class test {
    public static void main(String[] args) {
        System.out.println("hello world!");
        boolean a1[][] = new boolean[4][4];
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                System.out.printf("%b", a1[i][j]);
    }
}