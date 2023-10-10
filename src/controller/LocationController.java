/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
//import java.sql.Date;
import java.util.Date;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import projet.ConnexionMysql;
import java.time.Period;

/**
 *
 * @author FADI
 */
public class LocationController implements Initializable {

     Connection cnx;
    public PreparedStatement st;
    public ResultSet result;
    @FXML
    private JFXTextField txt_searchLogementid;
    @FXML
    private JFXTextField txt_adr;
    @FXML
    private JFXTextField txt_loyer;
    @FXML
    private JFXTextField txt_type;
    @FXML
    private JFXTextField txt_region;
    @FXML
    private JFXTextField txt_cinSearch;
    @FXML
    private JFXTextField txt_nomPrenom;
    @FXML
    private JFXTextField txt_tele;
    @FXML
    private JFXTextField txt_CIN;
    @FXML
    private JFXDatePicker dateDebut;
    @FXML
    private JFXDatePicker dateFin;
    @FXML
    private JFXTextField txt_periode;
    @FXML
    private ImageView imageLog;

  private Boolean isBetween(java.sql.Date my_date, java.sql.Date my_debut,java.sql.Date my_fin ){
      
     return (my_date.equals(my_debut) || my_date.after(my_debut))&& (my_date.equals(my_fin)|| my_date.before(my_fin));
      
  }
  public Boolean isOut(java.sql.Date dateDebut,java.sql.Date dateFin,  java.sql.Date my_debut,java.sql.Date my_fin){
      
    return (dateDebut.before(my_debut) && dateFin.after(my_fin))  ;
      
  }

