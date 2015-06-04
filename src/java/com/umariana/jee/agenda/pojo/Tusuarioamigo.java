package com.umariana.jee.agenda.pojo;
// Generated 27-may-2015 22:53:34 by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Tusuarioamigo generated by hbm2java
 */
public class Tusuarioamigo  implements java.io.Serializable {


     private String codigoUsuarioAmigo;
     private Tusuario tusuario;
     private String nombre;
     private String apellido1;
     private String apellido2;
     private String correoElectronico;
     private String contrasenia;
     private Date fechaNacimiento;
     private boolean sexo;
     private String telefono;
     private Date fechaRegistro;
     private Date fechaModificacion;
     private Set tactividadparticipantes = new HashSet(0);
     private Set tusuarioamigotelefonos = new HashSet(0);
     private Set tactividadcomentarios = new HashSet(0);

    public Tusuarioamigo() {
    }

	
    public Tusuarioamigo(String codigoUsuarioAmigo, Tusuario tusuario, String nombre, String apellido1, String apellido2, String correoElectronico, String contrasenia, Date fechaNacimiento, boolean sexo, String telefono, Date fechaRegistro, Date fechaModificacion) {
        this.codigoUsuarioAmigo = codigoUsuarioAmigo;
        this.tusuario = tusuario;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.correoElectronico = correoElectronico;
        this.contrasenia = contrasenia;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.telefono = telefono;
        this.fechaRegistro = fechaRegistro;
        this.fechaModificacion = fechaModificacion;
    }
    public Tusuarioamigo(String codigoUsuarioAmigo, Tusuario tusuario, String nombre, String apellido1, String apellido2, String correoElectronico, String contrasenia, Date fechaNacimiento, boolean sexo, String telefono, Date fechaRegistro, Date fechaModificacion, Set tactividadparticipantes, Set tusuarioamigotelefonos, Set tactividadcomentarios) {
       this.codigoUsuarioAmigo = codigoUsuarioAmigo;
       this.tusuario = tusuario;
       this.nombre = nombre;
       this.apellido1 = apellido1;
       this.apellido2 = apellido2;
       this.correoElectronico = correoElectronico;
       this.contrasenia = contrasenia;
       this.fechaNacimiento = fechaNacimiento;
       this.sexo = sexo;
       this.telefono = telefono;
       this.fechaRegistro = fechaRegistro;
       this.fechaModificacion = fechaModificacion;
       this.tactividadparticipantes = tactividadparticipantes;
       this.tusuarioamigotelefonos = tusuarioamigotelefonos;
       this.tactividadcomentarios = tactividadcomentarios;
    }
   
    public String getCodigoUsuarioAmigo() {
        return this.codigoUsuarioAmigo;
    }
    
    public void setCodigoUsuarioAmigo(String codigoUsuarioAmigo) {
        this.codigoUsuarioAmigo = codigoUsuarioAmigo;
    }
    public Tusuario getTusuario() {
        return this.tusuario;
    }
    
    public void setTusuario(Tusuario tusuario) {
        this.tusuario = tusuario;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido1() {
        return this.apellido1;
    }
    
    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }
    public String getApellido2() {
        return this.apellido2;
    }
    
    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }
    public String getCorreoElectronico() {
        return this.correoElectronico;
    }
    
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
    public String getContrasenia() {
        return this.contrasenia;
    }
    
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
    public Date getFechaNacimiento() {
        return this.fechaNacimiento;
    }
    
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    public boolean isSexo() {
        return this.sexo;
    }
    
    public void setSexo(boolean sexo) {
        this.sexo = sexo;
    }
    public String getTelefono() {
        return this.telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public Date getFechaRegistro() {
        return this.fechaRegistro;
    }
    
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    public Date getFechaModificacion() {
        return this.fechaModificacion;
    }
    
    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
    public Set getTactividadparticipantes() {
        return this.tactividadparticipantes;
    }
    
    public void setTactividadparticipantes(Set tactividadparticipantes) {
        this.tactividadparticipantes = tactividadparticipantes;
    }
    public Set getTusuarioamigotelefonos() {
        return this.tusuarioamigotelefonos;
    }
    
    public void setTusuarioamigotelefonos(Set tusuarioamigotelefonos) {
        this.tusuarioamigotelefonos = tusuarioamigotelefonos;
    }
    public Set getTactividadcomentarios() {
        return this.tactividadcomentarios;
    }
    
    public void setTactividadcomentarios(Set tactividadcomentarios) {
        this.tactividadcomentarios = tactividadcomentarios;
    }




}


