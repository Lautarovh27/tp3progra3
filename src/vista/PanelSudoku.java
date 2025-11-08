package vista;

import javax.swing.*;
import javax.swing.text.*;
import controlador.ControladorSudoku;
import java.awt.*;
import javax.swing.event.*;

public class PanelSudoku extends JPanel {

    private JTextField[][] celdas;
    private ControladorSudoku controlador;
    private JLabel lblMensajeError;

    public PanelSudoku() {
        setLayout(new BorderLayout());

        JPanel grilla = new JPanel(new GridLayout(9, 9));
        celdas = new JTextField[9][9];

        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                JTextField campo = new JTextField();
                campo.setHorizontalAlignment(JTextField.CENTER);
                campo.setFont(new Font("Arial", Font.BOLD, 20));
                ((AbstractDocument) campo.getDocument()).setDocumentFilter(new FiltroNumerico());

                if ((fila / 3 + col / 3) % 2 == 0)
                    campo.setBackground(new Color(235, 235, 235));

                int f = fila, c = col;
                campo.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) { notificarCambio(); }

                    @Override
                    public void removeUpdate(DocumentEvent e) { notificarCambio(); }

                    @Override
                    public void changedUpdate(DocumentEvent e) {}

                    private void notificarCambio() {
                        if (controlador != null) {
                            String texto = campo.getText().trim();
                            int valor = 0;

                            if (!texto.isEmpty()) {
                                try {
                                    valor = Integer.parseInt(texto);
                                } catch (NumberFormatException ignored) {}
                            }
                            controlador.numeroIngresado(f, c, valor);
                        }
                    }
                });

                celdas[fila][col] = campo;
                grilla.add(campo);
            }
        }

        lblMensajeError = new JLabel(" ");
        lblMensajeError.setFont(new Font("Arial", Font.ITALIC, 13));
        lblMensajeError.setForeground(Color.RED);
        lblMensajeError.setHorizontalAlignment(SwingConstants.CENTER);

        add(grilla, BorderLayout.CENTER);
        add(lblMensajeError, BorderLayout.SOUTH);
    }

    public void setControlador(ControladorSudoku controlador) {
        this.controlador = controlador;
    }

    public int[][] obtenerValores() {
        int[][] valores = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String texto = celdas[i][j].getText();
                valores[i][j] = texto.isEmpty() ? 0 : Integer.parseInt(texto);
            }
        }
        return valores;
    }

    public void mostrarValores(int[][] valores) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JTextField celda = celdas[i][j];
                if (valores[i][j] != 0) {
                    celda.setText(String.valueOf(valores[i][j]));
                    celda.setEditable(false);
                    celda.setForeground(Color.BLUE);
                } else {
                    celda.setText("");
                    celda.setEditable(true);
                    celda.setForeground(Color.BLACK);
                }
            }
        }
    }

    public void limpiar() {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                JTextField celda = celdas[fila][col];
                celda.setText("");
                celda.setEditable(true);
                celda.setForeground(Color.BLACK);

                
                if ((fila / 3 + col / 3) % 2 == 0) {
                    celda.setBackground(new Color(235, 235, 235));
                } else {
                    celda.setBackground(Color.WHITE);
                }
            }
        }
        limpiarMensajeError();
    }


    public void mostrarMensajeError(String mensaje) {
        lblMensajeError.setText(mensaje);
    }

    public void limpiarMensajeError() {
        lblMensajeError.setText(" ");
    }
    
    public void actualizarDesdeMatriz(int[][] matriz) {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                int valor = matriz[fila][col];
                JTextField celda = celdas[fila][col];
                String textoActual = celda.getText();

                String nuevoTexto = valor == 0 ? "" : String.valueOf(valor);
                if (!textoActual.equals(nuevoTexto)) {
                    celda.setText(nuevoTexto);
                }
            }
        }
    }
    
    private static class FiltroNumerico extends DocumentFilter {
        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            if (text == null) return;

            String nuevoTexto = fb.getDocument().getText(0, fb.getDocument().getLength());
            nuevoTexto = nuevoTexto.substring(0, offset) + text + nuevoTexto.substring(offset + length);

            if (nuevoTexto.isEmpty()) {
                super.replace(fb, offset, length, text, attrs);
            } else if (nuevoTexto.length() <= 1 && nuevoTexto.matches("[1-9]")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String text, AttributeSet attrs)
                throws BadLocationException {
            replace(fb, offset, 0, text, attrs);
        }
    }
}
