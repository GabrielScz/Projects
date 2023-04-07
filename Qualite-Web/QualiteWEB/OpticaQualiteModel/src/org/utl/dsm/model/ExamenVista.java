/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.dsm.model;

/**
 *
 * @author carlossanchez
 */
public class ExamenVista {
    private int idExamenVista;
    private String clave;
    private double fecha;
    private Empleado empleado;
    private Cliente cliente;
    private Graduacion graduacion;

    public ExamenVista() {
    }

    public ExamenVista(String clave, double fecha, Empleado empleado, Cliente cliente, Graduacion graduacion) {
        this.clave = clave;
        this.fecha = fecha;
        this.empleado = empleado;
        this.cliente = cliente;
        this.graduacion = graduacion;
    }

    public ExamenVista(int idExamenVista, String clave, double fecha, Empleado empleado, Cliente cliente, Graduacion graduacion) {
        this.idExamenVista = idExamenVista;
        this.clave = clave;
        this.fecha = fecha;
        this.empleado = empleado;
        this.cliente = cliente;
        this.graduacion = graduacion;
    }

    public int getIdExamenVista() {
        return idExamenVista;
    }

    public void setIdExamenVista(int idExamenVista) {
        this.idExamenVista = idExamenVista;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public double getFecha() {
        return fecha;
    }

    public void setFecha(double fecha) {
        this.fecha = fecha;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Graduacion getGraduacion() {
        return graduacion;
    }

    public void setGraduacion(Graduacion graduacion) {
        this.graduacion = graduacion;
    }

    @Override
    public String toString() {
        return "ExamenVista{" + "idExamenVista=" + idExamenVista + ", clave=" + clave + ", fecha=" + fecha + ", empleado=" + empleado + ", cliente=" + cliente + ", graduacion=" + graduacion + '}';
    }

    
}
