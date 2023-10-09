/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
//import static com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolver.length;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import models.Logement;
import projet.ConnexionMysql;

/**
 *
 * @author FADI
 */
public class LogementController implements Initializable {
    Connection cnx;
    public PreparedStatement st;
    public ResultSet result;

    @FXML
    private JFXTextField txt_searchid;
    @FXML
    private JFXTextField txt_adr;
    @FXML
    private JFXTextField txt_superfice;
    @FXML
    private JFXComboBox<String> cb_region;
    @FXML
    private JFXComboBox<String> cb_province;
    @FXML
    private JFXComboBox<String> cb_commune;
    @FXML
    private JFXComboBox<String> cb_type;
    @FXML
    private JFXTextField txt_loyer;
    @FXML
    private Label lab_url;
    @FXML
    private TableView<Logement> table_logement;
    @FXML
    private TableColumn<Logement,Integer> col_id;
    @FXML
    private TableColumn<Logement, String> col_adr;
    @FXML
    private TableColumn<Logement,Integer> col_superfice;
    @FXML
    private TableColumn<Logement, Integer> col_loyer;
    @FXML
    private TableColumn<Logement, String> col_type;
    @FXML
    private TableColumn<Logement, String> col_region;
    @FXML
    private ImageView image_logement;
    @FXML
    private JFXButton btn_add;
    @FXML
    private JFXButton btn_edit;
    @FXML
    private JFXButton btn_delete;
    @FXML
    private JFXButton icon_importer;

   private FileInputStream fs;

    @FXML
    private void remplirProvince() {
        String sql="select nomProvince from province where region = (select idRegion from region where nomRegion ='"+cb_region.getValue()+"')";
    List<String> provinces = new ArrayList<String>();
        try {
            st=cnx.prepareStatement(sql);
            result=st.executeQuery();
            while(result.next()){
                provinces.add(result.getString("nomProvince"));
                
            }
           
        } catch (SQLException ex) {
        }
        cb_province.setItems(FXCollections.observableArrayList(provinces));
    }

    @FXML
    private void remplirCommune() {
        String sql="select nomCommune from commune where province = (select idProvince from province where nomProvince ='"+cb_province.getValue()+"')";
    List<String> communes = new ArrayList<String>();
        try {
            st=cnx.prepareStatement(sql);
            result=st.executeQuery();
            while(result.next()){
                communes.add(result.getString("nomCommune"));
                
            }
           
        } catch (SQLException ex) {
        }
        cb_commune.setItems(FXCollections.observableArrayList(communes));
    }

    @FXML
    private void ajouterLogement() {
       String adr = txt_adr.getText();
String superf=txt_superfice.getText(); 
int superfice =Integer.parseInt(superf);
String loy=txt_loyer.getText();
int loyer=Integer.parseInt(loy);
String typ=cb_type.getValue() ;
String sqll="select idType from type where nomType='"+typ+"'";
int type=0;
try {
st=cnx.prepareStatement (sqll);
result=st.executeQuery();
if (result.next()) {
    type=result.getInt("idType");
}

} catch (SQLException e) {
e.printStackTrace();
}
String reg=cb_region.getValue() ;
String sql2="select idRegion from region where nomRegion='"+reg+"'";
int region=0;
try {
st=cnx.prepareStatement (sql2);
result=st.executeQuery();
if (result.next()) {
    region=result.getInt("idRegion");
}

} catch (SQLException e) {
e.printStackTrace();
}
String prov=cb_province.getValue() ;
String sql3="select idProvince from province where nomProvince='"+prov+"'";
int province=0;
try {
st=cnx.prepareStatement (sql3);
result=st.executeQuery();
if (result.next()) {
    province=result.getInt("idProvince");
}

} catch (SQLException e) {
e.printStackTrace();
}
String com=cb_commune.getValue() ;
String sql4="select idCommune from commune where nomCommune='"+com+"'";
int commune=0;
try {
st=cnx.prepareStatement (sql4);
result=st.executeQuery();
if (result.next()) {
    commune=result.getInt("idCommune");
}

} catch (SQLException e) {
e.printStackTrace();
}
File image=new File(lab_url.getText());
String sql = "INSERT INTO logement (adrL, superfice, loyer, type, region, province, commune, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            st=cnx.prepareStatement(sql);
             st.setString(1, adr);
            st.setInt(2, superfice);
            st.setInt(3, loyer);
            st.setInt(4, type);
            st.setInt(5, region);
            st.setInt(6, province);
            st.setInt(7, commune);
            fs= new FileInputStream(image);
            st.setBinaryStream(8, fs, image.length());
            st.executeUpdate();
            showLogement();
            lab_url.setText("aucune selectionée");
            txt_adr.setText("");
               txt_superfice.setText("");
            txt_loyer.setText("");
            txt_searchid.setText("");
            cb_commune.setValue("commune");
              cb_province.setValue("province");
                cb_type.setValue("type");
                  cb_region.setValue("region");
                  image_logement.setImage(null);
                   Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Le logement a été ajouté avec succès.");
            alert.showAndWait();
            

            
            } catch (SQLException ex) {
        } catch (FileNotFoundException ex) {
        }

            
            
