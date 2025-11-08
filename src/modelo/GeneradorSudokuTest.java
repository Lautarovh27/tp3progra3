package modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

public class GeneradorSudokuTest {

    @Test
    void testCantidadPrefijadosNegativaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            GeneradorSudoku.generar(-1);
        });
    }

    @Test
    void testCantidadPrefijadosMayorA81LanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            GeneradorSudoku.generar(82);
        });
    }

    @Test
    void testGenerarCon0PrefijadosDevuelveTableroVacio() {
        int[][] tablero = GeneradorSudoku.generar(0);

        assertEquals(9, tablero.length);
        assertEquals(9, tablero[0].length);

        long cantidadNoVacias = contarCeldasNoVacias(tablero);
        assertEquals(0, cantidadNoVacias, "No debe haber celdas prefijadas");
    }

    @Test
    void testGenerarCon81PrefijadosDevuelveTableroLlenoValido() {
        int[][] tablero = GeneradorSudoku.generar(81);

        assertEquals(81, contarCeldasNoVacias(tablero));
        assertTrue(esTableroValido(tablero), "El tablero completo debe ser válido según las reglas del Sudoku");
    }

    @Test
    void testGenerarConCantidadPrefijadosEsCorrecta() {
        int cantidadPrefijados = 40;
        int[][] tablero = GeneradorSudoku.generar(cantidadPrefijados);

        assertEquals(cantidadPrefijados, contarCeldasNoVacias(tablero),
            "Debe haber exactamente " + cantidadPrefijados + " celdas prefijadas");
        assertTrue(esTableroValido(tablero), "Incluso con huecos, el tablero debe mantener validez parcial");
    }

    @Test
    void testDosGeneracionesSonDistintas() {
        int[][] tablero1 = GeneradorSudoku.generar(40);
        int[][] tablero2 = GeneradorSudoku.generar(40);

        assertFalse(sonTablerosIguales(tablero1, tablero2),
            "Dos tableros generados aleatoriamente no deberían ser idénticos");
    }

    private long contarCeldasNoVacias(int[][] tablero) {
        long count = 0;
        for (int[] fila : tablero) {
            for (int val : fila) {
                if (val != 0) count++;
            }
        }
        return count;
    }

    private boolean esTableroValido(int[][] tablero) {
        for (int i = 0; i < 9; i++) {
            if (!sinDuplicados(fila(tablero, i))) return false;
            if (!sinDuplicados(columna(tablero, i))) return false;
        }

        for (int fila = 0; fila < 9; fila += 3) {
            for (int col = 0; col < 9; col += 3) {
                if (!sinDuplicados(subgrilla(tablero, fila, col))) return false;
            }
        }
        return true;
    }

    private boolean sinDuplicados(int[] valores) {
        Set<Integer> vistos = new HashSet<>();
        for (int val : valores) {
            if (val != 0 && !vistos.add(val)) return false;
        }
        return true;
    }

    private int[] fila(int[][] tablero, int f) {
        return tablero[f];
    }

    private int[] columna(int[][] tablero, int c) {
        int[] col = new int[9];
        for (int i = 0; i < 9; i++) col[i] = tablero[i][c];
        return col;
    }

    private int[] subgrilla(int[][] tablero, int fila, int col) {
        int[] vals = new int[9];
        int k = 0;
        for (int i = fila; i < fila + 3; i++) {
            for (int j = col; j < col + 3; j++) {
                vals[k++] = tablero[i][j];
            }
        }
        return vals;
    }

    private boolean sonTablerosIguales(int[][] t1, int[][] t2) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (t1[i][j] != t2[i][j]) return false;
            }
        }
        return true;
    }
}
