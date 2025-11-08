package modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class SudokuTest {

    private Sudoku sudoku;
    private int[][] tableroVacio;
    private int[][] tableroValido;
    private int[][] tableroInvalido;

    @BeforeEach
    void setUp() {
        tableroVacio = new int[9][9];

        tableroValido = new int[][]{
            {5,3,0,0,7,0,0,0,0},
            {6,0,0,1,9,5,0,0,0},
            {0,9,8,0,0,0,0,6,0},
            {8,0,0,0,6,0,0,0,3},
            {4,0,0,8,0,3,0,0,1},
            {7,0,0,0,2,0,0,0,6},
            {0,6,0,0,0,0,2,8,0},
            {0,0,0,4,1,9,0,0,5},
            {0,0,0,0,8,0,0,7,9}
        };

        tableroInvalido = new int[][]{
            {5,5,0,0,7,0,0,0,0}, 
            {6,0,0,1,9,5,0,0,0},
            {0,9,8,0,0,0,0,6,0},
            {8,0,0,0,6,0,0,0,3},
            {4,0,0,8,0,3,0,0,1},
            {7,0,0,0,2,0,0,0,6},
            {0,6,0,0,0,0,2,8,0},
            {0,0,0,4,1,9,0,0,5},
            {0,0,0,0,8,0,0,7,9}
        };
    }

    @Test
    void testIntentarColocarNumeroValido() {
        sudoku = new Sudoku(SudokuUtils.copiarMatriz(tableroVacio));
        assertTrue(sudoku.intentarColocarNumero(0, 0, 5));
        assertEquals(5, sudoku.getTablero()[0][0]);
    }

    @Test
    void testIntentarColocarNumeroInvalido() {
        sudoku = new Sudoku(SudokuUtils.copiarMatriz(tableroValido));
        assertFalse(sudoku.intentarColocarNumero(0, 2, 5));
    }

    @Test
    void testIntentarColocarNumeroCero() {
        sudoku = new Sudoku(SudokuUtils.copiarMatriz(tableroValido));
        assertTrue(sudoku.intentarColocarNumero(0, 2, 0)); 
        assertEquals(0, sudoku.getTablero()[0][2]);
    }


    @Test
    void testEsValidoInicial_Valido() {
        sudoku = new Sudoku(SudokuUtils.copiarMatriz(tableroValido));
        assertTrue(sudoku.esValidoInicial());
    }

    @Test
    void testEsValidoInicial_Invalido() {
        sudoku = new Sudoku(SudokuUtils.copiarMatriz(tableroInvalido));
        assertFalse(sudoku.esValidoInicial());
    }

    @Test
    void testEsValidoInicial_Vacio() {
        sudoku = new Sudoku(SudokuUtils.copiarMatriz(tableroVacio));
        assertTrue(sudoku.esValidoInicial());
    }

    @Test
    void testResolver_SudokuValido() {
        sudoku = new Sudoku(SudokuUtils.copiarMatriz(tableroValido));
        assertTrue(sudoku.resolver(), "Debe poder resolver un sudoku válido");
        assertTrue(sudoku.esValidoInicial(), "El resultado debe seguir siendo válido");
    }

    @Test
    void testResolver_SudokuInvalido() {
        sudoku = new Sudoku(SudokuUtils.copiarMatriz(tableroInvalido));
        assertFalse(sudoku.resolver(), "No debe resolver un sudoku inválido");
    }

    @Test
    void testResolver_SudokuVacio() {
        sudoku = new Sudoku(SudokuUtils.copiarMatriz(tableroVacio));
        assertTrue(sudoku.resolver(), "Debe poder resolver un sudoku vacío");
    }

    @Test
    void testNotificacionesObserver() {
        sudoku = new Sudoku(SudokuUtils.copiarMatriz(tableroValido));
        AtomicBoolean inicioNotificado = new AtomicBoolean(false);
        AtomicBoolean finNotificado = new AtomicBoolean(false);
        AtomicReference<Boolean> exito = new AtomicReference<>(false);

        sudoku.agregarObservador((evento, data) -> {
            if (evento.equals("inicioResolucion")) inicioNotificado.set(true);
            if (evento.equals("finResolucion")) {
                finNotificado.set(true);
                exito.set((Boolean) data);
            }
        });

        boolean resultado = sudoku.resolver();

        assertTrue(inicioNotificado.get(), "Debe notificar inicioResolucion");
        assertTrue(finNotificado.get(), "Debe notificar finResolucion");
        assertEquals(resultado, exito.get(), "El valor de éxito debe coincidir con la notificación");
    }

    @Test
    void testGetEstadoActualDevuelveCopia() {
        sudoku = new Sudoku(SudokuUtils.copiarMatriz(tableroValido));
        int[][] copia = sudoku.getEstadoActual();
        copia[0][0] = 9;
        assertNotEquals(copia[0][0], sudoku.getTablero()[0][0],
                "getEstadoActual debe devolver una copia independiente del tablero");
    }
}
