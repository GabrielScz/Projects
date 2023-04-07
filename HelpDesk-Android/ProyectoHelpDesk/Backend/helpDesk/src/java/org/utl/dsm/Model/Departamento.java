/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.dsm.Model;


public class Departamento {
    
    private int idDepartamento;
    private String nombreDepartamento;
    private String sucursal;

    public Departamento() {
    }

    public Departamento(String nombreDepartamento, String sucursal) {
        this.nombreDepartamento = nombreDepartamento;
        this.sucursal = sucursal;
    }

    public Departamento(int idDepartamento, String nombreDepartamento, String sucursal) {
        this.idDepartamento = idDepartamento;
        this.nombreDepartamento = nombreDepartamento;
        this.sucursal = sucursal;
    }

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    @Override
    public String toString() {
        return "Departamento{" + "idDepartamento=" + idDepartamento + ", nombreDepartamento=" + nombreDepartamento + ", sucursal=" + sucursal + '}';
    }
}
