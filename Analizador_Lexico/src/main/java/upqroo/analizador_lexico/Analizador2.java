package upqroo.analizador_lexico;

import Lexico.Diccionario;
import Sintaxis.Biblioteca;
import Sintaxis.Proceso;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;

public class Analizador2 {
    Context context = new Context(); 
    Diccionario diccionario = new Diccionario();
    
    Proceso proceso = new Proceso(); 
    Biblioteca biblioteca = new Biblioteca();
    
    public Analizador2(){
    }
    
    public void run(String texto){
        String[] linea = texto.split("\n");
        proceso.Estado = false;
        //para cada linea evaluamos que los caracteres sean validos
        for(int noline=0; noline<linea.length; noline++){
            biblioteca.Estado = false;
            context.NoLine = noline;
            context.Apartado= linea[noline];
            int longitud = context.Apartado.length();
            String errores = "";
            for (context.i = 2; context.i <= longitud; context.i++) {//patra cada caracter dentro de la linea
                context.Caracter = context.Apartado.substring(context.i-1, context.i);//obtenemos el primer caracter omitiendo el numero de linea
                diccionario.enDiccionario(context.Caracter);//analisamos si existe en nuetro diccionario de letras

                // Verificar si es una palabra reservada para iniciar
                if (context.Caracter.equals("@")) {
                    proceso.evalueEnd();
                }
                //verificar si estamos en el cuerpo de un proceso;
                if(proceso.Estado){proceso.evalueBody();}
                // Verificar si es una palabra reservada para iniciar
                if (context.Caracter.equals("@")) {
                    biblioteca.evalueInit();
                    proceso.evalueInit();
                    proceso.evalueEnd();
                }
                
                //verificamos que no haya saltos
                if(context.salto){context.salto=false; break;}
                // verificar si es un valor esperado
                if (!proceso.Estado && !biblioteca.Estado && !context.Caracter.equals(" ") && !context.Caracter.equals("\t")){
                    errores=errores+context.Caracter;
                }
            }
            if(errores.length()>0){context.ERROR("Valor no esperado [ "+errores+" ] se espera un inicio de cuerpo <Proceso:>");}
        }
    }

    public void Estructura(){
    
    }
}
