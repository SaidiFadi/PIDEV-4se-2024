/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.ConditionalFeature.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import projet.ConnexionMysql;

/**
 *
 * @author FADI
 */

public class SigninController implements Initializable {
    Connection cnx;
    public PreparedStatement st;
    public ResultSet result;
       @FXML
    private JFXTextField txt_userName;

    @FXML
    private JFXTextField txt_password;

    @FXML
    private JFXButton btn_passwordForgoten;

    @FXML
    private JFXButton btn_seconnecter;
        @FXML
    private VBox VBox;
        private Parent fxml;

    @FXML
    void openHome() throws SQLException {
        String nom = txt_userName.getText();
    String pass = txt_password.getText();
    
    // Check if either username or password is empty
    if (nom.isEmpty() || pass.isEmpty()) {
        Alert alert = new Alert(AlertType.ERROR, "Please enter both username and password", ButtonType.OK);
        alert.showAndWait();
        return; // Exit the method
    }
    
    String sql = "SELECT userName, password FROM admin WHERE userName = ? AND password = ?";
    try {
        st = cnx.prepareStatement(sql);
        st.setString(1, nom);
        st.setString(2, pass);
        result = st.executeQuery();
        
        if (result.next()) {
            // User is authenticated; proceed to the home screen
            VBox.getScene().getWindow().hide();
            Stage home = new Stage();
            try {
                fxml = FXMLLoader.load(getClass().getResource("/Interfaces/Home.fxml"));
                Scene scene = new Scene(fxml);
                home.setScene(scene);
                home.show();
            } catch (IOException ex) {
                Logger.getLogger(SigninController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR, "Nom d'utilisateur ou mot de passe incorrect", ButtonType.OK);
            alert.showAndWait();
        }
    } catch (SQLException ex) {
        // Handle any database-related exceptions here
    }
}
       

    @FXML
    void sendPassword() {

    }
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            cnx=ConnexionMysql.connexionDB();
        } catch (SQLException ex) {
        }
    }
    
}
