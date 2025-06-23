package expression.generic;

import static expression.generic.GenericMode.getMode;

public class GenericTabulator implements Tabulator {
    @Override
    public Object[][][] tabulate(
            String mode, String expression,
            int x1, int x2, int y1, int y2, int z1, int z2
    ) {
        InterfaceGenericMode<?> count_mode = getMode(mode);
        return tabulateGeneric(count_mode, expression, x1, x2, y1, y2, z1, z2);
    }

    private <T> Object[][][] tabulateGeneric(
            InterfaceGenericMode<T> mode, String expression,
            int x1, int x2, int y1, int y2, int z1, int z2
    ){
        GenericParser<T> parser = new GenericParser<>();
        IGenericExpression<T> expr = parser.parse(expression, mode);

        int sizeX = x2 - x1 + 1;
        int sizeY = y2 - y1 + 1;
        int sizeZ = z2 - z1 + 1;
        Object[][][] result = new Object[sizeX][sizeY][sizeZ];

        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                for (int k = 0; k < sizeZ; k++) {
                    try {
                        result[i][j][k] = expr.evaluate(
                                mode.parseType(String.valueOf(x1 + i)),
                                mode.parseType(String.valueOf(y1 + j)),
                                mode.parseType(String.valueOf(z1 + k)),
                                mode
                        );
                    } catch (ArithmeticException e) {
                        result[i][j][k] = null;
                    }
                }
            }
        }
        return result;
    }
}