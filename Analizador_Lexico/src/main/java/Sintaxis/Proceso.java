package Sintaxis;

import Lexico.Diccionario;
import static Sintaxis.Biblioteca.Estado;
import java.util.ArrayList;
import java.util.List;
import upqroo.analizador_lexico.Context;

public class Proceso {
    public static boolean Estado=false;//cuidado puede quedar asi cada que lo llames
    static List<String> procesosName = new ArrayList<>();
    Context context = new Context();
    Diccionario diccionario = new Diccionario();
    Biblioteca biblioteca = new Biblioteca();
    Numero numero = new Numero();
    Texto texto = new Texto();
    Gato gato = new Gato();
    Decimal decimal = new Decimal ();
    EsVerdad esverdad = new EsVerdad();
    
    public Proceso(){
    }
    
    public void evalueInit (){
       try{ //preteccion por si me paso del indeice XD
            if(context.Apartado.substring(context.i-1,context.i+8).equals("@Proceso:")){
               if(Estado){
                   context.ERROR("No se puede iniciar un Proceso dentro de otro");
               }else{
                   Estado = true;
                   evalueName();
                   context.saltar(context.i-context.Apartado.length());//le dice que se salte toda la linea actual
               }
            }
        } catch (StringIndexOutOfBoundsException e) {}
    }
    
    private void evalueName(){
        int max = context.Apartado.length();
        String nombre = diccionario.evaluaEspacios(context.Apartado.substring(context.i+8,max));
        if(nombre!=null){
            Diccionario dc = new Diccionario();
            if(dc.availabeName(nombre)){procesosName.add(nombre);}
        }
    }
    
    public void evalueBody(){
        boolean invocacion =false;
        if (context.Caracter.equals("@")) {
            invocacion=evalueExecute();
        }
        
        if (context.Caracter.equals("E")) {
            esverdad.evalueInit();
        }
        
        if (context.Caracter.equals("R")) {
            //valor
        }
        
        if(context.Caracter.equals("#")){
            numero.evalueInit();
            texto.evalueInit();
            gato.evalueInit();
            decimal.evalueInit();
        }
        
        if(context.salto){context.salto=false; return;} // si saltaron ya dieron un error
        
        if (!invocacion && !context.Caracter.equals(" ") && !context.Caracter.equals("\t")){
            String errores = context.Apartado.substring(context.i,context.Apartado.length());
            context.ERROR("Valor no esperado [ "+errores+" ] esparando.");
            context.saltar(context.Apartado.length()-context.i);
        }
    }
    
    public boolean evalueExecute(){
        try{ //preteccion por si me paso del indeice XD
            if(context.Apartado.substring(context.i-1,context.i+8).equals("@Iniciar:")){
                String busqnombre = diccionario.evaluaEspacios(context.Apartado.substring(context.i+8,context.Apartado.length()));
                context.saltar(context.Apartado.length()-context.i);//salta todo el apartado
                //validar que el nombre sea correcto
                for (String nombre : procesosName) {
                    if (nombre.equals(busqnombre)) {
                        return true;
                    }
                }
                if(diccionario.availabeName(busqnombre)){
                    context.ERROR(" ["+busqnombre+"] "+"No se ha declarado como proceso.");
                }
                return true;
            }
        } catch (StringIndexOutOfBoundsException e) {}
        return false;
    }
    
    
    public void evalueEnd(){
        try{ //preteccion por si me paso del indeice XD
            if(context.Apartado.substring(context.i-1,context.i+9).equals("@Terminado")){
               if(Estado){
                    Estado = false;
                    context.saltar(9);
               }else{
                   context.ERROR("No se puede TERMINAR un Proceso si no se ha INICIADO otro");
               }
            }
        } catch (StringIndexOutOfBoundsException e) {}
    }
}
