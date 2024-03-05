package Lexico;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import upqroo.analizador_lexico.Context;

public class Diccionario {
    String[] PalabrasReservadas = {"#Numero", "#Texto", "#Gato", "#Decimal"};
    String[] Diccionario_L = {"Q","W","E","R","T","Y","U","I","O","P","A","S","D","F","G","H","J","K","L","Ñ","Z","X","C","V","B","N","M",
    "q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","ñ","z","x","c","v","b","n","m"};
    String[] Diccionario_N = {"0","1","2","3","4","5","6","7","8","9"};
    String[] Diccionario_S = {"[",":" , "]", ">" , "<" , "”" , "#" , "*" , ";" , "-" , "_" , "¿" , "?" ,"@" , "(" , ")", " ", "\t","\n", "."};
    String[] Diccionario_OM = {"+" , "-" , "/" , "*"};
    String[] Diccionario_OL = {"=" , "=>" , "<=" , "<" , ">" , "¡="};
    Context context = new Context();
    public Diccionario(){}
    
    public void enDiccionario(String caracter) {
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
                return;
            }
        }
        context.ERROR("Caracter no permitido: " + caracter);
    }
    
    public boolean availabeName(String nombre){
        if(nombre!=null){
            // Definir la expresión regular para validar el nombre de la librería
            String expresionRegular = "^\\s*[_$]?[a-zA-Z][_a-zA-Z0-9$]*$";
            // Compilar la expresión regular
            Pattern patron = Pattern.compile(expresionRegular);
            // Verificar si el nombre de la librería coincide con la expresión regular
            if(!patron.matcher(nombre).matches()){
                if (nombre.endsWith(";")) {
                    context.ERROR("Valor no esperado [ ; ] en "+"[ "+nombre+" ] <Nombre no valido>");
                }else{
                    context.ERROR("[ "+nombre+" ] <Nombre no valido>");
                }
            }
            return(patron.matcher(nombre).matches());
        }
        return(false);
    }
    
    public String evaluaEspacios(String nombre){
        String _nombre = nombre.trim();
        if (!nombre.equals(_nombre)) {
            //context.ERROR("ALerta no es recomendable poner espacios");
            context.ALERT("No es recomandable dejar espacios.");
        }
        return(_nombre);
    }

    
    
}
