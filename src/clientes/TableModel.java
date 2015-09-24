/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientes;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Fabio
 */
public class TableModel{
    
    IntegerProperty id;
    StringProperty nome;
    StringProperty email;
    StringProperty fone;
    StringProperty site;
    StringProperty cep;
    

    public TableModel() {
    }

    
    public TableModel(int id, String nome, String email, String fone, String site,String cep) {
        
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.email = new SimpleStringProperty(email);
        this.fone = new SimpleStringProperty(fone);
        this.site = new SimpleStringProperty(site);
        this.cep  = new SimpleStringProperty(cep);
        
    }

    public IntegerProperty idProperty() {
        return id;
    }
    public StringProperty nomeProperty() {
        return nome;
    }
    public StringProperty emailProperty() {
        return email;
    }
    public StringProperty foneProperty() {
        return fone;
    }
    public StringProperty siteProperty() {
        return site;
    }
    public StringProperty cepProperty() {
        return cep;
    }
   
    public IntegerProperty getId() {
        return id;
    }
    public void setId(IntegerProperty id) {
        this.id = id;
    }
    
    public StringProperty getNome() {
        return nome;
    }
    public void setNome(StringProperty nome) {
        this.nome = nome;
    }
    
    public StringProperty getEmail() {
        return email;
    }
    public void setEmail(StringProperty email) {
        this.email = email;
    }
    
    public StringProperty getFone() {
        return fone;
    }
    public void setFone(StringProperty fone) {
        this.fone = fone;
    }
    
    public StringProperty getSite() {
        return site;
    }
    public void setSite(StringProperty site) {
        this.site = site;
    }
      
    public StringProperty getCep() {
        return cep;
    }
    public void setCep(StringProperty cep) {
        this.cep = cep;
    }
      
       
}  
