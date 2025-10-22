package controlador;

import modelo.GeneradorSudoku;
import modelo.Sudoku;
import vista.VentanaPrincipal;
import vista.PanelSudoku;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorSudoku implements ActionListener {

    private VentanaPrincipal vista;

    public ControladorSudoku(VentanaPrincipal vista) {
        this.vista = vista;
        vista.setControlador(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == vista.getBtnResolver()) resolverSudoku();
        else if (src == vista.getBtnGenerar()) generarSudoku();
        else if (src == vista.getBtnLimpiar()) vista.getPanelSudoku().limpiar();
    }

    private void resolverSudoku() {
        PanelSudoku panel = vista.getPanelSudoku();
        int[][] valores = panel.obtenerValores();
        Sudoku sudoku = new Sudoku(valores);

        if (!sudoku.esValidoInicial()) {
            JOptionPane.showMessageDialog(vista, "⚠️ El Sudoku tiene valores inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (sudoku.resolver()) {
            panel.mostrarValores(sudoku.getTablero());
            JOptionPane.showMessageDialog(vista, "✅ Sudoku resuelto correctamente.");
        } else {
            JOptionPane.showMessageDialog(vista, "❌ No existe solución para este Sudoku.");
        }
    }

    private void generarSudoku() {
        String input = JOptionPane.showInputDialog(vista, "Ingrese cantidad de valores prefijados (1–81):", "Generar Sudoku", JOptionPane.QUESTION_MESSAGE);
        try {
            int n = Integer.parseInt(input);
            if (n < 1 || n > 81) throw new NumberFormatException();
            int[][] generado = GeneradorSudoku.generar(n);
            vista.getPanelSudoku().mostrarValores(generado);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Ingrese un número válido entre 1 y 81.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}