package com.company.controller;

import com.company.indice.archivoMaestro;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ActualizacionController implements Initializable {
    @FXML
    Button home_btn,show_btn,edit_btn,delete_btn,add_btn,go_btn;
    @FXML
    Label llave_lbl, ant_lbl, cons_lbl;
    @FXML
    TextField llave_tf,ant_tf,cons_tf;
    @FXML
    HBox info_hb;

    private archivoMaestro am = new archivoMaestro();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicarComponentes();
        home_btn.setOnAction(handler);
        show_btn.setOnAction(handler);
        edit_btn.setOnAction(handler);
        delete_btn.setOnAction(handler);
        add_btn.setOnAction(handler);
        go_btn.setOnAction(handler);
    }

    EventHandler<ActionEvent>handler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (event.getSource()==home_btn){
                System.out.println("HOME");
            }
            if (event.getSource()==show_btn){
                System.out.println("MOSTRAR");
            }
            if (event.getSource()==edit_btn){
                info_hb.setVisible(true);
                mostrarLlaveField(true);
                mostrarAntField(true);
                mostrarConsField(true);
                go_btn.setVisible(true);

            }
            if (event.getSource()==delete_btn){
                info_hb.setVisible(true);
                mostrarLlaveField(true);
                mostrarConsField(false);
                mostrarAntField(false);
                go_btn.setVisible(true);
            }
            if (event.getSource()==add_btn){
                info_hb.setVisible(true);
                mostrarLlaveField(false);
                mostrarAntField(true);
                mostrarConsField(true);
                go_btn.setVisible(true);
            }
            if  (event.getSource()==go_btn){
                info_hb.setVisible(false);
                inicarComponentes();
            }

        }
    };

    private void inicarComponentes(){
        info_hb.setVisible(false);
        mostrarAntField(false);
        mostrarConsField(false);
        mostrarLlaveField(false);
        go_btn.setVisible(false);
        boolean archivo =am.inicializar();
        if  (!archivo){
            AlertaNoexisteArchivo();
        }
    }

    private void AlertaNoexisteArchivo(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Precaución BC");
        alert.setHeaderText("Base de Conocimientos");
        alert.setContentText("El archivo de la base de conocimientos no existe o no contiene ninguna regla");
        alert.showAndWait();
    }

    private void mostrarLlaveField(boolean f){
        llave_lbl.setVisible(f);
        llave_tf.setVisible(f);
    }

    private void mostrarAntField(boolean f){
        ant_tf.setVisible(f);
        ant_lbl.setVisible(f);
    }

    private void mostrarConsField(boolean f){
        cons_tf.setVisible(f);
        cons_lbl.setVisible(f);
    }
}
