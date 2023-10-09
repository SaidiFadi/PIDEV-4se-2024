/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXTextField;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import projet.ConnexionMysql;

/**
 *
 * @author FADI
 */
public class AccueilController implements Initializable {
    private Connection cnx;
    private PreparedStatement st;
    private ResultSet result;

    @FXML
    private Label lab_nbr;
    @FXML
    private ImageView imageLog;
    @FXML
    private JFXTextField txt_loyer;
    @FXML
    private JFXTextField txt_superfice;
    @FXML
    private JFXTextField txt_region;
    @FXML
    private Button precedant;
    @FXML
    private Button suivant;
    @FXML
    private JFXTextField txt_adr;

   
    @FXML
    private void ShowPrecedant() {
        
          String adr = txt_adr.getText();
          String sql3="select idLogement from logement where adrL ='"+adr+"'"; 
        int position=0;
          try {
            st=cnx.prepareStatement(sql3);
            result=st.executeQuery();
            if(result.next()){
                position=result.getInt("idLogement");
                
                
            }
        } catch (SQLException ex) {
        }
    //    System.out.println(position);
        
        String sql4="select loyer, superfice, nomRegion,adrL,image from logement,region where region.idRegion=logement.region and idLogement not in (select logement from location)and idLogement <'"+position+"'";
        int loyer=0;
      int superfice;
      
      byte byteImg[];
      
              Blob blob;
        try {
            st= cnx.prepareStatement(sql4);
            result=st.executeQuery();
             if(result.next()) {
                loyer=result.getInt("loyer");
                txt_loyer.setText(Integer.toString(loyer));
                superfice=result.getInt("superfice");
                txt_superfice.setText(Integer.toString(superfice));
                txt_region.setText(result.getString("nomRegion"));
                txt_adr.setText(result.getString("adrL"));
                blob=result.getBlob("image");
                byteImg=blob.getBytes(1,(int) blob.length());
              //  Image img= new Image(new ByteArryInputStream(byte Img),imageLog.getFitWidth(),imageLog.getFitHeight(),true,true);
                ByteArrayInputStream inputStream = new ByteArrayInputStream(byteImg);
        Image img = new Image(inputStream);
        imageLog.setImage(img); 
            }
            
            
        } catch (SQLException ex) {
        }
    }

    @FXML
    private void ShowSuivant() {
        String adr = txt_adr.getText();
          String sql3="select idLogement from logement where adrL ='"+adr+"'"; 
        int position=0;
          try {
            st=cnx.prepareStatement(sql3);
            result=st.executeQuery();
            if(result.next()){
                position=result.getInt("idLogement");
                
                
            }
        } catch (SQLException ex) {
        }
       // System.out.println(position);
        
        String sql4="select loyer, superfice, nomRegion,adrL,image from logement,region where region.idRegion=logement.region and idLogement not in (select logement from location)and idLogement >'"+position+"'";
        int loyer=0;
      int superfice;
      
      byte byteImg[];
      
              Blob blob;
        try {
            st= cnx.prepareStatement(sql4);
            result=st.executeQuery();
             if(result.next()) {
                loyer=result.getInt("loyer");
                txt_loyer.setText(Integer.toString(loyer));
                superfice=result.getInt("superfice");
                txt_superfice.setText(Integer.toString(superfice));
                txt_region.setText(result.getString("nomRegion"));
                txt_adr.setText(result.getString("adrL"));
                blob=result.getBlob("image");
                byteImg=blob.getBytes(1,(int) blob.length());
              //  Image img= new Image(new ByteArryInputStream(byte Img),imageLog.getFitWidth(),imageLog.getFitHeight(),true,true);
                ByteArrayInputStream inputStream = new ByteArrayInputStream(byteImg);
        Image img = new Image(inputStream);
        imageLog.setImage(img); 
            }
            
            
        } catch (SQLException ex) {
        }
            
    }
    
    
public void showLogement(){
    String sql="select count(*) from logement where idLogement not in (select logement from location)";
    
    int i=0;   
    try {
            st=cnx.prepareStatement(sql);
            result=st.executeQuery();
            if(result.next()) {
                i=result.getInt(1);
               
            }
            lab_nbr.setText(Integer.toString(i));
        } catch (SQLException ex) {
        }
    String sql2="select loyer, superfice, nomRegion,adrL,image from logement,region where region.idRegion=logement.region and idLogement not in (select logement from location)";
      int loyer=0;
      int superfice;
      
      byte byteImg[];
      
              Blob blob;
    try {
            st=cnx.prepareStatement(sql2);
            result=st.executeQuery();
            if(result.next()) {
                loyer=result.getInt("loyer");
                txt_loyer.setText(Integer.toString(loyer));
                superfice=result.getInt("superfice");
                txt_superfice.setText(Integer.toString(superfice));
                txt_region.setText(result.getString("nomRegion"));
                txt_adr.setText(result.getString("adrL"));
                blob=result.getBlob("image");
                byteImg=blob.getBytes(1,(int) blob.length());
              //  Image img= new Image(new ByteArryInputStream(byte Img),imageLog.getFitWidth(),imageLog.getFitHeight(),true,true);
                ByteArrayInputStream inputStream = new ByteArrayInputStream(byteImg);
        Image img = new Image(inputStream);
        imageLog.setImage(img); 
            }
            
        } catch (SQLException ex) {
        }
    
    
    
}
        
        
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
       
        
        try {
            cnx=ConnexionMysql.connexionDB();
             showLogement();
        } catch (SQLException ex) {
        }
           
        
        
        
       
    }
    
}
