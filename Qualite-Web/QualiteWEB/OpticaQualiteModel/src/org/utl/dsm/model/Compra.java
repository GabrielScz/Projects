/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.dsm.model;

/**
 *
 * @author carlossanchez
 */
public class Compra {
    private int idCompra;
    private Empleado idEmpleado;

    public Compra() {
    }

    public Compra(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Compra(int idCompra, Empleado idEmpleado) {
        this.idCompra = idCompra;
        this.idEmpleado = idEmpleado;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }
    
    
    
}
