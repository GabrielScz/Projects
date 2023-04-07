/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.dsm.model;

/**
 *
 * @author carlossanchez
 */
public class presupuestoLentesContacto {
    private int idPresupuestoLentesContacto;
    private ExamenVista examenVista;
    private LenteContacto lenteContacto;
    private Presupuesto presupuesto;
    private String clave;

    public presupuestoLentesContacto() {
    }

    public presupuestoLentesContacto(ExamenVista examenVista, LenteContacto lenteContacto, Presupuesto presupuesto, String clave) {
        this.examenVista = examenVista;
        this.lenteContacto = lenteContacto;
        this.presupuesto = presupuesto;
        this.clave = clave;
    }

    public presupuestoLentesContacto(int idPresupuestoLentesContacto, ExamenVista examenVista, LenteContacto lenteContacto, Presupuesto presupuesto, String clave) {
        this.idPresupuestoLentesContacto = idPresupuestoLentesContacto;
        this.examenVista = examenVista;
        this.lenteContacto = lenteContacto;
        this.presupuesto = presupuesto;
        this.clave = clave;
    }

    public int getIdPresupuestoLentesContacto() {
        return idPresupuestoLentesContacto;
    }

    public void setIdPresupuestoLentesContacto(int idPresupuestoLentesContacto) {
        this.idPresupuestoLentesContacto = idPresupuestoLentesContacto;
    }

    public ExamenVista getExamenVista() {
        return examenVista;
    }

    public void setExamenVista(ExamenVista examenVista) {
        this.examenVista = examenVista;
    }

    public LenteContacto getLenteContacto() {
        return lenteContacto;
    }

    public void setLenteContacto(LenteContacto lenteContacto) {
        this.lenteContacto = lenteContacto;
    }

    public Presupuesto getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Presupuesto presupuesto) {
        this.presupuesto = presupuesto;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Override
    public String toString() {
        return "presupuestoLentesContacto{" + "idPresupuestoLentesContacto=" + idPresupuestoLentesContacto + ", examenVista=" + examenVista + ", lenteContacto=" + lenteContacto + ", presupuesto=" + presupuesto + ", clave=" + clave + '}';
    }

    
}