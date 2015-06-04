/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umariana.jee.agenda.bean;

import com.umariana.jee.agenda.clases.Encriptar;
import com.umariana.jee.agenda.dao.DaoTUsuario;
import com.umariana.jee.agenda.dao.DaoUsuarioAmigo;
import com.umariana.jee.agenda.pojo.Tusuario;
import com.umariana.jee.agenda.pojo.Tusuarioamigo;
import com.umariana.jee.agenda.util.HibernateUtil;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.mindmap.DefaultMindmapNode;
import org.primefaces.model.mindmap.MindmapNode;

/**
 *
 * @author INTERNET
 */
@ManagedBean
@ViewScoped
public class UsuarioAmigoView {

    /**
     * Creates a new instance of MbRUsuarioAmigo
     */
    private Tusuarioamigo usuarioamigo;
    private List<Tusuarioamigo> listaUsuarioAmigo;
    private Tusuario usuario;
    
    private String txtContraseniaRepita;
    
    private MindmapNode nodoUsuario;
    
    private Session session;
    private Transaction transaccion;
    
    public UsuarioAmigoView() {
        this.usuarioamigo=new Tusuarioamigo();
        this.usuarioamigo.setCodigoUsuarioAmigo("");
        this.usuarioamigo.setSexo(true);
    }
    
    public void register()
    {
        this.session=null;
        this.transaccion=null;
        
        try
        {            
            if(!this.usuarioamigo.getContrasenia().equals(this.txtContraseniaRepita))
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Las contraseñas no coencide"));

                return;
            }

            DaoUsuarioAmigo daoTUsuarioAmigo=new DaoUsuarioAmigo();
            DaoTUsuario daoTUsuario=new DaoTUsuario();
            
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaccion=session.beginTransaction();

            if(daoTUsuarioAmigo.getByCorreoElectronico(this.session, this.usuarioamigo.getCorreoElectronico())!=null)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El correo electrónico ya se encuentra registrado en el sistema"));

                return;
            }

            this.usuarioamigo.setContrasenia(Encriptar.sha512(this.usuarioamigo.getContrasenia()));
            
