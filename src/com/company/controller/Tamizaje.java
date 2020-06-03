package com.company.controller;

import com.company.Main;
import com.company.indice.archivoMaestro;
import com.company.motroInferncia.MotoroInferencia;
import com.company.motroInferncia.Reglas;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.geom.RectangularShape;
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
    private ArrayList<String>resultados = new ArrayList<>();
    private ArrayList<Reglas>reglasAplicadas = new ArrayList<>();
    private String mostrarJus = "";
    private String reco = "";
    String Diagnostico_Final = "";
    public Stage StageP1;


    @FXML
    TextField peso_id, altura_id, gender_id, edad_id, cintura_id, ejercicio_id, padres_id, hermanos_id, bebe_id;
    @FXML
    TextArea diagnosarea;
    @FXML
    Button diagnos,btnHome;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        diagnosarea.setEditable(false);
        diagnosarea.setStyle("-fx-opacity: 1;");
        diagnos.setOnAction(handler);
        btnHome.setOnAction(handler);
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
            if (event.getSource()==btnHome){
                regresar();
            }

        }
    } ;

    public void Diagnosticar ()
    {
        Diagnostico_Final = "";
        mostrarJus = "";
        reco = "";
        double peso = Double.parseDouble(peso_id.getText());
        double altura = Double.parseDouble(altura_id.getText());
        String imc_hecho = IMC(peso,altura);
        int edad = Integer.parseInt(edad_id.getText());
        String edad_hecho = edad(edad);
        double cint = Double.parseDouble(cintura_id.getText());
        char genero = gender_id.getText().charAt(0);
        String genero_hecho = generoh(genero);
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
        resultados = motor.inferir("K");
        reglasAplicadas = motor.getReglasAplicadas();
        justificacion();
        diagnosarea.setText(mostrarJus);

    }

    private void justificacion(){
        String aux = "";
        for(String aplicada : resultados){
            String ap = convertirHecho(aplicada);
            if (!ap.equalsIgnoreCase("NONE")){
                aux = "\n";
                aux += ap;
                aux += "\n";
                mostrarJus += aux;
            }
        }
        getDiagnosticoFinal();
    }

    private String convertirHecho(String s){
        String convertida = "";
        switch (s){
            case "A(sobrepeso)":
                convertida = "Usted tiene sobrepeso";
                break;
            case "A(obeso)":
                convertida = "Usted tiene obesidad";
                break;
            case "A(normal)":
                convertida = "Usted esta en peso normal";
                break;
            case "D(mayor_80)":
                convertida = "Su cintura es muy grande";
                break;
            case "D(mayor_90)":
                convertida = "Su cintura es muy grande";
                break;
            case "C(mayor_65)":
                convertida = "Se encuentra en un rango de edad muy vulnerable";
                break;
            case "C(45_64)":
                convertida = "Se encuentra en un rango de edad vulnerable";
                break;
            case "E(no)":
                convertida = "Usted no realiza ejercicio";
                break;
            case "F(padres)":
                convertida = "Usted cuenta con antecedentes familiares";
                break;
            case "F(hermanos)":
                convertida = "Usted cuenta con antecedentes familiares";
                break;
            case "H(G(4kg))":
                convertida = "Dio a luz un bebe de más de 4kg";
                break;
            default:
                convertida = "NONE";
                break;
        }
        return convertida;
    }

    private void getDiagnosticoFinal(){
        for (String resultado : resultados){
            DeterminarRiesgo(resultado);
        }
        mostrarJus += Diagnostico_Final;

        for (String resultado : resultados){
            RecomendacionesGrl(resultado);
        }
        mostrarJus += reco;
    }

    private void DeterminarRiesgo(String factor){
        switch (factor){
            case "K":
                Diagnostico_Final += "\nDebido a esto el sistema considera que: "+"\n"+
                                    "Usted esta en alto riesgo de padecer diabetes."+"\n"+
                                    " Haga la determinación de azúcar en sangre y aún"+
                                    " si el resultado fuera negativo (Si la prueba de Glucemia es menor de 100 mg/dl )"+
                                    " practique estilos"+
                                    " de vida saludables y repita la determinación en un"+
                                    " año."+"\n";
                break;
            case "L":
                Diagnostico_Final +=  "\nDebido a esto el sistema considera que: "+"\n"+
                                        "Usted esta en bajo riesgo de tener diabetes."+"\n"+
                                        "Pero no olvide que en le futuro puede estar en"+
                                        " riesgo mayor."+
                                        "\nSe le recomienda adpotar un estilo de vida saludable"+
                                        " y aplicar nuevamente la prueba en 3 años."+"\n";
                break;
            default:
                Diagnostico_Final += "";
                break;

        }
    }

    private void RecomendacionesGrl(String factor){
        switch (factor){
            case "A(normal)":
                reco += "\nUsted tiene un IMC normal, no obstatne debe de: \n"+
                                     "Vigilar su peso, Realizar actividad física y alimentars"+
                                     "correctamente\n";
                break;
            case "A(sobrepeso)":
                reco +=    "\nUsted tiene sobrepeso, debería de: \n"+
                                        "Acudir a su médico";

                break;
            case "A(obeso)":
                reco +=    "Usted tiene obesidad: \n"+
                                        "Requiere intervenciones inmediatas";
                break;

        }
    }

    public String generoh(char _g){
        String g = "";
        if  (_g == 'f'){
            g = "mujer";
        }else{
            g = "hombre";
        }
        return g;
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
           bebeto="G(4kg)";
       else
           bebeto="NONE";
       return bebeto;
    }


    private void construirHechos(String IMC, String genero_paciente, String edad_paciente, String edad_paciente_menor, String cintura, String ejercicio, String pario, String diabetes_melitus2, String diabetes_melitus1){
        String hecho_imc = "A("+IMC+")";
        hechos.add(hecho_imc);

        String hecho_paciente = "B("+genero_paciente+")";
        hechos.add(hecho_paciente);

        String hecho_edad = "C("+edad_paciente+")";
        hechos.add(hecho_edad);

        if (edad_paciente_menor!="NONE"){
            String hecho_edad2 = "C("+edad_paciente_menor+")";
            hechos.add(hecho_edad2);
        }
        if(cintura != "NONE"){
            String hecho_cintura = "D("+cintura+")";
            hechos.add(hecho_cintura);
        }

        String hecho_ejercicio = "E("+ejercicio+")";
        hechos.add(hecho_ejercicio);

        if  (pario != "NONE"){
            String hecho_pario = "H("+pario+")";
            hechos.add(hecho_pario);
        }

        if (diabetes_melitus2 != "NONE"){
            String hecho_dm2 = "F("+diabetes_melitus2+")";
            hechos.add(hecho_dm2);
        }

        if(diabetes_melitus1 != "NONE"){
            String hecho_dm1 = "F("+diabetes_melitus1+")";
            hechos.add(hecho_dm1);
        }
    }

    private void llenarbc () throws IOException {
        reglas = archi.getBC();
        ArrayList<String> rules = new ArrayList<>();
        String antef ="";
        ArrayList<Reglas> bc = new ArrayList<>();
        for(Reglas R : reglas){
            String[] espacios = R.getAntecedentes();
            rules = new ArrayList<>();
            for(int i = 0; i<espacios.length; i++){
                antef = "";
               if(!espacios[i].equalsIgnoreCase(cvacia)){
                    String[] antecedente = espacios[i].split(" ");
                    antef = antecedente[0];
                    rules.add(antef);
                }
            }
            String []consec_sin_espacios =R.getConsecuente().split(" ");
            Reglas t = new Reglas(rules,consec_sin_espacios[0],R.getIndice());
            bc.add(t);
        }
        reglas = bc;
    }

    public void regresar(){
        Parent actualizacion = null;
        try {
            actualizacion = FXMLLoader.load(getClass().getResource("../GUI/MenúPrincipalFXML.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene escena = new Scene(actualizacion, 600,600);
        StageP1 = Main.homeS;
        StageP1.setScene(escena);
    }



}
