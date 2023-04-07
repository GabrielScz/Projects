/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.dsm.model;

/**
 *
 * @author carlossanchez
 */
public class VentaPresupuesto {
    
    private int cantidad;
    private double precioUnitario;
    private double descuento;
    private presupuestoLentesContacto presupuestoLentesContacto;

    public VentaPresupuesto() {
    }

    public VentaPresupuesto(int cantidad, double precioUnitario, double descuento, presupuestoLentesContacto presupuestoLentesContacto) {
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.descuento = descuento;
        this.presupuestoLentesContacto = presupuestoLentesContacto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public presupuestoLentesContacto getPresupuestoLentesContacto() {
        return presupuestoLentesContacto;
    }

    public void setPresupuestoLentesContacto(presupuestoLentesContacto presupuestoLentesContacto) {
        this.presupuestoLentesContacto = presupuestoLentesContacto;
    }

    @Override
    public String toString() {
        return "VentaPresupuesto{" + "cantidad=" + cantidad + ", precioUnitario=" + precioUnitario + ", descuento=" + descuento + ", presupuestoLentesContacto=" + presupuestoLentesContacto + '}';
    }
}
