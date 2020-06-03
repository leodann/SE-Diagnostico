package com.company.controller;

import com.company.Main;
import com.company.indice.archivoMaestro;
import com.company.motroInferncia.Reglas;
import com.company.motroInferncia.ReglasTabla;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javax.xml.bind.SchemaOutputResolver;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ActualizacionController implements Initializable {
    public Stage StageP1;
    @FXML
    Button home_btn,edit_btn,delete_btn,add_btn,go_btn;
    @FXML
    Label llave_lbl, ant_lbl, cons_lbl;
    @FXML
    TextField llave_tf,ant_tf,cons_tf;
    @FXML
    HBox info_hb;
    @FXML
    TableView<ReglasTabla>reglas_tbl;

    private archivoMaestro am = new archivoMaestro();
    private ArrayList<Reglas> BaseConocimiento =new ArrayList<>();
    private boolean archivoExiste;
    private String cvacia;
    private int accion = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicarComponentes();
        home_btn.setOnAction(handler);
        edit_btn.setOnAction(handler);
        delete_btn.setOnAction(handler);
        add_btn.setOnAction(handler);
        go_btn.setOnAction(handler);
    }

    EventHandler<ActionEvent>handler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (event.getSource()==home_btn){
                try {
                    pantallafuncion(home_btn);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (event.getSource()==edit_btn){
                info_hb.setVisible(true);
                restablecerValores();
                mostrarLlaveField(true);
                mostrarAntField(true);
                mostrarConsField(true);
                go_btn.setVisible(true);
                accion = 1;

            }
            if (event.getSource()==delete_btn){
                info_hb.setVisible(true);
                restablecerValores();
                mostrarLlaveField(true);
                mostrarConsField(false);
                mostrarAntField(false);
                go_btn.setVisible(true);
                accion = 2;
            }
            if (event.getSource()==add_btn){
                info_hb.setVisible(true);
                restablecerValores();
                mostrarLlaveField(false);
                mostrarAntField(true);
                mostrarConsField(true);
                go_btn.setVisible(true);
                accion = 3;
            }
            if  (event.getSource()==go_btn){
                info_hb.setVisible(false);
                inicarComponentes();
                acciones(accion);
            }

        }
    };

    private void acciones(int accion){
        switch (accion){
            case 1:
                editar();
                break;
            case 2:
                eliminar();
                break;
            case 3:
                agregar();
                break;
            default:
                break;

        }
        recargarTabla();

    }

    private void editar(){
        int llave = Integer.parseInt(llave_tf.getText());
        String consecuente = cons_tf.getText().toUpperCase();
        String entrada = ant_tf.getText();

        String[] aux = entrada.split("&");
        ArrayList<String>antecedentes = new ArrayList<>();
        String antecendente_completo;
        String consecuente_completo;
        try {
            for (int i = 0; i<aux.length;i++){
                antecendente_completo = "";
                for (int j = 0; j<30;j++){
                    if (j<aux[i].length()){
                        antecendente_completo += aux[i].charAt(j);
                    }else{
                        antecendente_completo += " ";
                    }
                }
                antecedentes.add(antecendente_completo);
            }
            consecuente_completo = "";
            for (int i = 0 ; i<30;i++){
                if(i<consecuente.length()){
                    consecuente_completo += consecuente.charAt(i);
                }else{
                    consecuente_completo +=" ";
                }
            }
            consecuente = consecuente_completo;
            am.editar(llave,antecedentes,consecuente);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void eliminar(){
        try {
            int llave = Integer.parseInt(llave_tf.getText());
            am.eliminar(llave);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void agregar(){
        String consecuente = cons_tf.getText().toUpperCase();
        String entrada = ant_tf.getText();

        String[] aux = entrada.split("&");
        ArrayList<String>antecedentes = new ArrayList<>();
        String antecendente_completo;
        String consecuente_completo;
        try {
            for (int i = 0; i<aux.length;i++){
                antecendente_completo = "";
                for (int j = 0; j<30;j++){
                    if (j<aux[i].length()){
                        antecendente_completo += aux[i].charAt(j);
                    }else{
                        antecendente_completo += " ";
                    }
                }
                antecedentes.add(antecendente_completo);
            }
            consecuente_completo = "";
            for (int i = 0 ; i<30;i++){
                if(i<consecuente.length()){
                    consecuente_completo += consecuente.charAt(i);
                }else{
                    consecuente_completo +=" ";
                }
            }
            consecuente = consecuente_completo;
            ArrayList<Reglas>r = new ArrayList<>();
            r.add(new Reglas(antecedentes,consecuente,0));
            if (archivoExiste){
                am.añadir(r);
            }else{
                am.escribir_archivo(r,"conocimiento");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void inicarComponentes(){
        cvacia = "                              ";
        info_hb.setVisible(false);
        mostrarAntField(false);
        mostrarConsField(false);
        mostrarLlaveField(false);
        go_btn.setVisible(false);
        archivoExiste =am.inicializar();
        if  (!archivoExiste){
            AlertaNoexisteArchivo();

        }else{
            cargarTabla();
        }
    }

    private void AlertaNoexisteArchivo(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Precaución BC");
        alert.setHeaderText("Base de Conocimientos");
        alert.setContentText("El archivo de la base de conocimientos no existe o no contiene ninguna regla");
        alert.showAndWait();
    }

    private void recargarTabla(){
        ArrayList<ReglasTabla>rtbl = new ArrayList<>();
        ObservableList<ReglasTabla>obs = FXCollections.observableArrayList(rtbl);
        reglas_tbl.setItems(obs);
        cargarTabla();
    }

    private void cargarTabla(){
        String antecedente_final = "";
        ArrayList<ReglasTabla>rtbl = new ArrayList<>();
        try {
            BaseConocimiento = am.getBC();
            for (Reglas rbc : BaseConocimiento){
                antecedente_final = "";
                String[]antecedentes = rbc.getAntecedentes();
                for (int i = 0; i<antecedentes.length;i++){
                    if (!antecedentes[i].equalsIgnoreCase(cvacia)){
                        String[] antecedente_sin_espacios = antecedentes[i].split(" ");
                        if (i != 0){
                            antecedente_final +=" & ";
                        }
                        antecedente_final  += antecedente_sin_espacios[0];
                    }
                }
                String[]cons_sin_espacios = rbc.getConsecuente().split(" ");
                rtbl.add(new ReglasTabla(rbc.getIndice(),antecedente_final,cons_sin_espacios[0]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObservableList<ReglasTabla> tablaReglas = FXCollections.observableArrayList(rtbl);
        reglas_tbl.setItems(tablaReglas);

    }

    private void mostrarLlaveField(boolean f){
        llave_lbl.setVisible(f);
        llave_tf.setVisible(f);

    }

    private void restablecerValores(){
        llave_tf.setText("");
        ant_tf.setText("");
        cons_tf.setText("");
    }

    private void mostrarAntField(boolean f){
        ant_tf.setVisible(f);
        ant_lbl.setVisible(f);

    }

    private void mostrarConsField(boolean f){
        cons_tf.setVisible(f);
        cons_lbl.setVisible(f);
    }

    public void pantallafuncion(Button btn) throws IOException
    {
        if(btn == home_btn)
        {
            Parent actualizacion = FXMLLoader.load(getClass().getResource("../GUI/MenúPrincipalFXML.fxml"));
            Scene escena = new Scene(actualizacion, 600,600);
            StageP1 = Main.homeS;
            StageP1.setScene(escena);
        }
    }

}