            HttpSession sessionUsuario=(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            
            this.usuarioamigo.setTusuario(daoTUsuario.getByEmail(this.session, sessionUsuario.getAttribute("correoElectronico").toString()));
            
            daoTUsuarioAmigo.register(this.session, this.usuarioamigo);
            
            this.transaccion.commit();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto:", "El registro fue realizado correctamente"));

            this.usuarioamigo=new Tusuarioamigo();
            this.usuarioamigo.setCodigoUsuarioAmigo("");
            this.usuarioamigo.setSexo(true);
        }
        catch(Exception ex)
        {
            if(this.transaccion!=null)
            {
                this.transaccion.rollback();
            }
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal:", "Por favor contacte con su administrador "+ex.getMessage()));
        }
        finally
        {
            if(this.session!=null)
            {
                this.session.close();
            }
        }
    }
    
    public MindmapNode cargarNodosUsuarioAmigoPorCodigoUsuario()
    {
        this.session=null;
        this.transaccion=null;
        
        try
        {
            DaoUsuarioAmigo daoTUsuarioAmigo=new DaoUsuarioAmigo();
            DaoTUsuario daoTUsuario=new DaoTUsuario();
            
            this.session=HibernateUtil.getSessionFactory().openSession();
            this.transaccion=session.beginTransaction();
            
            HttpSession sessionUsuario=(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            
            this.usuario=daoTUsuario.getByEmail(this.session, sessionUsuario.getAttribute("correoElectronico").toString());
            this.listaUsuarioAmigo=daoTUsuarioAmigo.getByCodigoUsuario(this.session, this.usuario.getCodigoUsuario());
            
            this.nodoUsuario=new DefaultMindmapNode(this.usuario.getNombre()+" "+this.usuario.getApellido1()+" "+this.usuario.getApellido2(), this.usuario.getCodigoUsuario(), "EBF8A4", false);
            
            for(Tusuarioamigo item : this.listaUsuarioAmigo)
            {
                this.nodoUsuario.addNode(new DefaultMindmapNode(item.getNombre()+" "+item.getApellido1()+" "+item.getApellido2()+':'+item.getCodigoUsuarioAmigo(), item.getCodigoUsuarioAmigo(), "E5E5E5", true));
            }
            
            this.transaccion.commit();

            return this.nodoUsuario;
        }
        catch(Exception ex)
        {
            if(this.transaccion!=null)
            {
                this.transaccion.rollback();
            }
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal:", "Por favor contacte con su administrador "+ex.getMessage()));
            
            return null;
        }
        finally
        {
            if(this.session!=null)
            {
                this.session.close();
            }
        }
    }
    
    public void mostrarDialogoEditarUsuarioAmigo(SelectEvent event)
    {
        MindmapNode node=(MindmapNode) event.getObject();
        
        String codigoUsuarioAmigo=node.getLabel().split(":")[1];
        
        this.session=null;
        this.transaccion=null;
        
        try
        {
            DaoUsuarioAmigo daoTUsuarioAmigo=new DaoUsuarioAmigo();
            
            this.session=HibernateUtil.getSessionFactory().openSession();
            this.transaccion=session.beginTransaction();
            
            this.usuarioamigo=daoTUsuarioAmigo.getByCodigoUsuarioAmigo(this.session, codigoUsuarioAmigo);
            
            RequestContext.getCurrentInstance().update("frmEditarUsuarioAmigo:panelEditarUsuarioAmigo");
            RequestContext.getCurrentInstance().execute("PF('dialogoEditarUsuarioAmigo').show()");
            
            this.transaccion.commit();
        }
        catch(Exception ex)
        {
            if(this.transaccion!=null)
            {
                this.transaccion.rollback();
            }
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal:", "Por favor contacte con su administrador "+ex.getMessage()));
        }
        finally
        {
            if(this.session!=null)
            {
                this.session.close();
            }
        }
    }
    
    public String update()
    {
        this.session=null;
        this.transaccion=null;
        
        try
        {
            DaoUsuarioAmigo daoTUsuarioAmigo=new DaoUsuarioAmigo();
            
            this.session=HibernateUtil.getSessionFactory().openSession();
            this.transaccion=session.beginTransaction();

            if(daoTUsuarioAmigo.getByCorreoElectronicoDiferent(this.session, this.usuarioamigo.getCodigoUsuarioAmigo(), this.usuarioamigo.getCorreoElectronico())!=null)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Correo electrónico ocupado"));

                return "verporcodigousuario.xhtml";
            }

            daoTUsuarioAmigo.update(this.session, this.usuarioamigo);
            
            this.transaccion.commit();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto:", "Los cambios fueron guardados correctamente"));
            
            return "verporcodigousuario.xhtml";
        }
        catch(Exception ex)
        {
            if(this.transaccion!=null)
            {
                this.transaccion.rollback();
            }
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal:", "Por favor contacte con su administrador "+ex.getMessage()));
            
            return "verporcodigousuario.xhtml";
        }
        finally
        {
            if(this.session!=null)
            {
                this.session.close();
            }
        }
    }
    
    public Tusuarioamigo getUsuarioamigo() {
        return usuarioamigo;
    }

    public void setUsuarioamigo(Tusuarioamigo usuarioamigo) {
        this.usuarioamigo = usuarioamigo;
    }
    
    public List<Tusuarioamigo> getListaUsuarioAmigo() {
        return listaUsuarioAmigo;
    }

    public void setListaUsuarioAmigo(List<Tusuarioamigo> listaUsuarioAmigo) {
        this.listaUsuarioAmigo = listaUsuarioAmigo;
    }
    
    public Tusuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Tusuario usuario) {
        this.usuario = usuario;
    }

    public String getTxtContraseniaRepita() {
        return txtContraseniaRepita;
    }

    public void setTxtContraseniaRepita(String txtContraseniaRepita) {
        this.txtContraseniaRepita = txtContraseniaRepita;
    }
    
    public MindmapNode getNodoUsuario() {
        return nodoUsuario;
    }

    public void setNodoUsuario(MindmapNode nodoUsuario) {
        this.nodoUsuario = nodoUsuario;
    }
}
