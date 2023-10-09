/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import static javafx.application.ConditionalFeature.FXML;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;


   
/**
 *
 * @author FADI
 */
public class MainController implements Initializable {
    
 @FXML
    private JFXButton btn_sinscrire;

    @FXML
    private JFXButton btn_seconnecter;

    @FXML
    private VBox VBox;
private Parent fxml;

    @FXML
    void openSignin() {
 TranslateTransition t = new TranslateTransition(Duration.seconds(1),VBox);
        t.setToX(VBox.getLayoutX()*5.5);
        t.play();
         t.setOnFinished(e -> {
            
     try {
         fxml= FXMLLoader.load(getClass().getResource("/Interfaces/Signin.fxml"));
      VBox.getChildren().removeAll();
                 VBox.getChildren().setAll(fxml);
     } catch (IOException ex) {
     }
                
           
       
    });
    }

    @FXML
    void openSignup() {
        TranslateTransition t = new TranslateTransition(Duration.seconds(1),VBox);
        t.setToX(5);
        t.play();
         t.setOnFinished(e -> {
            try {
                fxml= FXMLLoader.load(getClass().getResource("/Interfaces/Signup.fxml"));
                 VBox.getChildren().removeAll();
                 VBox.getChildren().setAll(fxml);
            } catch (IOException ex) {
            }
       
    });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TranslateTransition t = new TranslateTransition(Duration.seconds(1),VBox);
        t.setToX(VBox.getLayoutX()*5.5);
        t.play();
        try {
         fxml= FXMLLoader.load(getClass().getResource("/Interfaces/Signin.fxml"));
      VBox.getChildren().removeAll();
                 VBox.getChildren().setAll(fxml);
     } catch (IOException ex) {
     }
    }

    
}
