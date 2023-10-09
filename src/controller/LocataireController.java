/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.sql.Date;
//import java.util.Date;
import java.net.URL;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import models.Locataire;
import projet.ConnexionMysql;

/**
 * FXML Controller class
 *
 * @author FADI
 */
public class LocataireController implements Initializable {
    
    Connection cnx;
    public PreparedStatement st;
    public ResultSet result;
     //  public ObservableList<Locataire> data = FXCollections.checkedObservableList();
       public ObservableList<Locataire> data = FXCollections.observableArrayList(); // Corrected ObservableList type


    @FXML
    private JFXTextField txt_searchCIN;
    @FXML
    private JFXTextField txt_nom;
    @FXML
    private TableView<Locataire> table_locataire;
    @FXML
    private TableColumn<Locataire, Integer> col_id;
    @FXML
    private TableColumn<Locataire, String> col_nom;
    @FXML
    private TableColumn<Locataire, String> col_cin;
    @FXML
    private TableColumn<Locataire, Date> col_date;
    @FXML
    private TableColumn<Locataire, String> col_tele;
    @FXML
    private JFXButton btn_add;
    @FXML
    private JFXButton btn_edit;
    @FXML
    private JFXButton btn_delet;
    @FXML
    private JFXTextField txt_CIN;
    @FXML
    private JFXTextField txt_tele;
    @FXML
    private JFXDatePicker datePicker;
    @FXML
    private ImageView ImgImport;
    @FXML
    private Button btn_import;
    

  
     @FXML
    private void SearchLocataire() {
          FilteredList<Locataire> filter = new FilteredList<>(LocataireList, e -> true);

        txt_searchCIN.textProperty().addListener((Observable, oldValue, newValue) -> {

            filter.setPredicate(predicateLocataire -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                /*if (predicateLocataire.getId().toLowerCase().contains(searchKey)) {
                    return true;
                } else*/ if (predicateLocataire.getNomprenom().toLowerCase().contains(searchKey)) {
                    return true;
               // } else if (predicateLocataire.getLastName().toLowerCase().contains(searchKey)) {
                //    return true;
                  } else if (predicateLocataire.getCin().toLowerCase().contains(searchKey)) {
                    return true; 
                     } else if (predicateLocataire.getTele().toLowerCase().contains(searchKey)) {
                    return true;  
              /* } else if (predicateClientData.getEtat().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateClientData.getPhoneNumone().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateClientData.getPhoneNumtwo().toLowerCase().contains(searchKey)) {
                    return true;*/
                } else if (predicateLocataire.getDateNaise().toString().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Locataire> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(table_locataire.comparatorProperty());
        table_locataire.setItems(sortList);  
        
        
    }
       public ObservableList<Locataire> LocataireListData() {
    ObservableList<Locataire> listData = FXCollections.observableArrayList();
    String sql = "SELECT * FROM locataire";

    try {
        st = cnx.prepareStatement(sql);
        result = st.executeQuery();

        while (result.next()) {
            Locataire locataire = new Locataire(
                result.getInt("idL"),
                result.getString("nomprenomL"),
                     result.getDate("datenaissL"),
                
               //result.getString("adresse"), // Adjust this based on your column names
                result.getString("teleL"),
                    result.getString("CIN")// Adjust this based on your column names
                 // Adjust this based on your column names
            );
            listData.add(locataire);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return listData;
}
        private ObservableList<Locataire> LocataireList;
    @FXML
    private void addLocataire() {
       String nom = txt_nom.getText();
    String tele = txt_tele.getText();
    String cin = txt_CIN.getText();
    Alert alert;

    String sql = "insert into locataire(nomprenomL, datenaissL, teleL, CIN) values (?, ?, ?, ?)";
    if (!nom.equals("") && !tele.equals("") && !cin.equals("") && datePicker.getValue() != null) {

        try {
            st = cnx.prepareStatement(sql);
            st.setString(1, nom);

            // Assuming datePicker.getValue() returns a java.util.Date
            java.util.Date utilDate = java.util.Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

            // Convert java.util.Date to java.sql.Date
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            st.setDate(2, sqlDate);
            st.setString(3, tele);
            st.setString(4, cin);
            st.execute();

            // Clear input fields and date picker
            txt_CIN.setText("");
            txt_nom.setText("");
            txt_searchCIN.setText("");
            txt_tele.setText("");
            datePicker.setValue(null);

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Added!");
            alert.showAndWait();
          showLocataire();
        } catch (SQLException ex) {
            ex.printStackTrace(); // Properly handle the exception by logging or displaying an error message.
        }
    } else {
       alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Please fill all blank fields");
        alert.showAndWait();
    }
}

    
    
    
    @FXML
    private void editLocataire() {
        String nom = txt_nom.getText();
    String tele = txt_tele.getText();
    String cin = txt_CIN.getText();
    Alert alert;

    // Construct the SQL statement using a parameter for CIN
    String sql = "UPDATE locataire SET nomprenomL = ?, datenaissL = ?, teleL = ?, CIN = ? WHERE CIN = ?";

    if (!nom.equals("") && !tele.equals("") && !cin.equals("") && datePicker.getValue() != null) {
        try {
            st = cnx.prepareStatement(sql);
            st.setString(1, nom);

            // Assuming datePicker.getValue() returns a java.util.Date
            java.util.Date utilDate = java.util.Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

            // Convert java.util.Date to java.sql.Date
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            st.setDate(2, sqlDate);
            st.setString(3, tele);
            st.setString(4, cin);
            st.setString(5, txt_searchCIN.getText()); // Set CIN as a parameter

            int rowsUpdated = st.executeUpdate();

            if (rowsUpdated > 0) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Modified!");
                 // Refresh the displayed data
                alert.showAndWait();
                showLocataire();
                // Clear input fields and date picker after a successful update
                txt_CIN.setText("");
                txt_nom.setText("");
                txt_searchCIN.setText("");
                txt_tele.setText("");
                datePicker.setValue(null);
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("No records updated. Verify the CIN.");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Properly handle the exception by logging or displaying an error message.
        }
    } else {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Please fill all blank fields");
        alert.showAndWait();
    }
}

    @FXML
    private void deleteLocataire() {
    String sql="delete from locataire where CIN='"+txt_searchCIN.getText()+"'";
        try {
            st = cnx.prepareStatement(sql);
            st.executeUpdate();
             txt_CIN.setText("");
                txt_nom.setText("");
                txt_searchCIN.setText("");
                txt_tele.setText("");
                datePicker.setValue(null);
                Alert alert;
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Modified!");
                 // Refresh the displayed data
                alert.showAndWait();
                showLocataire();
        } catch (SQLException ex) {
        }

    
    
        }

    

    @FXML
    private void importCIN() {
    }
    /**
     * Initializes the controller class.
     */
    
    public void showLocataire(){
      table_locataire.getItems().clear();
        String sql="select * from locataire";
        try {
            st=cnx.prepareStatement(sql);
            result=st.executeQuery();
            while(result.next()) {
                 data.add(new Locataire(result.getInt("idL"), result.getString("nomprenomL"), result.getDate("datenaissL"), result.getString("teleL"), result.getString("CIN")));
                
              //  data.add(new Locataire(result.getInt("id"),result.getString("nomprenomL"),result.getDate("datenaiseL");result.getString("teleL"),result.getString("CIN")));
            //col_cin.setCellValueFactory(new PropertyValueFactory<Locataire,String>("CIN"))
                
               
            }
            
        } catch (SQLException ex) {
        }
        col_cin.setCellValueFactory(new PropertyValueFactory<Locataire, String>("cin"));
        col_date.setCellValueFactory(new PropertyValueFactory<Locataire, Date>("dateNaise"));
        col_id.setCellValueFactory(new PropertyValueFactory<Locataire, Integer>("id"));
        col_nom.setCellValueFactory(new PropertyValueFactory<Locataire, String>("nomprenom"));
        col_tele.setCellValueFactory(new PropertyValueFactory<Locataire, String>("tele"));
        table_locataire.setItems(data);
        
    }
      @FXML
    private void LocataireSelect() {
       
       Locataire locataireL = table_locataire.getSelectionModel().getSelectedItem();
    int num = table_locataire.getSelectionModel().getSelectedIndex();

    if (locataireL == null || num < 0) {
        return;
    }

    txt_nom.setText(locataireL.getNomprenom());
    txt_CIN.setText(locataireL.getCin());
    txt_tele.setText(locataireL.getTele());
    txt_searchCIN.setText(locataireL.getCin());

    Date dateNaise = locataireL.getDateNaise();
    if (dateNaise != null) {
        LocalDate localDate = dateNaise.toLocalDate();
        datePicker.setValue(localDate);
    } else {
        datePicker.setValue(null); // Set DatePicker to null if the date is null
    }

    
    }

    @FXML
    private void chercherL() {
       /* String sql = "SELECT nomprenomL,CIN,darenaissL,teleL From locataire where CIN ='"+txt_searchCIN.getText()+"'";
     
        try {
            st=cnx.prepareStatement(sql);
            result=st.executeQuery();
            if(result.next()) {
                txt_nom.setText(result.getString("nomprenomL"));
        txt_CIN.setText(result.getString("CIN"));
        txt_tele.setText(result.getString("teleL")); 
        Date date= result.getDate("datenaissL");
        datePicker.setValue(date.toLocalDate());
                
            }
            
            
        } catch (SQLException ex) {
        }
           */
       String cinToSearch = txt_searchCIN.getText().trim();
    
    if (cinToSearch.isEmpty()) {
        // Handle empty input, show a message to the user, etc.
        return;
    }

    String sql1 = "SELECT nomprenomL, CIN, datenaissL, teleL FROM locataire WHERE CIN = ?";
    
    try {
        st = cnx.prepareStatement(sql1);
        st.setString(1, cinToSearch); // Use parameterized query to prevent SQL injection
        result = st.executeQuery();
        
        if (result.next()) {
            txt_nom.setText(result.getString("nomprenomL"));
            txt_CIN.setText(result.getString("CIN"));
            txt_tele.setText(result.getString("teleL"));
            Date date = result.getDate("datenaissL");
            
            if (date != null) {
                datePicker.setValue(date.toLocalDate());
            } else {
                datePicker.setValue(null); // Set DatePicker to null if the date is null
            }
        } else {
            // Handle the case where no matching record was found
            // You can clear the fields or show a message to the user
            txt_nom.clear();
            txt_CIN.clear();
            txt_tele.clear();
            datePicker.setValue(null);
        }
    } catch (SQLException ex) {
        // Handle the SQL exception here (e.g., log it, show an error message)
        ex.printStackTrace();
    }
    }

            
       
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            cnx=ConnexionMysql.connexionDB();
            LocataireList= LocataireListData();
           // addLocataire();
            showLocataire();
          
        } catch (SQLException ex) {
        }
       
        // TODO
    }    

  }
