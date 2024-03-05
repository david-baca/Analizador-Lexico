package Vistas;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import upqroo.analizador_lexico.Analizador2;
import upqroo.analizador_lexico.Context;
import static upqroo.analizador_lexico.Context.Alertas;

public class Casilla extends javax.swing.JFrame {
    Context c = new Context();
    public Casilla() {
        initComponents();
        c.Area = this.Area;
        c.Alertas = this.Alertas;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Area = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        Alertas = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 51));

        Area.setEditable(false);
        Area.setBackground(new java.awt.Color(153, 153, 153));
        Area.setColumns(20);
        Area.setForeground(new java.awt.Color(255, 255, 255));
        Area.setRows(5);
        Area.setText("hola nundo");
        Area.setCaretColor(new java.awt.Color(255, 255, 255));
        Area.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        Area.setMargin(new java.awt.Insets(5, 5, 5, 5));
        Area.setSelectionColor(new java.awt.Color(204, 204, 255));
        jScrollPane1.setViewportView(Area);

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Analizador Lexico Mikencode");

        jButton1.setBackground(new java.awt.Color(19, 90, 199));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Importar Archivo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        Alertas.setBackground(new java.awt.Color(0, 0, 0));
        Alertas.setColumns(20);
        Alertas.setForeground(new java.awt.Color(255, 255, 153));
        Alertas.setRows(5);
        jScrollPane2.setViewportView(Alertas);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 486, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(18, 18, 18))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto (*.txt)", "txt");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            try {
                FileReader fileReader = new FileReader(archivo);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String texto = "";
                String linea;
                String cadenaInicial ="";
                int contador = 0;
                while ((linea = bufferedReader.readLine()) != null) {
                    texto += contador + " " + eliminarTextoDobleAsterisco(linea) + "\n";
                    if (contador < 5) {
                        cadenaInicial = texto;
                    }
                    contador++;
                }

                Area.setText(texto);
                Analizador2 gerente = new Analizador2();
                c.Cleen();
                DefaultHighlighter alerta = (DefaultHighlighter) Alertas.getHighlighter();
                alerta.removeAllHighlights();
                gerente.run(texto);

                
                JOptionPane.showMessageDialog(null, "Archivo leído correctamente");
                bufferedReader.close(); // Cierra el BufferedReader después de usarlo
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al leer el archivo", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed
private String eliminarTextoDobleAsterisco(String linea) {
    StringBuilder resultado = new StringBuilder();
    boolean dentroDeAsteriscos = false;

    for (char c : linea.toCharArray()) {
        if (c == '*' && dentroDeAsteriscos) {
            dentroDeAsteriscos = false;
            continue;
        }

        if (c == '*' && !dentroDeAsteriscos) {
            dentroDeAsteriscos = true;
            continue;
        }

        if (!dentroDeAsteriscos) {
            resultado.append(c);
        }
    }

    return resultado.toString();
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea Alertas;
    private javax.swing.JTextArea Area;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
