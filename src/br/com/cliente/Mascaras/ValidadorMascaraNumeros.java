/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cliente.Mascaras;

import javafx.scene.control.TextField;
import static javassist.CtMethod.ConstParameter.string;

/**
 *
 * @author Fabio
 */
public class ValidadorMascaraNumeros extends TextField{
    
    public ValidadorMascaraNumeros(){
        this.setPromptText("SOMENTE NUMEROS");
    }

    @Override
    public void replaceText(int i, int i1, String string) {
      if(string.matches("[0-9()]") || string.isEmpty()){
          super.replaceText(i,i1, string);
          
      }
    }

    @Override
    public void replaceSelection(String string) {
        super.replaceSelection(string); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
