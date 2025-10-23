package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VentanaPrincipal extends JFrame {

    private PanelSudoku panelSudoku;
    private JButton btnResolver, btnGenerar, btnLimpiar;
    private JLabel lblEstado, lblTitulo;

    public VentanaPrincipal() {
        setTitle("🧩 Resolver Sudoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 0));

        inicializarComponentes(); // 🔹 Método separado para crear la interfaz
    }

    /** 
     * Inicializa y organiza todos los componentes visuales de la ventana.
     */
    private void inicializarComponentes() {
        add(crearPanelSuperior(), BorderLayout.NORTH);
        add(crearPanelCentral(), BorderLayout.CENTER);
        add(crearPanelBotones(), BorderLayout.SOUTH);
    }

    /** 
     * Crea el panel superior con el título y el estado.
     */
    private JPanel crearPanelSuperior() {
        JPanel panelSuperior = new JPanel(new BorderLayout());

        lblTitulo = new JLabel("🧩 SUDOKU", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        lblEstado = new JLabel(" ", SwingConstants.CENTER);
        lblEstado.setFont(new Font("Arial", Font.ITALIC, 14));
        lblEstado.setForeground(Color.DARK_GRAY);

        panelSuperior.add(lblTitulo, BorderLayout.NORTH);
        panelSuperior.add(lblEstado, BorderLayout.SOUTH);

        return panelSuperior;
    }

    /** 
     * Crea el panel central con la grilla del Sudoku.
     */
    private JPanel crearPanelCentral() {
        panelSudoku = new PanelSudoku();
        return panelSudoku;
    }

    /** 
     * Crea el panel inferior con los botones.
     */
    private JPanel crearPanelBotones() {
        JPanel panelBotones = new JPanel(new FlowLayout());

        btnGenerar = new JButton("Generar Aleatorio");
        btnResolver = new JButton("Resolver Sudoku");
        btnLimpiar = new JButton("Limpiar");

        panelBotones.add(btnGenerar);
        panelBotones.add(btnResolver);
        panelBotones.add(btnLimpiar);

        return panelBotones;
    }

    // 🔹 Getters y métodos de interacción con el controlador
    public PanelSudoku getPanelSudoku() { return panelSudoku; }

    public void setControlador(ActionListener controlador) {
        btnGenerar.addActionListener(controlador);
        btnResolver.addActionListener(controlador);
        btnLimpiar.addActionListener(controlador);
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
