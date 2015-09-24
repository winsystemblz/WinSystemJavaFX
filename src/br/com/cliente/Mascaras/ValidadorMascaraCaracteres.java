/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cliente.Mascaras;

import javafx.scene.control.TextField;

/**
 *
 * @author Fabio
 */
public class ValidadorMascaraCaracteres extends TextField{
    
    private int maxlength;
  
    public ValidadorMascaraCaracteres(){
        this.maxlength = 5;
        this.setPromptText("SOMENTE LETRAS");
    }
    public void setMaxlength(int maxlength){
        this.maxlength = maxlength;
    }

    @Override
    public void replaceText(int i, int i1, String string) {
        if(string.matches("[a-zA-Z_@. ]") || string.isEmpty()){
            super.replaceText(i, i1, string); 
        }
    }

    @Override
    public void replaceSelection(String string) {
        super.replaceSelection(string); 
    }
    
    
    
}
