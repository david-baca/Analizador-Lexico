package upqroo.analizador_lexico;

import java.awt.Color;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;

public class Analizador {
    javax.swing.JTextArea Area;
    javax.swing.JTextArea Alertas;
    String[] PalabrasReservadas = {"#Numero", "#Texto", "#Gato", "#Decimal"};
    public Analizador(javax.swing.JTextArea Area, javax.swing.JTextArea Alertas){
        this.Area = Area;
        this.Alertas = Alertas;
    }
    
    boolean ZonaDeVariables;
    boolean InicioDelCuerpo;
    boolean FinDelCuerpo;
    
    public void Analizar(String Apartado){
        if (Apartado.contains("@invoco_a:")){ ZonaDeBiblotecas(Apartado);}
        
        for (String palabra : PalabrasReservadas) {
            if(Apartado.contains(palabra)){
                ZonaDeVariables(Apartado);
            }
        }
        
        if (Apartado.contains("@Proceso:")) InicioDelCuerpo(Apartado);
        if (Apartado.contains("@Iniciar:")) IniciarOtroCuerpo(Apartado);
        if (Apartado.contains("@Terminado")) FinDelCuerpo(Apartado);
    }
    
    private void ZonaDeBiblotecas(String Apartado){
        setColorForText(Apartado, new Color(0, 0, 0));
    }
    private void ZonaDeVariables(String Apartado){
        setColorForText(Apartado, new Color(32, 100, 234));
    }
    private void InicioDelCuerpo(String Apartado){
        if(this.InicioDelCuerpo == false){
            setColorForText(Apartado, new Color(41, 32, 109));
            this.InicioDelCuerpo = true;
        }else{
            setColorForText(Apartado, new Color(201, 53, 42));
            String[] saltos = Apartado.split(" ");
            Alertas.setText("Eror en linea: "+saltos[0]+" No se puede Declarar un proceso dendro de un proceso");
        }          
    }
    
    private void IniciarOtroCuerpo(String Apartado){
        if (this.InicioDelCuerpo == true) {
            //pinta de color verde
            setColorForText(Apartado, new Color(50, 130, 108));
        } else {
            setColorForText(Apartado, new Color(201, 53, 42));
            String[] saltos = Apartado.split(" ");
            Alertas.setText("Eror en linea: "+saltos[0]+" No se puede iniciar un proceso sin fuera de un proceso");
        }
    }
    
    private void FinDelCuerpo(String Apartado){
        if (this.InicioDelCuerpo == true) {
            //pinta de color verde
            setColorForText(Apartado, new Color(41, 32, 109));
            this.InicioDelCuerpo = false;
        } else {
            setColorForText(Apartado, new Color(201, 53, 42));
            String[] saltos = Apartado.split(" ");
            String texto = Alertas.getText()+"\n"+"Eror en linea: "+saltos[0]+" Se debe declarar el inicio de un Proceso para declarar su fin "+"\n";
            Alertas.setText(texto);
        }
    }
    
    private void setColorForText(String text, Color backgroundColor) {
        DefaultHighlighter highlighter = (DefaultHighlighter) Area.getHighlighter();
        DefaultHighlighter.DefaultHighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(backgroundColor);

        try {
            int start = Area.getText().indexOf(text);
            int end = start + text.length();

            // Resalta la línea con el color de fondo deseado
            highlighter.addHighlight(start, end, painter);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
