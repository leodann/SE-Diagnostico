package com.company;

import com.company.indice.archivoMaestro;
import com.company.menu.Recuperar_Factores_Riesgo;
import com.company.motroInferncia.MotoroInferencia;
import com.company.motroInferncia.Reglas;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        //Recuperar_Factores_Riesgo recuperar_factores = new Recuperar_Factores_Riesgo();
        ArrayList<String>antecedentes1 = new ArrayList<>();
        ArrayList<String>antecedentes2 = new ArrayList<>();
        ArrayList<String>antecedentes3 = new ArrayList<>();
        ArrayList<String>antecedentes4 = new ArrayList<>();
        ArrayList<String>antecedentes5 = new ArrayList<>();
        ArrayList<String>antecedentes6 = new ArrayList<>();

        String a = "A                             ";
        String b = "B                             ";
        String c = "C                             ";
        String d = "D                             ";
        String e = "E                             ";
        String f = "F                             ";
        String g = "G                             ";
        String h = "H                             ";
        String i = "I                             ";
        String k = "K                             ";

        antecedentes1.add(f);
        antecedentes2.add(e);
        antecedentes3.add(g);
        antecedentes4.add(h);
        antecedentes5.add(b);
        antecedentes5.add(c);
        antecedentes6.add(k);

        Reglas r1 = new Reglas(antecedentes1,b,1);
        Reglas r2 = new Reglas(antecedentes2,c,2);
        Reglas r3 = new Reglas(antecedentes3,d,3);
        Reglas r4 = new Reglas(antecedentes4,i,4);
        Reglas r5 = new Reglas(antecedentes5,k,5);
        Reglas r6 = new Reglas(antecedentes6,a,6);

        ArrayList<Reglas>conocimiento = new ArrayList<>();
        conocimiento.add(r1);
        conocimiento.add(r2);
        conocimiento.add(r3);
        conocimiento.add(r4);
        conocimiento.add(r5);
        conocimiento.add(r6);

        ArrayList<String>BH = new ArrayList<>();
        BH.add(f);
        BH.add(e);
        BH.add(g);
        BH.add(h);

        //MotoroInferencia motor = new MotoroInferencia(conocimiento,BH);

        archivoMaestro am = new archivoMaestro();
        try {
            am.escribir_archivo_maestro(conocimiento);
            //System.out.println(""+ am.tamReg());
            //am.recuperarSecuencial();
            am.recuperarAleatorio();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


    }
}

