package com.company;

import com.company.menu.Recuperar_Factores_Riesgo;

public class Main {

    public static void main(String[] args) {
        //Recuperar_Factores_Riesgo recuperar_factores = new Recuperar_Factores_Riesgo();
        String cadena1 = "HOLA";
        String cadena2 = "HOLA";
        String cadena3 = "ADIOS";

        boolean bandera1 = cadena1.equalsIgnoreCase(cadena2);
        boolean bandera2 = cadena1.equalsIgnoreCase(cadena3);
        if  (!bandera1){
            System.out.println("bandera 1");
        }
        if(!bandera2){
            System.out.println("bandera2");
        }



    }
}

