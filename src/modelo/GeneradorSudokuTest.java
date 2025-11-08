package modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GeneradorSudokuTest {

    @Test
    void testGenerar_Completo81Prefijados() {
        int[][] tablero = GeneradorSudoku.generar(81);
        assertTrue(tableroLleno(tablero));
    }

    @Test
    void testGenerar_Parcial40Prefijados() {
        int[][] tablero = GeneradorSudoku.generar(40);
        int llenas = contarCeldasLlenas(tablero);
        assertEquals(40, llenas);
    }

    @Test
    void testGenerar_ExcepcionPorParametrosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> GeneradorSudoku.generar(-1));
        assertThrows(IllegalArgumentException.class, () -> GeneradorSudoku.generar(100));
    }

    private boolean tableroLleno(int[][] t) {
        for (int[] fila : t)
            for (int val : fila)
                if (val == 0) return false;
        return true;
    }

    private int contarCeldasLlenas(int[][] t) {
        int count = 0;
        for (int[] fila : t)
            for (int val : fila)
                if (val != 0) count++;
        return count;
    }
}
