package upqroo.analizador_lexico;

import Sintaxis.Numero;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;

public class Context {
    public static javax.swing.JTextArea Area;
    public static javax.swing.JTextArea Alertas;
    public static int NoLine;
    public static String Apartado;
    public static String Caracter;
    public static int i;
    public static boolean salto;
    static String[] Palabras;
    static int barril;
    
    static List<Numero> numeros = new ArrayList<>();
    
    public void setNumero (Numero numero){
        numeros.add(numero);
    }
    
    public void saltar(int no_casillas_a_saltar){
        salto=true;
        i=i+no_casillas_a_saltar;
    }
    
    public void Cleen(){
        Alertas.setText("");
    }
    
    public void setApartdado(String apartado){
        this.Apartado = apartado;
        this.Palabras = apartado.trim().split("\\s+");
        barril=1;//eliminamos el numero de linea 1 [lo importante...] [etc..]
    }
    
    public boolean Exist_Palabra(){
        if(Palabras.length == barril){return false;}
        return true;
    }
    
    public String Sig_Palabra(){
        String respuesta=Palabras[barril];
        barril++;
        return(respuesta);
        
    }
    
    public Context(){
    }
    
    public void ERROR(String error){
        setColorForText(Apartado, new Color(201, 53, 42),Apartado.length());
        Alertas.append("Error en línea: " + NoLine + ". "+ error + "\n");
    }
    
    public void ALERT(String alert){
        setColorForText(Apartado, new Color(209, 189, 0),1);
        Alertas.append("ALERT en línea: " + NoLine + ". "+ alert + "\n");
    }
    
    private void setColorForText(String text, Color backgroundColor, int lg) {
        DefaultHighlighter highlighter = (DefaultHighlighter) Area.getHighlighter();
        DefaultHighlighter.DefaultHighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(backgroundColor);
        try {
            int start = Area.getText().indexOf(text);//iniciamos donde se obtenga el texto especifico
            int end = start + lg;
            // Resalta la línea con el color de fondo deseado
            highlighter.addHighlight(start, end, painter);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
