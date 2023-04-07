 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.dsm.model;

/**
 *
 * @author carlossanchez
 */
public class Empleado {
    private int idEmpleado;
    private int estatus;
    private String numeroUnico;
    private Persona persona;
    private Usuario usuario;

    public Empleado() {
    }

    public Empleado(int estatus, String numeroUnico, Persona persona, Usuario usuario) {
        this.estatus = estatus;
        this.numeroUnico = numeroUnico;
        this.persona = persona;
        this.usuario = usuario;
    }

    public Empleado(int idEmpleado, int estatus, String numeroUnico, Persona persona, Usuario usuario) {
        this.idEmpleado = idEmpleado;
        this.estatus = estatus;
        this.numeroUnico = numeroUnico;
        this.persona = persona;
        this.usuario = usuario;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public String getNumeroUnico() {
        return numeroUnico;
    }

    public void setNumeroUnico(String numeroUnico) {
        this.numeroUnico = numeroUnico;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Empleado{" + "idEmpleado=" + idEmpleado + ", estatus=" + estatus + ", numeroUnico=" + numeroUnico + ", persona=" + persona + ", usuario=" + usuario + '}';
    }

   
}
