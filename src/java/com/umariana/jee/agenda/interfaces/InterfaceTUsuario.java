/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umariana.jee.agenda.interfaces;

import com.umariana.jee.agenda.pojo.Tusuario;
import java.util.List;
import javax.validation.ConstraintViolationException;
import org.hibernate.Session;

/**
 *
 * @author Andres
 */
public interface InterfaceTUsuario {
    public boolean registrar(Tusuario tUsuario, Session session)throws Exception, ConstraintViolationException ;
    public List<Tusuario>getAll(Session session) throws Exception;
    public Tusuario getByCodigoUsuario(String codigoUsuario, Session session)throws Exception;
    public Tusuario getByEmail(Session session, String correoElectronico) throws Exception;
    public Tusuario getByCorreoElectronicoDiferent(Session session, String codigoUsuario ,String correoElectronico)throws Exception;
    public boolean update(Tusuario tUsuario, Session session)throws Exception;
    
}
