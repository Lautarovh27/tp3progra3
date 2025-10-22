package main;

import controlador.ControladorSudoku;
import vista.VentanaPrincipal;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            VentanaPrincipal vista = new VentanaPrincipal();
            new ControladorSudoku(vista);
            vista.setVisible(true);
        });
    }
}