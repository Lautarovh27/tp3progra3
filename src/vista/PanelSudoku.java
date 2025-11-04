package vista;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class PanelSudoku extends JPanel {

    private JTextField[][] celdas;

    public PanelSudoku() {
        setLayout(new GridLayout(9, 9));
        celdas = new JTextField[9][9];

        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                JTextField campo = new JTextField();
                campo.setHorizontalAlignment(JTextField.CENTER);
                campo.setFont(new Font("Arial", Font.BOLD, 20));

               
                ((AbstractDocument) campo.getDocument())
                        .setDocumentFilter(new FiltroNumerico());

                
                if ((fila / 3 + col / 3) % 2 == 0)
                    campo.setBackground(new Color(235, 235, 235));

                celdas[fila][col] = campo;
                add(campo);
            }
        }
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
                celdas[i][j].setText(valores[i][j] == 0 ? "" : String.valueOf(valores[i][j]));
            }
        }
    }

  
    public void limpiar() {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                celdas[i][j].setText("");
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
