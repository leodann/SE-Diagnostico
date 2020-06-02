package com.company;

import com.company.indice.archivoMaestro;
import com.company.menu.Recuperar_Factores_Riesgo;
import com.company.motroInferncia.MotoroInferencia;
import com.company.motroInferncia.Reglas;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {
    public static Stage homeS;
    @Override
    public void start(Stage primaryStage) throws Exception {
        homeS = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("GUI/MenúPrincipalFXML.fxml"));
        primaryStage.setMaximized(false);
        primaryStage.setResizable(false);
        primaryStage.setTitle("SE diagnostico de Diabetes");
        primaryStage.setScene(new Scene(root,600,600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


