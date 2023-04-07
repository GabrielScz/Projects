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
public class DetalleVentaProducto {
    
    private Venta venta;
    private List<VentaProducto> listaVentaProducto;

    public DetalleVentaProducto() {
    }

    public DetalleVentaProducto(Venta venta, List<VentaProducto> listaVentaProducto) {
        this.venta = venta;
        this.listaVentaProducto = listaVentaProducto;
    }

    public List<VentaProducto> getListaVentaProducto() {
        return listaVentaProducto;
    }

    public void setListaVentaProducto(List<VentaProducto> listaVentaProducto) {
        this.listaVentaProducto = listaVentaProducto;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }
}
