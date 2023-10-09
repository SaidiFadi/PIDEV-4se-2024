/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Date;

/**
 *
 * @author FADI
 */
public class Locataire {
    int id;
    String nomprenom;
    Date dateNaise;
    String tele;
    String cin ;
   
    
    public Locataire(){
        super();
        
    }
    
    public Locataire(int id, String nom, Date date, String tele, String cin){
        this.id=id;
        this.nomprenom=nom;
        this.dateNaise=date;
        this.tele=tele;
        this.cin=cin;
        
        
    }
    

    public Locataire(int aInt, String string, String string0, String string1, String string2, Date date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomprenom() {
        return nomprenom;
    }

    public void setNomprenom(String nomprenom) {
        this.nomprenom = nomprenom;
    }

    public Date getDateNaise() {
        return dateNaise;
    }

    public void setDateNaise(Date dateNaise) {
        this.dateNaise = dateNaise;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }
    
}
