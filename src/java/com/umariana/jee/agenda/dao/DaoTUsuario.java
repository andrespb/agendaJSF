/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umariana.jee.agenda.dao;

import com.umariana.jee.agenda.pojo.Tusuario;
import com.umariana.jee.agenda.util.HibernateUtil;
import java.util.List;
import javax.validation.ConstraintViolationException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Andres
 */
public class DaoTUsuario implements com.umariana.jee.agenda.interfaces.InterfaceTUsuario{
    
    //private Session session; 

    @Override
    public boolean registrar(Tusuario tUsuario, Session session) throws Exception, ConstraintViolationException {
        //session = HibernateUtil.getSessionFactory().openSession();
        //Transaction trx = session.beginTransaction();
        session.save(tUsuario);
        //trx.commit();
        //session.close();
        
        return true;
    }

    @Override
    public List<Tusuario> getAll(Session session) throws Exception {
        String hql="from Tusuario";
        Query query=session.createQuery(hql);
        List<Tusuario> listaTUsuario = (List<Tusuario>) query.list();
        
        return listaTUsuario;
    }

    @Override
    public Tusuario getByCodigoUsuario(String codigoUsuario, Session session) throws Exception {
        return (Tusuario) session.get(Tusuario.class, codigoUsuario);
    }
    
    @Override
    public Tusuario getByEmail(Session session, String correoElectronico) throws Exception {
        // Apunta hacia el POJO, no hacia la tabla en la BD
        String hql = "from Tusuario where correoElectronico=:correoElectronico";
        Query query = session.createQuery( hql );
        query.setParameter("correoElectronico", correoElectronico);
        
        Tusuario tUsuario = (Tusuario) query.uniqueResult();
        
        return tUsuario;
    }
    
    @Override
    public boolean update(Tusuario tUsuario, Session session) throws Exception {
        session.update(tUsuario);
        return true;
    }

    @Override
    public Tusuario getByCorreoElectronicoDiferent(Session session, String codigoUsuario, String correoElectronico) throws Exception {
        String hql="from Tusuario where codigoUsuario!=:codigoUsuario and correoElectronico=:correoElectronico";
        Query query=session.createQuery(hql);
        query.setParameter("codigoUsuario", codigoUsuario);
        query.setParameter("correoElectronico", correoElectronico);
        
        Tusuario tUsuario=(Tusuario) query.uniqueResult();
        
        return tUsuario;
    }

    
}
