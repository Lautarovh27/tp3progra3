package modelo;

import java.util.Random;

public class GeneradorSudoku {

    public static int[][] generar(int cantidadPrefijados) {
        int[][] tablero = new int[9][9];
        Sudoku sudoku = new Sudoku(tablero);
        sudoku.resolver(); // genera un sudoku completo

        Random rand = new Random();
        int quitar = 81 - cantidadPrefijados;
        while (quitar > 0) {
            int fila = rand.nextInt(9);
            int col = rand.nextInt(9);
            if (tablero[fila][col] != 0) {
                tablero[fila][col] = 0;
                quitar--;
            }
        }
        return tablero;
    }
}