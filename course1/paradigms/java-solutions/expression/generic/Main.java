package expression.generic;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            String mode = args[0].substring(1);
            String expr = args[1];
            Tabulator tabulator = new GenericTabulator();
            Object[][][] result = tabulator.tabulate(mode, expr, -2, 2, -2, 2, -2,  2);
            for (int i = 0; i <= 4; i++) {
                for (int j = 0; j <= 4; j++) {
                    for (int k = 0; k <= 4; k++) {
                        System.out.print(result[i][j][k] + " ");
                    }
                    System.out.println();
                }
                System.out.println();
            }
        } catch (IllegalArgumentException e) {
            System.err.println("First arg: -i, -d, -bi. Second arg: expression" + e.getMessage());
        }

    }
}
