/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author FADI
 */
public class Logement {
    int id;
    String adr;
    int superfice;
    int loyer;
    String type;
    String region;
    
    public Logement(){
        super();
        
    }

    public Logement(int id, String adr, int superfice, int loyer, String type, String region) {
        this.id = id;
        this.adr = adr;
        this.superfice = superfice;
        this.loyer = loyer;
        this.type = type;
        this.region = region;
    }

    public Logement(int id, String adr, int superfice, int loyer, String nomType, String nomRegion, String nomProvince, String nomCommune) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdr() {
        return adr;
    }

    public void setAdr(String adr) {
        this.adr = adr;
    }

    public int getSuperfice() {
        return superfice;
    }

    public void setSuperfice(int superfice) {
        this.superfice = superfice;
    }

    public int getLoyer() {
        return loyer;
    }

    public void setLoyer(int loyer) {
        this.loyer = loyer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
    
    
    
}


