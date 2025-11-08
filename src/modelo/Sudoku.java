package modelo;

public class Sudoku extends Observado {

    private int[][] tablero;
    private int[][] ultimoTableroNotificado;
  

    public Sudoku(int[][] tableroInicial) {
        this.tablero = tableroInicial;
    }

    public int[][] getTablero() {
        return tablero;
    }

 

    public boolean resolver() {
        notificar("inicioResolucion", null);

        boolean exito = resolverRecursivo();

        notificar("finResolucion", exito);
        return exito;
    }

    private boolean resolverRecursivo() {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                if (tablero[fila][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (SudokuUtils.esValido(tablero, fila, col, num)) {
                            tablero[fila][col] = num;
                            notificar("progreso", SudokuUtils.copiarMatriz(tablero)); // opcional: reducir frecuencia
                            if (resolverRecursivo()) return true;
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


