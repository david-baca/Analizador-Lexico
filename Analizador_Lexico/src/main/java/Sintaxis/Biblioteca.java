package Sintaxis;
import Lexico.Diccionario;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import upqroo.analizador_lexico.Context;
public class Biblioteca {
    public static boolean Estado=false;//cuidado puede quedar asi cada que lo llames
    Context context =new Context();
    
    public Biblioteca(){
    }
    
    public void evalueInit (){
        try{ //preteccion por si me paso del indeice XD
            if(context.Apartado.substring(context.i-1,context.i+9).equals("@Invoco_a:")){
                Estado = true;
                evalueName();
                context.saltar(context.i-context.Apartado.length());
            }
        } catch (StringIndexOutOfBoundsException e) {}
    }
   
    
    private void evalueName(){
        int max = context.Apartado.length();
        Diccionario dc = new Diccionario();
        String nombre = dc.evaluaEspacios(context.Apartado.substring(context.i+9,max));
        if(nombre!=null){
            dc.availabeName(nombre);
        }
    }
    
    public void evaluaContenido(){
    
    }
}
