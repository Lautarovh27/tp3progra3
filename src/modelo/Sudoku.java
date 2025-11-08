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
                        if (SudokuUtils.esValido(tablero, fila, col, num)) {
                            tablero[fila][col] = num;
                            if (resolver()) {
                                notificar("progreso", SudokuUtils.copiarMatriz(tablero));
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
    
    public boolean intentarColocarNumero(int fila, int col, int valor) {
        if (valor == 0) {
            tablero[fila][col] = 0;
            return true;
        }
        int anterior = tablero[fila][col];
        tablero[fila][col] = 0;
        
        boolean valido = SudokuUtils.esValido(tablero, fila, col, valor);
        
        tablero[fila][col] = anterior;

    
        if (valido) {
            tablero[fila][col] = valor;
            return true;
        }
        return false;
    }
    
    public int[][] getEstadoActual() {
        return SudokuUtils.copiarMatriz(tablero);
    }

    public boolean esValidoInicial() {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                int valor = tablero[fila][col];
                if (valor != 0) {
                    tablero[fila][col] = 0;
                    boolean valido = SudokuUtils.esValido(tablero, fila, col, valor);
                    tablero[fila][col] = valor;
                    if (!valido) return false;
                }
            }
        }
        return true;
    }
}


