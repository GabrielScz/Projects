/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.dsm.model;

/**
 *
 * @author carlossanchez
 */
public class VentaPresupuestoLentes {
    
    private int cantidad;
    private double precioUnitario;
    private double descuento;
    private presupuestoLentes presupuestoLentes;

    public VentaPresupuestoLentes() {
    }

    public VentaPresupuestoLentes(int cantidad, double precioUnitario, double descuento, presupuestoLentes presupuestoLentes) {
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.descuento = descuento;
        this.presupuestoLentes = presupuestoLentes;
    }

    public presupuestoLentes getPresupuestoLentes() {
        return presupuestoLentes;
    }

    public void setPresupuestoLentes(presupuestoLentes presupuestoLentes) {
        this.presupuestoLentes = presupuestoLentes;
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

    @Override
    public String toString() {
        return "VentaPresupuestoLentes{" + "cantidad=" + cantidad + ", precioUnitario=" + precioUnitario + ", descuento=" + descuento + ", presupuestoLentes=" + presupuestoLentes + '}';
    }
}
