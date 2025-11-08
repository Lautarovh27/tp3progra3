package modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SudokuUtilsTest {

    @Test
    void testEsValido_CeldaValida() {
        int[][] tablero = new int[9][9];
        tablero[0][0] = 5;
        assertTrue(SudokuUtils.esValido(tablero, 0, 1, 3));
    }

    @Test
    void testEsValido_RepiteEnFila() {
        int[][] tablero = new int[9][9];
        tablero[0][0] = 5;
        assertFalse(SudokuUtils.esValido(tablero, 0, 3, 5));
    }

    @Test
    void testEsValido_RepiteEnColumna() {
        int[][] tablero = new int[9][9];
        tablero[0][0] = 8;
        assertFalse(SudokuUtils.esValido(tablero, 4, 0, 8));
    }

    @Test
    void testEsValido_RepiteEnSubcuadro() {
        int[][] tablero = new int[9][9];
        tablero[1][1] = 4;
        assertFalse(SudokuUtils.esValido(tablero, 2, 2, 4));
    }

    @Test
    void testCopiarMatriz_Independencia() {
        int[][] original = new int[9][9];
        original[0][0] = 7;

        int[][] copia = SudokuUtils.copiarMatriz(original);
        assertEquals(7, copia[0][0]);

        copia[0][0] = 0;
        assertNotEquals(original[0][0], copia[0][0]);
    }
}
