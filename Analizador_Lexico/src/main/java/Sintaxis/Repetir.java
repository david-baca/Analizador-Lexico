package Sintaxis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import upqroo.analizador_lexico.Context;

public class Repetir {
    Context context =new Context();
    
    public void evalueInit (){
        try{ //preteccion por si me paso del indeice XD
            if(context.Apartado.substring(context.i-1,context.i+6).equals("Repetir")){
                String estructura = context.Apartado.substring(context.i-1,context.Apartado.length());
                if (estructura.contains(")")) {
                    int indexParetesis = estructura.indexOf(')');
                    String contenidoBody = estructura.substring(0, indexParetesis+1);
                    String expresionRegular = "\\bRepetir\\s*\\((.*?)\\)\\s*";
                    Pattern patron = Pattern.compile(expresionRegular);
                    Matcher matcher = patron.matcher(contenidoBody);
                    
                    if(matcher.find()){
                        //evaluar condicion
                        context.saltar(contenidoBody.length());
                    }
                    
                    if(!patron.matcher(contenidoBody).matches()){
                        context.ERROR("Declaracion de repeticion incorrecta");
                        context.saltar(contenidoBody.length());
                    }
                    
                } else {
                    context.ERROR("Se espera un ')' en la estructura.");
                    context.saltar(estructura.length()-1);
                }
                
            }
        } catch (StringIndexOutOfBoundsException e) {}
    }
}
