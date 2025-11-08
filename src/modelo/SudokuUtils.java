package modelo;

public class SudokuUtils {

    private static final int SIZE = 9;

    private SudokuUtils() {}

    public static boolean esValido(int[][] tablero, int fila, int col, int num) {
        for (int c = 0; c < SIZE; c++)
            if (tablero[fila][c] == num) return false;

        for (int f = 0; f < SIZE; f++)
            if (tablero[f][col] == num) return false;

        int inicioFila = (fila / 3) * 3;
        int inicioCol = (col / 3) * 3;
        for (int f = inicioFila; f < inicioFila + 3; f++)
            for (int c = inicioCol; c < inicioCol + 3; c++)
                if (tablero[f][c] == num) return false;

        return true;
    }

    public static int[][] copiarMatriz(int[][] original) {
        int[][] copia = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++)
            System.arraycopy(original[i], 0, copia[i], 0, SIZE);
        return copia;
    }
}
