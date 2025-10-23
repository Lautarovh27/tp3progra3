package modelo;

import java.util.Random;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class GeneradorSudoku {

    private static final int SIZE = 9;

    /**
     * Genera una grilla de Sudoku válida con una cantidad específica de valores prefijados.
     * @param cantidadPrefijados número de celdas visibles (1–81)
     * @return matriz 9x9 con los valores iniciales
     */
    public static int[][] generar(int cantidadPrefijados) {
        int[][] tableroCompleto = new int[SIZE][SIZE];
        llenarTablero(tableroCompleto);

        // Quitar valores para dejar solo los prefijados
        int[][] tablero = copiarMatriz(tableroCompleto);
        int quitar = 81 - cantidadPrefijados;
        Random rand = new Random();

        while (quitar > 0) {
            int fila = rand.nextInt(SIZE);
            int col = rand.nextInt(SIZE);
            if (tablero[fila][col] != 0) {
                tablero[fila][col] = 0;
                quitar--;
            }
        }
        return tablero;
    }

    /**
     * Llena un tablero vacío con una solución válida de Sudoku usando backtracking.
     */
    private static boolean llenarTablero(int[][] tablero) {
        for (int fila = 0; fila < SIZE; fila++) {
            for (int col = 0; col < SIZE; col++) {
                if (tablero[fila][col] == 0) {
                    for (int num : numerosAleatorios()) {
                        if (esValido(tablero, fila, col, num)) {
                            tablero[fila][col] = num;
                            if (llenarTablero(tablero))
                                return true;
                            tablero[fila][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true; // tablero completo
    }

    /**
     * Genera una lista de números del 1 al 9 en orden aleatorio.
     */
    private static List<Integer> numerosAleatorios() {
        List<Integer> numeros = new ArrayList<>();
        for (int i = 1; i <= SIZE; i++) numeros.add(i);
        Collections.shuffle(numeros);
        return numeros;
    }

    /**
     * Valida si se puede colocar un número en una posición dada.
     */
    private static boolean esValido(int[][] tablero, int fila, int col, int num) {
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

    private static int[][] copiarMatriz(int[][] original) {
        int[][] copia = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++)
            System.arraycopy(original[i], 0, copia[i], 0, SIZE);
        return copia;
    }
}
