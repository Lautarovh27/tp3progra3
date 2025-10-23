package controlador;

import modelo.GeneradorSudoku;
import modelo.Observador;
import modelo.Sudoku;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        PanelSudoku panel = vista.getPanelSudoku();

        if (src == vista.getBtnResolver()) {
            int[][] valores = panel.obtenerValores();
            modelo = new Sudoku(valores);
            modelo.agregarObservador(this);

            if (!modelo.esValidoInicial()) {
                JOptionPane.showMessageDialog(vista, "⚠️ El Sudoku tiene valores inválidos.");
                return;
            }

            modelo.resolverAsync();

        } else if (src == vista.getBtnGenerar()) {
            String input = JOptionPane.showInputDialog(vista, "Ingrese cantidad de valores prefijados (1–81):");
            try {
                int n = Integer.parseInt(input);
                if (n < 1 || n > 81) throw new NumberFormatException();
                int[][] generado = GeneradorSudoku.generar(n);
                panel.mostrarValores(generado);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, "Ingrese un número válido entre 1 y 81.");
            }

        } else if (src == vista.getBtnLimpiar()) {
            panel.limpiar();
        }
    }

    @Override
    public void actualizar(String evento, Object data) {
        switch (evento) {
            case "inicioResolucion" ->  vista.mostrarEstado(" ");
            case "progreso" -> vista.getPanelSudoku().mostrarValores((int[][]) data);
            case "finResolucion" -> {
                boolean exito = (boolean) data;
                if (exito)
                    vista.mostrarMensaje("✅ Sudoku resuelto correctamente.");
                else
                    vista.mostrarMensaje("❌ No existe solución.");
                vista.mostrarEstado(" "); // limpiar el estado
            }

        }
    }

}
