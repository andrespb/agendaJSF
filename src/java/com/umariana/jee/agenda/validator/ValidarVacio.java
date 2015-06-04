/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umariana.jee.agenda.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Andres
 */
@FacesValidator("validadorVacio")
public class ValidarVacio implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        String label;
        //Recupera el componente donde se va a validar
        HtmlInputText htmlInputText = (HtmlInputText) component;
        // Verifica si es nulo o vacio
        if(htmlInputText.getLabel() == null || htmlInputText.getLabel().trim().equals("")){
            label=htmlInputText.getId();
        }
        else{
            // Si no es nulo, lo asigna a la variable label
            label = htmlInputText.getLabel();
        }
        // Convierte a String, borra los espacios vacios
        if(value.toString().trim().equals("")){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", label + ": el campo es obligatorio!"));
        }
            
    }
    
    
}
