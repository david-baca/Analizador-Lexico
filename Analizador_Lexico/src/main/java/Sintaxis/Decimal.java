package Sintaxis;

import Lexico.Diccionario;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import upqroo.analizador_lexico.Context;

public class Decimal {
    public static boolean Estado=false;//cuidado puede quedar asi cada que lo llames
    Context context =new Context();
    Diccionario diccionario= new Diccionario();
    
    public void evalueInit (){
        try{ //preteccion por si me paso del indeice XD
            if(context.Apartado.substring(context.i-1,context.i+7).equals("#Decimal")){
                Estado = true;
                String estructura = context.Apartado.substring(context.i-1,context.Apartado.length());
                if (estructura.contains(";")) {
                    
                    int indexPuntoComa = estructura.indexOf(';');
                    String contenidoBody = estructura.substring(0, indexPuntoComa);
                   
                    String expresionRegular = "#Decimal\\s+([a-zA-Z_][a-zA-Z0-9_]*)\\s*=\\s*(\\d+\\.\\d+)?;?";
                    Pattern patron = Pattern.compile(expresionRegular);
                    Matcher matcher = patron.matcher(contenidoBody);
                    
                    if(matcher.find()){
                        diccionario.addData("Decimal",matcher.group(1), matcher.group(2));
                        context.saltar(contenidoBody.length());
                    }
                    
                    if(!patron.matcher(contenidoBody).matches()){
                        context.ERROR("Declaracion de #Decimal incorrecta");
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
