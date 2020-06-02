package com.company.controller;

import com.company.indice.archivoMaestro;
import com.company.motroInferncia.MotoroInferencia;
import com.company.motroInferncia.Reglas;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Tamizaje implements Initializable {
    MotoroInferencia motor;
    String cvacia ="                              ";
    String rango_edad="NONE";
    ArrayList<String> hechos = new ArrayList<>();
    ArrayList<Reglas> reglas = new ArrayList<>();
    archivoMaestro archi = new archivoMaestro();


    @FXML
    TextField peso_id, altura_id, gender_id, edad_id, cintura_id, ejercicio_id, padres_id, hermanos_id, bebe_id;
    @FXML
    TextArea diagnosarea;
    @FXML
    Button diagnos;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

        diagnos.setOnAction(handler);
        try {
            llenarbc();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>()
    {
        @Override
        public void handle(ActionEvent event)
        {
            if(event.getSource() == diagnos){
                rango_edad = "NONE";
                hechos = new ArrayList<>();
                Diagnosticar();
            }

        }
    } ;

    public void Diagnosticar ()
    {
        double peso = Double.parseDouble(peso_id.getText());
        double altura = Double.parseDouble(altura_id.getText());
        String imc_hecho = IMC(peso,altura);
        int edad = Integer.parseInt(edad_id.getText());
        String edad_hecho = edad(edad);
        double cint = Double.parseDouble(cintura_id.getText());
        char genero = gender_id.getText().charAt(0);
        String genero_hecho = gender_id.getText();
        String cintura_hecho = cintura(cint,genero);
        char ejercicio = ejercicio_id.getText().charAt(0);
        String ejercicio_hecho = ejercicio(ejercicio);
        char padre = padres_id.getText().charAt(0);
        String padres_hecho = padres(padre);
        char hermano = hermanos_id.getText().charAt(0);
        String hermano_hecho = hermanos(hermano);
        char bebe = bebe_id.getText().charAt(0);
        String bebe_hecho = bebeto(bebe, genero);

        construirHechos(imc_hecho, genero_hecho, edad_hecho, rango_edad, cintura_hecho, ejercicio_hecho, bebe_hecho, hermano_hecho, padres_hecho);
        motor = new MotoroInferencia(reglas,hechos);

    }

    public String IMC(double _peso, double _altura)
    {
        String imc ="";
        double calculo;
        calculo = (_peso / (Math.pow(_altura,2)));

        if( calculo >= 18.5 && calculo<= 24.9)
            imc = "normal";
        else if(calculo>24.9 && calculo<= 29.9)
            imc = "sobrepeso";
        else if(calculo > 29.9)
            imc ="obeso";
        else
            imc="bajo";
        return  imc;
    }

    public String edad(int _edad)
    {
        String age ="";
        if(_edad >=65)
            age = "mayor_65";
        else {
            age = "menor_65";
            if (_edad >= 45)
                rango_edad = "45_64";
        }
        return age;
    }

    public String cintura(double _cintura, char _genero)
    {
        String cint="NONE";
        if(_cintura >= 80.0) {

            if (_genero == 'f')
                cint = "mayor_80";
        }
        if(_cintura>=90.0){
            if(_genero == 'm')
                cint = "mayor_90";
        }

        return  cint;
    }

    public String ejercicio(char _ejercicio){
        String exer = "";
        if(_ejercicio == 's')
            exer = "si";
        else
            exer = "no";
        return exer;
    }

    public String padres (char _padres)
    {
        String parents ="";
        if(_padres == 's')
            parents="padres";
        else
            parents="NONE";
        return parents;
    }

    public String hermanos (char _hermano)
    {
        String bro ="";
        if(_hermano == 's')
            bro="hermanos";
        else
            bro="NONE";
        return bro;
    }

    public String bebeto (char _bebe, char _genero)
    {
       String bebeto ="";
       if(_genero=='f'&&_bebe=='s')
           bebeto="bebe(4kg)";
       else
           bebeto="NONE";
       return bebeto;
    }


    private void construirHechos(String IMC, String genero_paciente, String edad_paciente, String edad_paciente_menor, String cintura, String ejercicio, String pario, String diabetes_melitus2, String diabetes_melitus1){
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

    private void llenarbc () throws IOException {
        reglas = archi.getBC();
        ArrayList<String> rules = new ArrayList<>();
        String antef ="";
        ArrayList<Reglas> bc = new ArrayList<>();
        for(Reglas R : reglas){
            String[] espacios = R.getAntecedentes();

            for(int i = 0; i<espacios.length; i++){
                antef = "";
               if(!espacios[i].equalsIgnoreCase(cvacia)){
                String[] antecedente = espacios[i].split(" ");
                antef = antecedente[0];
                rules.add(antef);
                }
            }
            Reglas t = new Reglas(rules,R.getConsecuente(),R.getIndice());
            bc.add(t);
        }

        reglas = bc;


    }



}
