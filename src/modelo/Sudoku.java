package modelo;

public class Sudoku extends Observado {

    private int[][] tablero;

    public Sudoku(int[][] tableroInicial) {
        this.tablero = tableroInicial;
    }

    public int[][] getTablero() {
        return tablero;
    }

    public void resolverAsync() {
        // Simula un proceso asíncrono para no congelar la GUI
        new Thread(() -> {
            notificar("inicioResolucion", null);
            boolean exito = resolver();
            notificar("finResolucion", exito);
        }).start();
    }

    private boolean resolver() {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                if (tablero[fila][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (esValido(fila, col, num)) {
                            tablero[fila][col] = num;
                            if (resolver()) {
                                notificar("progreso", copiarMatriz(tablero));
                                return true;
                            }
                            tablero[fila][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean esValido(int fila, int col, int num) {
        for (int c = 0; c < 9; c++)
            if (tablero[fila][c] == num) return false;
        for (int f = 0; f < 9; f++)
            if (tablero[f][col] == num) return false;
        int inicioFila = (fila / 3) * 3;
        int inicioCol = (col / 3) * 3;
        for (int f = inicioFila; f < inicioFila + 3; f++)
            for (int c = inicioCol; c < inicioCol + 3; c++)
                if (tablero[f][c] == num) return false;
        return true;
    }

    public boolean esValidoInicial() {
        for (int fila = 0; fila < 9; fila++)
            for (int col = 0; col < 9; col++) {
                int valor = tablero[fila][col];
                if (valor != 0) {
                    tablero[fila][col] = 0;
                    boolean valido = esValido(fila, col, valor);
                    tablero[fila][col] = valor;
                    if (!valido) return false;
                }
            }
        return true;
    }

    private int[][] copiarMatriz(int[][] original) {
        int[][] copia = new int[9][9];
        for (int i = 0; i < 9; i++)
            System.arraycopy(original[i], 0, copia[i], 0, 9);
        return copia;
    }
}