            /*   // Récupérez les valeurs des champs de saisie et de la ComboBox
            String adresse = txt_adr.getText();
            String superficieStr = txt_superfice.getText();
            String loyerStr = txt_loyer.getText();
            String type = cb_type.getValue();
            String region = cb_region.getValue();
            String province = cb_province.getValue();
            String commune = cb_commune.getValue();
            String imagePath = lab_url.getText();
            
            // Check if any of the required fields are empty
            if (adresse.isEmpty() || superficieStr.isEmpty() || loyerStr.isEmpty() || type == null || region == null || province == null || commune == null || imagePath.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
            }
            
            int superficie;
            int loyer;
            
            try {
            superficie = Integer.parseInt(superficieStr);
            loyer = Integer.parseInt(loyerStr);
            } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer des valeurs numériques valides pour la superficie et le loyer.");
            alert.showAndWait();
            return;
            }
            
            // Insert the data into your database using an SQL INSERT statement
            String sql = "INSERT INTO logement (adrL, superficie, loyer, type, region, province, commune, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try {
            PreparedStatement statement = cnx.prepareStatement(sql);
            statement.setString(1, adresse);
            statement.setInt(2, superficie);
            statement.setInt(3, loyer);
            statement.setString(4, type);
            statement.setString(5, region);
            statement.setString(6, province);
            statement.setString(7, commune);
            
            
            // You should also save the image in a suitable format in the database.
            // You can use a BLOB type for that.
            // FileInputStream imageStream = new FileInputStream(imagePath);
            // statement.setBinaryStream(8, imageStream, imageStream.available());
            
            // Execute the SQL statement to insert the data
            statement.executeUpdate();
            
            // Display a success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Le logement a été ajouté avec succès.");
            alert.showAndWait();
            
            // Clear input fields and ComboBox after adding
            txt_adr.clear();
            txt_superfice.clear();
            txt_loyer.clear();
            cb_type.setValue(null);
            cb_region.setValue(null);
            cb_province.setValue(null);
            cb_commune.setValue(null);
            lab_url.setText(""); // Clear the image path
            image_logement.setImage(null); // Clear the displayed image
            } catch (SQLException e) {
            // Handle any SQL exceptions here, such as showing an error message
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur SQL");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur SQL s'est produite lors de l'ajout du logement.");
            alert.showAndWait();
        }*/ 
    }

    @FXML
    private void modifierLogement() {
    }

    @FXML
    private void supprimerLogement() {
    }

    @FXML
    private void importerImage() {
        FileChooser fc=new FileChooser();
        fc.getExtensionFilters().add(new ExtensionFilter("Image files","*.png","*jpg"));
        File f=fc.showOpenDialog(null);
        if(f!=null){
            lab_url.setText(f.getAbsolutePath());
            Image image= new Image(f.toURI().toString(),image_logement.getFitWidth(),image_logement.getFitHeight(),true,true);
            image_logement.setImage(image);
            
        }
        
        
        
    }

    @FXML
    private void searchLogement() {
    }
