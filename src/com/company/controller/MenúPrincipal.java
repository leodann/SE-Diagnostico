package com.company.controller;

import com.company.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MenúPrincipal implements Initializable {

    public static Stage StageP1;
    @FXML
    Button inicio, actu, salir;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        inicio.setOnAction(handler);
        actu.setOnAction(handler);
        salir.setOnAction(handler);
    }

    EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event)
        {
            if(event.getSource() == actu) {
                try {
                    pantallafuncion(actu);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(event.getSource() == inicio){
                try {
                    pantallafuncion(inicio);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    };

    public void pantallafuncion(Button btn) throws IOException
    {
        if(btn == inicio)
        {
            Parent actualizacion = FXMLLoader.load(getClass().getResource("../GUI/TamizajeFXML.fxml"));
            Scene escena = new Scene(actualizacion, 800,800);
            StageP1 = Main.homeS;
            StageP1.setScene(escena);
        }
        else if(btn == actu)
        {
            Parent actualizacion = FXMLLoader.load(getClass().getResource("../GUI/ActualizacionFXML.fxml"));
            Scene escena = new Scene(actualizacion, 800,600);
            StageP1 = Main.homeS;
            StageP1.setScene(escena);
        }
        else
        {

        }
    }
}
