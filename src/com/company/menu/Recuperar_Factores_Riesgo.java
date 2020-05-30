package com.company.menu;

import javax.xml.bind.SchemaOutputResolver;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Recuperar_Factores_Riesgo {
    private String IMC;
    private String genero_paciente;
    private String edad_paciente;
    private String edad_paciente_menor;
    private String cintura;
    private String ejercicio;
    private String pario;
    private String diabetes_melitus1;
    private String diabetes_melitus2;

    private List<String>hechos;

    public Recuperar_Factores_Riesgo(){
        edad_paciente_menor = "NONE";
        pario = "NONE";
        hechos = new ArrayList<String>();
        introducir();
        System.out.printf(IMC);
        construirHechos();

    }

    private void introducir(){
        Scanner sc = new Scanner(System.in);
        double estatura;
        double peso;
        char g;
        int edad;
        System.out.println("Indique genero F ó M");
        g = sc.nextLine().toLowerCase().charAt(0);
        genero_paciente = idnidcarGenero(g);
        System.out.println("Ingrese estatura: ");
        estatura = sc.nextDouble();
        System.out.println("Ingresa peso: ");
        peso = sc.nextDouble();
        IMC = calcauarImc(peso,estatura);
        System.out.println("Ingresar edad: ");
        edad = sc.nextInt();
        edad_paciente = calcularEdad(edad);
        formulario();
    }

    private void formulario(){
        System.out.println("Conteste S/N");
        Scanner sc = new Scanner (System.in);
        char respuesta = 's';
        medida_cintura(sc,respuesta);
        hace_ejercicio(sc,respuesta);
        antecedentes(sc,respuesta);
    }

    public void medida_cintura(Scanner sc, char respuesta){
        if (genero_paciente=="mujer"){
            System.out.println("su cintura mide 80 o más");
            respuesta = sc.nextLine().toLowerCase().charAt(0);
            if (respuesta == 's'){
                cintura = "mayor_80";
            }else if(respuesta == 'n'){ cintura = "NONE";}
            else {
                System.out.println("ingrese una respuesta correcta");
                medida_cintura(sc,respuesta);
            }
            bebe_pesado(sc,respuesta);
        }else if(genero_paciente == "hombre"){
            System.out.println("su cintura mide 90 o más");
            respuesta = sc.nextLine().toLowerCase().charAt(0);
            if (respuesta == 's'){
                cintura = "mayor_90";
            }else if(respuesta == 'n'){cintura = "NONE";}
            else {
                System.out.println("ingrese una respuesta correcta");
                medida_cintura(sc,respuesta);
            }
        }else{
            System.out.println("Error genero del paciente");
            System.exit(0);
        }
    }

    private void bebe_pesado(Scanner sc, char respuesta){
        System.out.println("pario un bebe de más de 4 kg");
        respuesta = sc.nextLine().toLowerCase().charAt(0);
        if (respuesta == 's'){
            pario = "bebe(4kg)";
        }else if(respuesta == 'n'){
            pario = "NONE";
        }else{
            System.out.printf("Intrudce una respuesta valida");
            bebe_pesado(sc,respuesta);
        }
    }

    private void hace_ejercicio(Scanner sc, char respuesta){
        if  (edad_paciente == "menor_65"){
            System.out.println("realiza ejercicio");
            respuesta = sc.nextLine().toLowerCase().charAt(0);
            if (respuesta == 's'){
                ejercicio = "si";
            }else if(respuesta == 'n'){ ejercicio = "no";}
            else{
                System.out.println("ingrese una respuesta valida");
                hace_ejercicio(sc,respuesta);
            }
        }
    }

    private void antecedentes(Scanner sc, char respuesta){
        System.out.println("Alguno de sus padres padece o padeció\n" +
                "diabetes mellitus");
        respuesta = sc.nextLine().toLowerCase().charAt(0);
        if (respuesta == 's'){
            diabetes_melitus1 = "padres";
        }else if(respuesta == 'n'){
            diabetes_melitus1 = "NONE";
        }
        else{
            System.out.println("ingrese una respuesta correcta");
            antecedentes(sc,respuesta);
        }
        System.out.println("Alguno de sus hermanos padece o padeció\n" +
                "diabetes mellitus");
        respuesta = sc.nextLine().toLowerCase().charAt(0);
        if (respuesta == 's'){
            diabetes_melitus2 = "hermanos";
        }else if(respuesta == 'n'){
            diabetes_melitus2 = "NONE";
        }else{
            System.out.println("ingrese una respuesta valida");
            antecedentes(sc,respuesta);
        }
    }

    private String calcularEdad(int edad){
        String edad_rango = "NONE";
        if(edad>=65){
            edad_rango = "mayor_65";
        }else {
            edad_rango = "menor_65";
            if (edad>=45){
                edad_paciente_menor = "45_64";
            }
        }
        return edad_rango;
    }

    private String idnidcarGenero(char g){
        String gen = "NONE";
        switch (g){
            case 'f':
                gen = "mujer";
                break;
            case  'm':
                gen = "hombre";
                break;
        }
        return gen;
    }

    private String calcauarImc (double peso, double estatura){
        String imc;
        double calculo = (peso/Math.pow(estatura,2));
        System.out.println("calculo==============");
        System.out.println(""+calculo);
        if(calculo>=18.5 && calculo<=24.9){
            imc = "normal";
        }else if (calculo>24.9 && calculo<=29.9){
            imc = "sobrepeso";
        }else if(calculo>29.9){
            imc = "obeso";
        }else{
            imc = "bajo";
        }
        return imc;
    }

    private void construirHechos(){
        String hecho_imc = "imc("+IMC+")";
        hechos.add(hecho_imc);

        String hecho_paciente = "paciente("+genero_paciente+")";
        hechos.add(hecho_paciente);

        String hecho_edad = "edad("+edad_paciente+")";
        hechos.add(hecho_edad);

        if (edad_paciente_menor!="NONE"){
            String hecho_edad2 = "edad("+edad_paciente_menor+")";
            hechos.add(hecho_edad2);
        }
        if(cintura != "NONE"){
            String hecho_cintura = "cintura("+cintura+")";
            hechos.add(hecho_cintura);
        }

        String hecho_ejercicio = "ejercicio("+ejercicio+")";
        hechos.add(hecho_ejercicio);

        if  (pario != "NONE"){
            String hecho_pario = "pario("+pario+")";
            hechos.add(hecho_pario);
        }

        if (diabetes_melitus2 != "NONE"){
            String hecho_dm2 = "diabetes_melitus("+diabetes_melitus2+")";
            hechos.add(hecho_dm2);
        }

        if(diabetes_melitus1 != "NONE"){
            String hecho_dm1 = "diabetes_melitus("+diabetes_melitus1+")";
            hechos.add(hecho_dm1);
        }

        mostrarHechos();
    }

    private void mostrarHechos(){
        for (int i = 0; i<hechos.size();i++){
            System.out.println(hechos.get(i));
        }
    }




}
