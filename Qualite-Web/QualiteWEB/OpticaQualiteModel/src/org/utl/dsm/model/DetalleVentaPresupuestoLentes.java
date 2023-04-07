/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.dsm.model;

import java.util.List;

/**
 *
 * @author carlossanchez
 */
public class DetalleVentaPresupuestoLentes {
    
    private List<VentaPresupuestoLentes> ListaVentaPresupuestoLentes;
    private Venta venta;

    public DetalleVentaPresupuestoLentes() {
    }

    public DetalleVentaPresupuestoLentes(List<VentaPresupuestoLentes> ListaVentaPresupuestoLentes, Venta venta) {
        this.ListaVentaPresupuestoLentes = ListaVentaPresupuestoLentes;
        this.venta = venta;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public List<VentaPresupuestoLentes> getListaVentaPresupuestoLentes() {
        return ListaVentaPresupuestoLentes;
    }

    public void setListaVentaPresupuestoLentes(List<VentaPresupuestoLentes> ListaVentaPresupuestoLentes) {
        this.ListaVentaPresupuestoLentes = ListaVentaPresupuestoLentes;
    }

    @Override
    public String toString() {
        return "DetalleVentaPresupuestoLentes{" + "ListaVentaPresupuestoLentes=" + ListaVentaPresupuestoLentes + ", venta=" + venta + '}';
    }
}
