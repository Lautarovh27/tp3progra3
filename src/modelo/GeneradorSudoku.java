package modelo;

import java.util.Random;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class GeneradorSudoku {

    private static final int SIZE = 9;

    public static int[][] generar(int cantidadPrefijados) {
        if (cantidadPrefijados < 0 || cantidadPrefijados > 81)
            throw new IllegalArgumentException("Cantidad de prefijados inválida: " + cantidadPrefijados);

        int[][] tableroCompleto = new int[SIZE][SIZE];
        llenarTablero(tableroCompleto);

        int[][] tablero = SudokuUtils.copiarMatriz(tableroCompleto);
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

    private static boolean llenarTablero(int[][] tablero) {
        for (int fila = 0; fila < SIZE; fila++) {
            for (int col = 0; col < SIZE; col++) {
                if (tablero[fila][col] == 0) {
                    for (int num : numerosAleatorios()) {
                        if (SudokuUtils.esValido(tablero, fila, col, num)) {
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
        return true;
    }

    private static List<Integer> numerosAleatorios() {
        List<Integer> numeros = new ArrayList<>();
        for (int i = 1; i <= SIZE; i++) numeros.add(i);
        Collections.shuffle(numeros);
        return numeros;
    }
}
