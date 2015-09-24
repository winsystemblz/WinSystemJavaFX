/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cliente.Util;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author Fabio
 */
public class ValidadorCep {
    
    public boolean verificarCep(String cep){
        try {
            String numeros = "0123456789";
            if(!String.valueOf(cep.charAt(5)).equals("-")){
                return false;
            }
            for(int i=0; i < cep.length();i++){
                if(i==5){
                    i++;
                }
                if(!numeros.contains(String.valueOf(cep.charAt(i)))){
                    return false;
                }
            }
            Desktop.getDesktop().browse(new URI("https://www.google.com.br/maps/search/"+cep));
            return true;
        } catch (IOException | URISyntaxException ex) {
            return false;
        }
    }
    
}
