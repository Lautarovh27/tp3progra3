package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VentanaPrincipal extends JFrame {

    private PanelSudoku panelSudoku;
    private JButton btnResolver, btnGenerar, btnLimpiar;

    public VentanaPrincipal() {
        setTitle("🧩 Resolver Sudoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        panelSudoku = new PanelSudoku();
        add(panelSudoku, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());

        btnGenerar = new JButton("Generar Aleatorio");
        btnResolver = new JButton("Resolver Sudoku");
        btnLimpiar = new JButton("Limpiar");

        panelBotones.add(btnGenerar);
        panelBotones.add(btnResolver);
        panelBotones.add(btnLimpiar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    public PanelSudoku getPanelSudoku() {
        return panelSudoku;
    }

    public void setControlador(ActionListener controlador) {
        btnGenerar.addActionListener(controlador);
        btnResolver.addActionListener(controlador);
        btnLimpiar.addActionListener(controlador);
    }

    public JButton getBtnGenerar() { return btnGenerar; }
    public JButton getBtnResolver() { return btnResolver; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