@FXML
   /* private void tableLogEvent() {
   Logement logement = table_logement.getSelectionModel().getSelectedItem();
    String sql2 = "SELECT idLogement, adrL, superfice, loyer, nomType, nomRegion, nomProvince, nomCommune, image FROM logement, type, province, commune, region WHERE logement.type = type.idType AND logement.region = region.idRegion AND logement.commune = commune.idCommune AND logement.province = province.idProvince AND idLogement = ?";
    
    try {
        st = cnx.prepareStatement(sql2);
        st.setInt(1, logement.getId());
        result = st.executeQuery();
        
        while (result.next()) {
            int id = result.getInt("idLogement");
            txt_searchid.setText(String.valueOf(id));
            
            // Set adrL from the database
            txt_adr.setText(result.getString("adrL"));
            
            int sur = result.getInt("superfice");
            txt_superfice.setText(String.valueOf(sur));
            
            int loyer = result.getInt("loyer");
            txt_loyer.setText(String.valueOf(loyer));
            
            cb_type.setValue(result.getString("nomType"));
            cb_region.setValue(result.getString("nomRegion"));
            cb_province.setValue(result.getString("nomProvince"));
            cb_commune.setValue(result.getString("nomCommune"));
            
            Blob blob = result.getBlob("image");
            byte[] byteImage = blob.getBytes(1, (int) blob.length());
            Image img = new Image(new ByteArrayInputStream(byteImage), image_logement.getFitWidth(), image_logement.getFitHeight(), true, true);
            image_logement.setImage(img);
        }
    } catch (SQLException ex) {
        // Handle the exception appropriately, e.g., display an error message or log it
        ex.printStackTrace();
    }
}*/
    
        private void tableLogEvent() {
           Logement logement = table_logement.getSelectionModel().getSelectedItem();
    if (logement == null) {
        return; // No selection in the table, nothing to do
    }

    try {
        // Populate ComboBox for provinces
        String sql = "SELECT nomProvince FROM province";
        List<String> provinces = new ArrayList<>();
        try (PreparedStatement stProvince = cnx.prepareStatement(sql);
             ResultSet resultProvince = stProvince.executeQuery()) {
            while (resultProvince.next()) {
                provinces.add(resultProvince.getString("nomProvince"));
            }
        }
        cb_province.setItems(FXCollections.observableArrayList(provinces));

        // Populate ComboBox for communes
        String sql1 = "SELECT nomCommune FROM commune";
        List<String> communes = new ArrayList<>();
        try (PreparedStatement stCommune = cnx.prepareStatement(sql1);
             ResultSet resultCommune = stCommune.executeQuery()) {
            while (resultCommune.next()) {
                communes.add(resultCommune.getString("nomCommune"));
            }
        }
        cb_commune.setItems(FXCollections.observableArrayList(communes));

        // Retrieve data based on the selected Logement
        String sql2 = "SELECT idLogement, adrL, superfice, loyer, nomType, nomRegion, nomProvince, nomCommune, image FROM logement, type, province, commune, region " +
                "WHERE logement.type = type.idType AND logement.region = region.idRegion AND logement.commune = commune.idCommune AND logement.province = province.idProvince AND idLogement = ?";
        try (PreparedStatement stLogement = cnx.prepareStatement(sql2)) {
            stLogement.setInt(1, logement.getId());
            try (ResultSet resultLogement = stLogement.executeQuery()) {
                while (resultLogement.next()) {
                    int id = resultLogement.getInt("idLogement");
                    txt_searchid.setText(String.valueOf(id));
                    txt_adr.setText(resultLogement.getString("adrL"));
                    int sur = resultLogement.getInt("superfice");
                    txt_superfice.setText(String.valueOf(sur));
                    int loyer = resultLogement.getInt("loyer");
                    txt_loyer.setText(String.valueOf(loyer));
                    cb_type.setValue(resultLogement.getString("nomType"));
                    cb_region.setValue(resultLogement.getString("nomRegion"));
                    cb_province.setValue(resultLogement.getString("nomProvince"));
                    cb_commune.setValue(resultLogement.getString("nomCommune"));

                    // Retrieve and display the image
                    Blob blob = resultLogement.getBlob("image");
                    byte[] byteImage = blob.getBytes(1, (int) blob.length());
                    Image img = new Image(new ByteArrayInputStream(byteImage), image_logement.getFitWidth(), image_logement.getFitHeight(), true, true);
                    image_logement.setImage(img);
                }
            }
        }
    } catch (SQLException ex) {
        // Handle SQL exceptions
        ex.printStackTrace();
    }
}
   
    
    ObservableList<Logement> listLog = FXCollections.observableArrayList();
    public void showLogement(){
        table_logement.getItems().clear();
        String sql=" select idLogement, adrL, superfice, loyer, nomType, nomRegion from logement,type,region where logement.region=region.idRegion and logement.type=type.idType";
        try {
            st = cnx.prepareStatement(sql);
            result=st.executeQuery();
            while (result.next()){
                listLog.add(new Logement(result.getInt(1),result.getString(2),result.getInt(3),result.getInt(4),result.getString(5),result.getString(6)));
                
                
                
            }
        } catch (SQLException ex) {
        }
        col_id.setCellValueFactory(new PropertyValueFactory<Logement,Integer>("id"));
        col_adr.setCellValueFactory(new PropertyValueFactory<Logement,String>("adr"));
        col_loyer.setCellValueFactory(new PropertyValueFactory<Logement,Integer>("loyer"));
        col_region.setCellValueFactory(new PropertyValueFactory<Logement,String>("region"));
        col_superfice.setCellValueFactory(new PropertyValueFactory<Logement,Integer>("superfice"));
        col_type.setCellValueFactory(new PropertyValueFactory<Logement,String>("type"));
        table_logement.setItems(listLog);

    }
    
    public void remplirType(){
        String sql="select nomType from type";
        List<String> types= new ArrayList<String>();
        try {
            st = cnx.prepareStatement(sql);
            result=st.executeQuery();
            while(result.next()){
                types.add(result.getString("nomType"));
                
            }
        } catch (SQLException ex) {
        }
        cb_type.setItems(FXCollections.observableArrayList(types));
    }
    
       public void remplirRegion(){
        String sql="select nomRegion from region";
        List<String> types= new ArrayList<String>();
        try {
            st = cnx.prepareStatement(sql);
            result=st.executeQuery();
            while(result.next()){
                types.add(result.getString("nomRegion"));
                
            }
        } catch (SQLException ex) {
        }
        cb_region.setItems(FXCollections.observableArrayList(types));
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            cnx=ConnexionMysql.connexionDB();
            showLogement();
            remplirType();
            remplirRegion();
        } catch (SQLException ex) {
        }
    }

    
    
}
