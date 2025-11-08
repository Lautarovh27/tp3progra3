package modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SudokuTest {

    @Test
    void testEsValidoInicial_Valido() {
        int[][] tablero = {
            {5,3,0, 0,7,0, 0,0,0},
            {6,0,0, 1,9,5, 0,0,0},
            {0,9,8, 0,0,0, 0,6,0},

            {8,0,0, 0,6,0, 0,0,3},
            {4,0,0, 8,0,3, 0,0,1},
            {7,0,0, 0,2,0, 0,0,6},

            {0,6,0, 0,0,0, 2,8,0},
            {0,0,0, 4,1,9, 0,0,5},
            {0,0,0, 0,8,0, 0,7,9}
        };

        Sudoku sudoku = new Sudoku(tablero);
        assertTrue(sudoku.esValidoInicial());
    }

    @Test
    void testEsValidoInicial_InvalidoPorFila() {
        int[][] tablero = new int[9][9];
        tablero[0][0] = 5;
        tablero[0][1] = 5;
        Sudoku sudoku = new Sudoku(tablero);
        assertFalse(sudoku.esValidoInicial());
    }

    @Test
    void testResolverAsync_EsperaHastaFinalizacion() throws InterruptedException {
        int[][] tablero = {
            {5,3,0, 0,7,0, 0,0,0},
            {6,0,0, 1,9,5, 0,0,0},
            {0,9,8, 0,0,0, 0,6,0},

            {8,0,0, 0,6,0, 0,0,3},
            {4,0,0, 8,0,3, 0,0,1},
            {7,0,0, 0,2,0, 0,0,6},

            {0,6,0, 0,0,0, 2,8,0},
            {0,0,0, 4,1,9, 0,0,5},
            {0,0,0, 0,8,0, 0,7,9}
        };

        Sudoku sudoku = new Sudoku(tablero);

        CountDownLatch latch = new CountDownLatch(1);

        // Registramos un observador para saber cuándo termina
        sudoku.agregarObservador((evento, dato) -> {
            if ("finResolucion".equals(evento)) {
                latch.countDown(); // desbloquea el test cuando termina
            }
        });

        sudoku.resolverAsync();

        // Esperamos un máximo de 10 segundos
        boolean terminado = latch.await(10, TimeUnit.SECONDS);

        assertTrue(terminado, "El proceso de resolución no terminó en el tiempo esperado");
        assertTrue(sudoku.esValidoInicial(), "El Sudoku resultante debe ser válido");
    }
}
