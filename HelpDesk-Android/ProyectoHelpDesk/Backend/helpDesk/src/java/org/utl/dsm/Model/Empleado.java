/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.dsm.Model;


public class Empleado {
    
    private int idEmpleado;
    private String nombreEmpleado;
    private String primerApellido;
    private String segundoApellido;
    private String rfc;
    private String email;
    private String telefono;
    private String fotografia;
    private Usuario usuario;
    private Departamento departamento;

    public Empleado() {
    }

    public Empleado(String nombreEmpleado, String primerApellido, String segundoApellido, String rfc, String email, String telefono, String fotografia, Usuario usuario, Departamento departamento) {
        this.nombreEmpleado = nombreEmpleado;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.rfc = rfc;
        this.email = email;
        this.telefono = telefono;
        this.fotografia = fotografia;
        this.usuario = usuario;
        this.departamento = departamento;
    }

    public Empleado(int idEmpleado, String nombreEmpleado, String primerApellido, String segundoApellido, String rfc, String email, String telefono, String fotografia, Usuario usuario, Departamento departamento) {
        this.idEmpleado = idEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.rfc = rfc;
        this.email = email;
        this.telefono = telefono;
        this.fotografia = fotografia;
        this.usuario = usuario;
        this.departamento = departamento;
    }

    @Override
    public String toString() {
        return "Empleado{" + "idEmpleado=" + idEmpleado + ", nombreEmpleado=" + nombreEmpleado + ", primerApellido=" + primerApellido + ", segundoApellido=" + segundoApellido + ", rfc=" + rfc + ", email=" + email + ", telefono=" + telefono + ", usuario=" + usuario + ", departamento=" + departamento + '}';
    }    

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFotografia() {
        return fotografia;
    }

    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
}
