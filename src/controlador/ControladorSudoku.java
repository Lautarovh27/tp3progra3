package controlador;

import modelo.GeneradorSudoku;
import modelo.Observador;
import modelo.Sudoku;
import modelo.SudokuUtils;
import vista.VentanaPrincipal;
import vista.PanelSudoku;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorSudoku implements ActionListener, Observador {

    private VentanaPrincipal vista;
    private Sudoku modelo;

    public ControladorSudoku(VentanaPrincipal vista) {
        this.vista = vista;
        vista.setControlador(this);
    }

    public void numeroIngresado(int fila, int col, int valor) {
        if (modelo == null) {
            modelo = new Sudoku(vista.getPanelSudoku().obtenerValores());
            modelo.agregarObservador(this);
        }

        modelo.intentarColocarNumero(fila, col, valor);
  
        int[][] estadoActual = vista.getPanelSudoku().obtenerValores();
      
        boolean valido = SudokuUtils.esTableroValido(estadoActual);

        if (!valido) {
            vista.getPanelSudoku().mostrarMensajeError("El número ya existe en la fila, columna o subgrilla.");
        } else {
            vista.getPanelSudoku().limpiarMensajeError();
        }
    }
    
    private void resolverAsync() {
        Thread hilo = new Thread(() -> modelo.resolver());
        hilo.start();
    }






    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        PanelSudoku panel = vista.getPanelSudoku();

        if (src == vista.getBtnResolver()) {
            int[][] valores = panel.obtenerValores();
            modelo = new Sudoku(valores);
            modelo.agregarObservador(this);

            if (!modelo.esValidoInicial()) {
                JOptionPane.showMessageDialog(vista, "El Sudoku tiene valores inválidos.");
                return;
            }

            resolverAsync();
        }else if (src == vista.getBtnGenerar()) {
            String input = JOptionPane.showInputDialog(vista, "Ingrese cantidad de valores prefijados (1–81):");
            if (input == null || input.isBlank()) return;

            try {
                int n = Integer.parseInt(input);
                if (n < 1 || n > 81) throw new NumberFormatException();

                int[][] generado = GeneradorSudoku.generar(n);
                modelo = new Sudoku(generado);
                modelo.agregarObservador(this);
                panel.mostrarValores(generado);
                panel.limpiarMensajeError();
                vista.mostrarEstado("Sudoku generado aleatoriamente.");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, "Ingrese un número válido entre 1 y 81.");
            }
        }
else if (src == vista.getBtnLimpiar()) {
            panel.limpiar();
            panel.limpiarMensajeError();
            modelo = null;
        }
    }

    @Override
    public void actualizar(String evento, Object data) {
        switch (evento) {
            case "inicioResolucion" -> vista.mostrarEstado("Resolviendo Sudoku...");
            case "progreso" -> vista.getPanelSudoku().actualizarDesdeMatriz((int[][]) data);
            case "finResolucion" -> {
                boolean exito = (boolean) data;
                if (exito) {
                    vista.getPanelSudoku().actualizarDesdeMatriz(modelo.getTablero());
                    vista.mostrarMensaje("Sudoku resuelto correctamente.");
                } else {
                    vista.mostrarMensaje("No existe solución.");
                }
                vista.mostrarEstado(" ");
            }
        }
    }

}

