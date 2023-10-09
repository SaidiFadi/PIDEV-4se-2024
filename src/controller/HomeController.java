/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.ConditionalFeature.FXML;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author FADI
 */
public class HomeController implements Initializable {
      private Parent fxml;
      @FXML
    private JFXButton btn_accueil;

    @FXML
    private JFXButton btn_logement;

    @FXML
    private JFXButton btn_locataire;

    @FXML
    private JFXButton btn_location;

    @FXML
    private JFXButton btn_contrat;

    @FXML
    private JFXButton btn_facture;

    @FXML
    private JFXButton btn_historiques;
    
      @FXML
    private AnchorPane root;
    @FXML
    private Label username;
      
     

    @FXML
    void accueil() {
         try {
            fxml= FXMLLoader.load(getClass().getResource("/Interfaces/Accueil.fxml"));
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        } catch (IOException ex) {
        }


    }

    @FXML
    void contrat() {
 try {
            fxml= FXMLLoader.load(getClass().getResource("/Interfaces/Contrat.fxml"));
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        } catch (IOException ex) {
        }
    }

    @FXML
    void facture() {
 try {
            fxml= FXMLLoader.load(getClass().getResource("/Interfaces/Facture.fxml"));
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        } catch (IOException ex) {
        }
    }

    @FXML
    void historiques() {
 try {
            fxml= FXMLLoader.load(getClass().getResource("/Interfaces/Historique.fxml"));
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        } catch (IOException ex) {
        }
    }

    @FXML
    void locataire() {
         try {
            fxml= FXMLLoader.load(getClass().getResource("/Interfaces/Locataire.fxml"));
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        } catch (IOException ex) {
        }

    }

    @FXML
    void location() {
 try {
            fxml= FXMLLoader.load(getClass().getResource("/Interfaces/Location.fxml"));
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        } catch (IOException ex) {
        }
    }

    @FXML
    void logement() {
 try {
            fxml= FXMLLoader.load(getClass().getResource("/Interfaces/Logement.fxml"));
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        } catch (IOException ex) {
        }
    }
 /*public void displayUsername(){
        
                username.setText(getData.username);

    }*/
   
   


    @Override
    public void initialize(URL location, ResourceBundle resources) {
         try {
            fxml= FXMLLoader.load(getClass().getResource("/Interfaces/Accueil.fxml"));
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        } catch (IOException ex) {
        }

    }
    
}
