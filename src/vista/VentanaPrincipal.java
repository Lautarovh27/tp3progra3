package vista;

import controlador.ControladorSudoku;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VentanaPrincipal extends JFrame {

    private PanelSudoku panelSudoku;
    private JButton btnResolver, btnGenerar, btnLimpiar;
    private JLabel lblEstado, lblTitulo;

    public VentanaPrincipal() {
        setTitle("Resolver Sudoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 720);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        add(crearPanelSuperior(), BorderLayout.NORTH);
        add(crearPanelCentral(), BorderLayout.CENTER);
        add(crearPanelBotones(), BorderLayout.SOUTH);
    }

    private JPanel crearPanelSuperior() {
        JPanel panelSuperior = new JPanel(new BorderLayout());

        lblTitulo = new JLabel("SUDOKU", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        lblEstado = new JLabel(" ", SwingConstants.CENTER);
        lblEstado.setFont(new Font("Arial", Font.ITALIC, 14));
        lblEstado.setForeground(Color.DARK_GRAY);

        panelSuperior.add(lblTitulo, BorderLayout.NORTH);
        panelSuperior.add(lblEstado, BorderLayout.SOUTH);

        return panelSuperior;
    }

    private JPanel crearPanelCentral() {
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        panelSudoku = new PanelSudoku();
        contenedor.add(panelSudoku, BorderLayout.CENTER);

        return contenedor;
    }

    private JPanel crearPanelBotones() {
        JPanel panelBotones = new JPanel(new FlowLayout());

        btnGenerar = new JButton("Generar Aleatorio");
        btnResolver = new JButton("Resolver Sudoku");
        btnLimpiar = new JButton("Limpiar");

        btnGenerar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnResolver.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnLimpiar.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        panelBotones.add(btnGenerar);
        panelBotones.add(btnResolver);
        panelBotones.add(btnLimpiar);

        return panelBotones;
    }

    public PanelSudoku getPanelSudoku() { return panelSudoku; }

    public void setControlador(ActionListener controlador) {
        btnGenerar.addActionListener(controlador);
        btnResolver.addActionListener(controlador);
        btnLimpiar.addActionListener(controlador);

       
        if (controlador instanceof ControladorSudoku cs) {
            panelSudoku.setControlador(cs);
        }
    }

    public JButton getBtnGenerar() { return btnGenerar; }
    public JButton getBtnResolver() { return btnResolver; }
    public JButton getBtnLimpiar() { return btnLimpiar; }

    public void mostrarEstado(String mensaje) {
        lblEstado.setText(mensaje);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    
}
