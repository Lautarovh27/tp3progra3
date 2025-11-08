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
    void testEsValidoInicial_InvalidoPorSubgrilla() {
        int[][] tablero = new int[9][9];
        tablero[0][0] = 5;
        tablero[1][1] = 5;
        Sudoku sudoku = new Sudoku(tablero);
        assertFalse(sudoku.esValidoInicial());
    }

    @Test
    void testIntentarColocarNumero_Valido() {
        int[][] tablero = new int[9][9];
        Sudoku sudoku = new Sudoku(tablero);

        boolean resultado = sudoku.intentarColocarNumero(0, 0, 5);
        assertTrue(resultado);
        assertEquals(5, sudoku.getTablero()[0][0]);
    }

    @Test
    void testIntentarColocarNumero_InvalidoPorColumna() {
        int[][] tablero = new int[9][9];
        tablero[1][0] = 5; 
        Sudoku sudoku = new Sudoku(tablero);

        boolean resultado = sudoku.intentarColocarNumero(0, 0, 5);
        assertFalse(resultado);
        assertEquals(0, sudoku.getTablero()[0][0]);
    }

    
    @Test
    void testGetEstadoActualDevuelveCopiaIndependiente() {
        int[][] tablero = new int[9][9];
        tablero[0][0] = 9;
        Sudoku sudoku = new Sudoku(tablero);

        int[][] copia = sudoku.getEstadoActual();
        copia[0][0] = 1;

        assertNotEquals(sudoku.getTablero()[0][0], copia[0][0],
                "La copia debe ser independiente del tablero interno");
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

        sudoku.agregarObservador((evento, dato) -> {
            if ("finResolucion".equals(evento)) {
                latch.countDown();
            }
        });

        sudoku.resolverAsync();
        boolean terminado = latch.await(10, TimeUnit.SECONDS);

        assertTrue(terminado, "El proceso de resolución no terminó en el tiempo esperado");
        assertTrue(sudoku.esValidoInicial(), "El Sudoku resultante debe ser válido");
    }

    @Test
    void testResolverAsync_SudokuIrresoluble() throws InterruptedException {
        int[][] tablero = new int[9][9];
        tablero[0][0] = 1;
        tablero[0][1] = 1;

        Sudoku sudoku = new Sudoku(tablero);
        CountDownLatch latch = new CountDownLatch(1);
        final boolean[] resultado = new boolean[1];

        sudoku.agregarObservador((evento, dato) -> {
            if ("finResolucion".equals(evento)) {
                resultado[0] = (boolean) dato;
                latch.countDown();
            }
        });

        sudoku.resolverAsync();
        latch.await(5, TimeUnit.SECONDS);

        assertFalse(resultado[0], "El Sudoku inválido no debería resolverse");
    }

  
    @Test
    void testNotificacionesDuranteResolucion() throws InterruptedException {
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
        CountDownLatch finLatch = new CountDownLatch(1);

        final boolean[] inicioNotificado = {false};
        final boolean[] progresoNotificado = {false};
        final boolean[] finNotificado = {false};

        sudoku.agregarObservador((evento, dato) -> {
            switch (evento) {
                case "inicioResolucion" -> inicioNotificado[0] = true;
                case "progreso" -> progresoNotificado[0] = true;
                case "finResolucion" -> {
                    finNotificado[0] = true;
                    finLatch.countDown();
                }
            }
        });

        sudoku.resolverAsync();
        finLatch.await(10, TimeUnit.SECONDS);

        assertTrue(inicioNotificado[0], "Debe notificarse inicioResolucion");
        assertTrue(progresoNotificado[0], "Debe notificarse progreso al menos una vez");
        assertTrue(finNotificado[0], "Debe notificarse finResolucion al terminar");
    }
}
