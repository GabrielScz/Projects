/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.dsm.model;

/**
 *
 * @author carlossanchez
 */
public class Venta {
    private int idVenta;
    private Empleado empleado;
    private String clave;

    public Venta() {
    }

    public Venta(Empleado empleado, String clave) {
        this.empleado = empleado;
        this.clave = clave;
    }

    public Venta(int idVenta, Empleado empleado, String clave) {
        this.idVenta = idVenta;
        this.empleado = empleado;
        this.clave = clave;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
