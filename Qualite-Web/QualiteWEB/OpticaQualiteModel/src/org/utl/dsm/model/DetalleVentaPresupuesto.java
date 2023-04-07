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

public class DetalleVentaPresupuesto {
    
    private List<VentaPresupuesto> listaVentaPresupuesto;
    private Venta venta;

    public DetalleVentaPresupuesto() {
    }

    public DetalleVentaPresupuesto(List<VentaPresupuesto> listaVentaPresupuesto, Venta venta) {
        this.listaVentaPresupuesto = listaVentaPresupuesto;
        this.venta = venta;
    }

    public List<VentaPresupuesto> getListaVentaPresupuesto() {
        return listaVentaPresupuesto;
    }

    public void setListaVentaPresupuesto(List<VentaPresupuesto> listaVentaPresupuesto) {
        this.listaVentaPresupuesto = listaVentaPresupuesto;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    @Override
    public String toString() {
        return "DetalleVentaPresupuesto{" + "listaVentaPresupuesto=" + listaVentaPresupuesto + ", venta=" + venta + '}';
    }
}
