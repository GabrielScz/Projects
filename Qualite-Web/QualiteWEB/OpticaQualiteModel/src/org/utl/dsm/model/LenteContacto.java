/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.dsm.model;

/**
 *
 * @author ruth1
 */
public class LenteContacto {
 
    
    private int idLenteContacto;
    private int keratometria;
    private String fotografia;
    private Producto producto;

    public LenteContacto() {
    }

    public LenteContacto(int keratometria, String fotografia, Producto producto) {
        this.keratometria = keratometria;
        this.fotografia = fotografia;
        this.producto = producto;
    }

    public LenteContacto(int idLenteContacto, int keratometria, String fotografia, Producto producto) {
        this.idLenteContacto = idLenteContacto;
        this.keratometria = keratometria;
        this.fotografia = fotografia;
        this.producto = producto;
    }

    public int getIdLenteContacto() {
        return idLenteContacto;
    }

    public void setIdLenteContacto(int idLenteContacto) {
        this.idLenteContacto = idLenteContacto;
    }

    public int getKeratometria() {
        return keratometria;
    }

    public void setKeratometria(int keratometria) {
        this.keratometria = keratometria;
    }

    public String getFotografia() {
        return fotografia;
    }

    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return "LenteContacto{" + "idLenteContacto=" + idLenteContacto + ", keratometria=" + keratometria + ", fotografia=" + fotografia + ", producto=" + producto + '}';
    }
    
    
}
    

    

    