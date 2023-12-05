package upqroo.analizador_lexico;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;

public class Analizador {
    javax.swing.JTextArea Area;
    javax.swing.JTextArea Alertas;
    String[] PalabrasReservadas = {"#Numero", "#Texto", "#Gato", "#Decimal"};
    String[] Diccionario_L = {"Q","W","E","R","T","Y","U","I","O","P","A","S","D","F","G","H","J","K","L","Ñ","Z","X","C","V","B","N","M",
    "q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","ñ","z","x","c","v","b","n","m"};
    String[] Diccionario_N = {"0","1","2","3","4","5","6","7","8","9"};
    String[] Diccionario_S = {"[",":" , "]", ">" , "<" , "”" , "#" , "*" , ";" , "-" , "_" , "¿" , "?" ,"@" , "(" , ")", " ", "."};
    String[] Diccionario_OM = {"+" , "-" , "/" , "*"};
    String[] Diccionario_OL = {"=" , "=>" , "<=" , "<" , ">" , "¡="};
    public Analizador(javax.swing.JTextArea Area, javax.swing.JTextArea Alertas){
        this.Area = Area;
        this.Alertas = Alertas;
    }
    
    boolean ZonaDeVariables;
    boolean InicioDelCuerpo;
    boolean FinDelCuerpo;
    
    public void Terminado(String Apartado){
        if(this.InicioDelCuerpo == true){
            setColorForText(Apartado, new Color(201, 53, 42));
            String[] saltos = Apartado.split(" ");
            Alertas.setText("Alerta en linea: "+saltos[0]+"Cada proceso debe terminar");
        }
    }
    
    public void Analizar(String Apartado){
        if (Apartado.contains("@invoco_a:")){ ZonaDeBiblotecas(Apartado);}
        for (String palabra : PalabrasReservadas) {
            if(Apartado.contains(palabra)){
                ZonaDeVariables(Apartado);
            }
        }
        if (Apartado.contains("@Proceso:")) InicioDelCuerpo(Apartado);
        if (Apartado.contains("@iniciar:")) IniciarOtroCuerpo(Apartado);
        if (Apartado.contains("EsVerdad")) IniciarOtroCuerpo(Apartado);
        if (Apartado.contains("Repetir")) IniciarOtroCuerpo(Apartado);
        if (Apartado.contains("@Terminado")) FinDelCuerpo(Apartado);
        evaluarCaracteres(Apartado);
    }
    
    private void ZonaDeBiblotecas(String Apartado){
        System.out.println("Inicio de Zana de biblioteca");
        
        setColorForText(Apartado, new Color(0, 0, 0));
    }
    private void ZonaDeVariables(String Apartado){
        System.out.println("Zona Variables");
        setColorForText(Apartado, new Color(32, 100, 234));
    }
    private void InicioDelCuerpo(String Apartado){
        System.out.println("Zona del cuerpo");
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
        System.out.println("Iniciar otro cuerpo");
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
        System.out.println("Fin del cuerpo");
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
    
    public void Cleen(){
        DefaultHighlighter highlighter = (DefaultHighlighter) Area.getHighlighter();
        highlighter.removeAllHighlights();
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
    
private void evaluarCaracteres(String Apartado) {
    String evalua = Apartado.trim(); // Puedes querer quitar espacios en blanco al principio y al final
    int longitud = evalua.length();

    for (int i = 1; i < longitud; i++) {
        String x = evalua.substring(i, i + 1);
        if (!enDiccionario(x)) {
            setColorForText(Apartado, new Color(201, 53, 42));
            Alertas.append("Error en línea: " + Apartado + ". Caracter no permitido: " + x + "\n");
            return; // Terminar la evaluación si se encuentra un caracter no permitido
        }
    }
}

    private boolean enDiccionario(String caracter) {
        List<String> listaDiccionario = new ArrayList<>();
        listaDiccionario.addAll(Arrays.asList(PalabrasReservadas));
        listaDiccionario.addAll(Arrays.asList(Diccionario_L));
        listaDiccionario.addAll(Arrays.asList(Diccionario_N));
        listaDiccionario.addAll(Arrays.asList(Diccionario_S));
        listaDiccionario.addAll(Arrays.asList(Diccionario_OM));
        listaDiccionario.addAll(Arrays.asList(Diccionario_OL));
        String[] Diccionario = listaDiccionario.toArray(new String[0]);
        for (String letra : Diccionario) {
            if (letra.equals(caracter)) {
                return true;
            }
        }
        return false;
    }
}
