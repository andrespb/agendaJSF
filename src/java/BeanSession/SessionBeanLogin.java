/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanSession;

import com.umariana.jee.agenda.clases.Encriptar;
import com.umariana.jee.agenda.dao.DaoTUsuario;
import com.umariana.jee.agenda.pojo.Tusuario;
import com.umariana.jee.agenda.util.HibernateUtil;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Andres
 */
@ManagedBean
@Named(value = "beanSessionLogin")
@SessionScoped
public class SessionBeanLogin implements Serializable{

    /**
     * Creates a new instance of SessionBeanLogin
     */
    private String email;
    private String password;

    private Session session;    
    private Transaction transaction;
    
    public SessionBeanLogin() {
        HttpSession httpSession = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        httpSession.setMaxInactiveInterval(600);
        
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String login(){
        session = null;
        transaction = null;
        try{
            DaoTUsuario daoTusuario = new DaoTUsuario();
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            
            Tusuario user = daoTusuario.getByEmail(session, email);
            if(user != null){
                if(user.getContrasenia().equals(Encriptar.sha512(password))){
                    HttpSession hS = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                    hS.setAttribute("email", this.email);
                    return "/usuario/listadoUsuarios";
                }   
            }
            transaction.commit();
            email = null;
            password = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Usuario y/o Contraseña incorrecto"));
            return "/index";
        }
        catch(Exception e){
            if( transaction != null){
                transaction.rollback();
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Fatal: ", "Por favor contacte al Admin" + e.getMessage())); 
            return null;
        }
        finally{
            if(session != null)
                session.close();
        }
    }
    
    public String cerrarSesion(){
        email = null;
        password = null;
        
        HttpSession hS = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        hS.invalidate();
        return "/index";
    }
}
