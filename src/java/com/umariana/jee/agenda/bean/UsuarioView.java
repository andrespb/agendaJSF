/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umariana.jee.agenda.bean;

import com.umariana.jee.agenda.clases.Encriptar;
import com.umariana.jee.agenda.dao.DaoTUsuario;
import com.umariana.jee.agenda.pojo.Tusuario;
import com.umariana.jee.agenda.util.HibernateUtil;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Andres
 */
@ManagedBean
@ViewScoped
public class UsuarioView {

    private Tusuario usuario;
    private List<Tusuario> listaUsuario;
    private List<Tusuario> listaUsuarioFiltrado;
    private String PasswordRepita;
    private Session session; 
    private Transaction transaction;
    
    /**
     * Creates a new instance of MbRUsuario
     */
    public UsuarioView() 
    {
        this.usuario=new Tusuario();
        this.usuario.setCodigoUsuario("");
        this.usuario.setSexo(true);
    }
    
    

    /**
     * Metodos 
     */
    public void registrar() throws Exception{
        /*this.session = null;
        this.transaction = null;
        try{
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            if(!this.usuario.getContrasenia().equals(PasswordRepita)){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Las contraseñas no coinciden"));
                return ;
            }
            // Se crea el dao para validar si existe o no
            DaoTUsuario daoTusuario = new DaoTUsuario();
            if( daoTusuario.getByEmail(session, this.usuario.getCorreoElectronico()) != null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "El usuario ya se encuentra registrado!"));
                return;
            }

            this.usuario.setContrasenia(Encriptar.sha512(this.usuario.getContrasenia()));
            DaoTUsuario daoUsuario = new DaoTUsuario();
            daoUsuario.registrar(this.usuario, this.session);
            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto: ", "El usuario ha sido registrado"));
            
            //Limpiar el form despues de registrar el usuario
            //RequestContext.getCurrentInstance().execute("limpiarForm('formRegistrarUser')");
            this.usuario = new Tusuario();
            this.usuario.setCodigoUsuario("");
            this.usuario.setSexo(true);
        }*/
        this.session=null;
        this.transaction=null;
        try
        {            
            if(!this.usuario.getContrasenia().equals(this.PasswordRepita)){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Las contraseñas no coencide"));
                return;
            }
            DaoTUsuario daoTUsuario=new DaoTUsuario();
            this.session=HibernateUtil.getSessionFactory().openSession();
            this.transaction=session.beginTransaction();
            if(daoTUsuario.getByEmail(this.session, this.usuario.getCorreoElectronico())!=null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El usuario ya se encuentra registrado en el sistema"));
                return;
            }
            this.usuario.setContrasenia(Encriptar.sha512(this.usuario.getContrasenia()));
            daoTUsuario.registrar( this.usuario, this.session);
            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto:", "El registro fue realizado correctamente"));
            this.usuario=new Tusuario();
            this.usuario.setCodigoUsuario("");
            this.usuario.setSexo(true);
        }
        catch( Exception e){
            if( this.transaction != null){
                this.transaction.rollback();
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Fatal: ", "Por favor contacte al Admin" + e.getMessage()));
            //System.out.println(e.getMessage());
        }
        finally{
            if(this.session != null)
                this.session.close();
        }   
    }
    
    public List<Tusuario> getAll(){
        session = null;
        transaction = null;
        
        try{
            DaoTUsuario daoTusuario = new DaoTUsuario();
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            
            listaUsuario = daoTusuario.getAll(session);
            transaction.commit();
            
            return listaUsuario;
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
    
    public Tusuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Tusuario usuario) {
        this.usuario = usuario;
    }

    public List<Tusuario> getListaUsuario() {
        return listaUsuario;
    }

    public void setListaUsuario(List<Tusuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    public String getPasswordRepita() {
        return PasswordRepita;
    }

    public void setPasswordRepita(String PasswordRepita) {
        this.PasswordRepita = PasswordRepita;
    }

    public List<Tusuario> getListaUsuarioFiltrado() {
        return listaUsuarioFiltrado;
    }

    public void setListaUsuarioFiltrado(List<Tusuario> listaUsuarioFiltrado) {
        this.listaUsuarioFiltrado = listaUsuarioFiltrado;
    }
    
    /**
     * Actualiza la informacion del usuario
     */
    public void update(){
        session = null;
        transaction = null;
        try{
            DaoTUsuario usuarioDao = new DaoTUsuario();
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            if(usuarioDao.getByCorreoElectronicoDiferent(this.session, this.usuario.getCodigoUsuario(), this.usuario.getCorreoElectronico())!=null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Correo electrónico ocupado"));
                return;
            }
            this.usuario.setContrasenia(Encriptar.sha512(this.usuario.getContrasenia()));
            usuarioDao.update(this.usuario, this.session);
            this.transaction.commit();
            //mbSLogin.setCorreoElectronico(this.usuario.getCorreoElectronico());
            HttpSession httpSession=(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            httpSession.setAttribute("correoElectronico", this.usuario.getCorreoElectronico());
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto:", "Los cambios fueron guardados correctamente"));
        }
        catch(Exception e){
            e.printStackTrace();
            if(transaction != null){
                transaction.rollback();
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal:", "Por favor contacte con su administrador " + e.getMessage()));
        }
        finally{
            if(session != null)
                session.close();
        }
    }
    
    /**
     * Carga un usuario para editarlo
     * @param codigoUsuario - El codigo del usuario que se va a editar
     */
    public void getUsuarioEditar(String codigoUsuario){
        session = null;
        transaction = null;
        try{
            DaoTUsuario usuarioDao = new DaoTUsuario();
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            
            usuario = usuarioDao.getByCodigoUsuario(codigoUsuario, session);
            RequestContext.getCurrentInstance().update("formEditarUsuario:panelEditarUsuario");
            RequestContext.getCurrentInstance().execute("PF('dialogoEditarUsuario').show()");
            
            transaction.commit();
        }
        catch(Exception e){
            if(transaction!=null)
            {
                transaction.rollback();
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal:", "Por favor contacte con su administrador "+e.getMessage()));
        }
        finally{
            if(session != null)
                session.close();
        }
    }
}
