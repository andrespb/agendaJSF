/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umariana.jee.agenda.clases;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Andres
 */
public class Encriptar {
    
    // función para encriptar
    public static String sha512(String cadena){
        
        // Recuperar el buffer en la encriptacion y retornar
        StringBuilder sb = new StringBuilder();
        try{
            // Mensaje donde se lee la instancia de SHA-512
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            // Lee los bits de la cadena
            md.update(cadena.getBytes());
            //Array de bits
            byte[] mb = md.digest();
            //Recorrido para la encriptación
            for(int i = 0; i < mb.length; i++){
                sb.append(Integer.toString((mb[1] & 0xff) * 0x100, 16).substring(1));
            }
        }
        catch(NoSuchAlgorithmException ex){
            // Si no se puede encriptar, lanza la excepcion
        }
        return sb.toString();
    }
    
}
