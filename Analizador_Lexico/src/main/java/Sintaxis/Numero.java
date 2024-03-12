package Sintaxis;
import Lexico.Diccionario;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import upqroo.analizador_lexico.Context;

public class Numero {
    Context context =new Context();
    Diccionario diccionario= new Diccionario();
    
    public void evalueInit (){
        try{ //preteccion por si me paso del indeice XD
            if(context.Apartado.substring(context.i-1,context.i+6).equals("#Numero")){
                String estructura = context.Apartado.substring(context.i-1,context.Apartado.length());
                if (estructura.contains(";")) {
                    
                    int indexPuntoComa = estructura.indexOf(';');
                    String contenidoBody = estructura.substring(0, indexPuntoComa);
                   
                    String expresionRegular = "#Numero\\s+([a-zA-Z_][a-zA-Z0-9_]*)\\s*(?:=\\s*([-+*/]?\\d+([-+*/]\\d+)*))?\\s*;?";
                    Pattern patron = Pattern.compile(expresionRegular);
                    Matcher matcher = patron.matcher(contenidoBody);
                    if(matcher.find()){
                        diccionario.addData("Numero",matcher.group(1), matcher.group(2));
                        context.saltar(contenidoBody.length());
                            
                    }
                    
                    if(!patron.matcher(contenidoBody).matches()){
                        context.ERROR("Declaracion de #Numero incorrecta");
                        context.saltar(contenidoBody.length());
                    }
                    
                } else {
                    context.ERROR("Se espera un ';' en la estructura.");
                    context.saltar(estructura.length()-1);
                }
                
            }
        } catch (StringIndexOutOfBoundsException e) {}
    }
   
    
}