    @FXML
    private void addLocation() {
       String sql="select idL from locataire where CIN='"+txt_CIN.getText()+"'";
       int locataire=0; 
       try {
             st=cnx.prepareStatement(sql);
             result=st.executeQuery();
             if (result.next()){
                 locataire=result.getInt("idL");
                     
                 
             }
         } catch (SQLException ex) {
         }
        String sql1="select idLogement from logement where adrL='"+txt_adr.getText()+"'";
       int logement=0; 
       try {
             st=cnx.prepareStatement(sql1);
             result=st.executeQuery();
             if (result.next()){
                 logement=result.getInt("idLogement");
                     
                 
             }
         } catch (SQLException ex) {
         }
        
       Date datedd = Date.from (dateDebut.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
       java.sql.Date dateDebut = new java.sql.Date (datedd.getTime());
       Date dateff = Date.from (dateFin.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
       java.sql.Date dateFin = new java.sql.Date (dateff.getTime());
       
       String sql2 ="select dateDebut, dateFin Froù location where logement ='"+logement+"'";
       Boolean debut=false; 
       Boolean fin=false;
       java.sql.Date dated=null;
       java.sql.Date datef=null;
       Date d=null;
       Date f=null;
       
       try {
             st=cnx.prepareStatement(sql2);
             result=st.executeQuery();
             while (result.next()){
                 dated=result.getDate("dateDebut");
                 datef=result.getDate("dateFin");
               if(isBetween(dateFin, dated, datef )==true){
                   fin=true;
               }  
                if(isBetween(dateDebut, dated, datef )==true){
                   debut=true;
               }    
             if(isOut(dateDebut, dateFin, dated, datef)==true){
                fin=true; 
                debut=true;
             }
             }
         } catch (SQLException ex) {
         }
       
       if(fin==true || debut==true){
             Alert alert=new Alert(AlertType.WARNING,"Ce logement est occupé pandant la période entre ="+dated+"et"+datef+"",ButtonType.OK);
          alert.showAndWait();
       } else {
          String sql0=" insert into location (logement,locataire,dateDebut,dateFin) value(?,?,?,?)";
           try {
               st=cnx.prepareStatement(sql0);
               st.setInt(1, logement);
                st.setInt(2, locataire);
                st.setDate(3, dateDebut);
                 st.setDate(4, dateFin);
                 st.executeUpdate();
                 txt_adr.setText("");
                 txt_CIN.setText("");
                 txt_loyer.setText("");
                 txt_nomPrenom.setText("");
                 txt_periode.setText("");
                 txt_region.setText("");
                 txt_tele.setText("");
                 txt_type.setText("");
                 //txt_adr.setText("");
                 this.dateDebut.setValue(null);
                 this.dateFin.setValue(null);
                 imageLog.setImage(null);
                 Alert alert=new Alert(AlertType.CONFIRMATION," logement est ajoutée",ButtonType.OK);
          alert.showAndWait();
           } catch (SQLException ex) {
           }
          
       }
       
       
    }

    @FXML
    private void searchLogement() {
        String sql="Select adrL, loyer, nomType, nomRegion,image from  logement,type,region where logement.type = type.idType AND logement.region = region.idRegion and logement.idLogement='"+txt_searchLogementid.getText()+"'";
         int nb=0;
        try {
             st=cnx.prepareStatement(sql);
             result=st.executeQuery();
             byte ByteImg[];
             Blob blob;
             if(result.next()){
               txt_adr.setText(result.getString("adrL"));
               int loyer=result.getInt("loyer");
                txt_loyer.setText(String.valueOf(loyer));
                txt_type.setText("nomType");
                txt_region.setText(result.getString("nomRegion"));
                 blob =result.getBlob("image");
                 ByteImg=blob.getBytes(1, (int) blob.length());
                 Image img=new Image(new ByteArrayInputStream(ByteImg),imageLog.getFitWidth(),imageLog.getFitHeight(),true,true);
                 imageLog.setImage(img);
                 txt_searchLogementid.setText("");
                 nb=1;
                  System.out.println("Result found"); 
                 System.out.println("SQL Query: " + sql);
             }
         } catch (SQLException ex) {
         ex.printStackTrace();}
         if(nb==0){
          Alert alert=new Alert(AlertType.ERROR,"aucun logement avec identifient="+txt_searchLogementid.getText()+"",ButtonType.OK);
          alert.showAndWait();
      }
    }
     @FXML
    private void periode() {
        
    /*  Date dated = Date.from (dateDebut.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
       java.sql.Date dateDebut = new java.sql.Date (dated.getTime());
        Date datef = Date.from (dateFin.getValue().atStartOfDay (ZoneId.systemDefault()).toInstant());
        java.sql.Date dateFin= new java.sql.Date (datef.getTime());
    int days=daysBetween (dateDebut, dateFin);
    int mois=days/30;
    txt_periode.setText(String.valueOf(days)); */
    if (dateDebut.getValue() != null && dateFin.getValue() != null) {
        Date dated = Date.from(dateDebut.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlDateDebut = new java.sql.Date(dated.getTime());
        
        Date datef = Date.from(dateFin.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlDateFin = new java.sql.Date(datef.getTime());
        
        int days = daysBetween(sqlDateDebut, sqlDateFin);
        int months = days / 30;
        txt_periode.setText(String.valueOf(days));
    }
}
    
    public int daysBetween (java.sql.Date dl, java.sql.Date d2){
        return (int) ((d2.getTime()-dl.getTime())/(1000*60*60*24)); 
       
    }

    @FXML
    private void searchLocataire() {
      String sql="SELECT nomprenomL, teleL, CIN FROM locataire WHERE CIN = '"+txt_cinSearch.getText()+"'";  
      int nbr=0;   
      try {
             st=cnx.prepareStatement(sql);
             result=st.executeQuery();
             if (result.next()){
              txt_CIN.setText(result.getString("CIN"));
              txt_nomPrenom.setText(result.getString("nomprenomL"));
              txt_tele.setText(result.getString("teleL"));
              txt_cinSearch.setText("");
              nbr=1;
                 System.out.println("Result found"); 
                 System.out.println("SQL Query: " + sql);

             }
         } catch (SQLException ex) {
        ex.printStackTrace(); }
      if(nbr==0){
          Alert alert=new Alert(AlertType.ERROR,"aucun locataire avec CIN="+txt_cinSearch.getText()+"",ButtonType.OK);
          alert.showAndWait();
      }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
         try {
             cnx=ConnexionMysql.connexionDB();
         } catch (SQLException ex) {
         }
    }

   
    
}
