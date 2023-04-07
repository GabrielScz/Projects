/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.dsm.model;

/**
 *
 * @author carlossanchez
 */
public class Graduacion {
    private int idGraduacion;
    private double esferaod;
    private double esferaoi;
    private int cilindrood;
    private int cilindrooi;
    private int ejeoi;
    private int ejeod;
    private String dip;

    public Graduacion() {
    }

    public Graduacion(double esferaod, double esferaoi, int cilindrood, int cilindrooi, int ejeoi, int ejeod, String dip) {
        this.esferaod = esferaod;
        this.esferaoi = esferaoi;
        this.cilindrood = cilindrood;
        this.cilindrooi = cilindrooi;
        this.ejeoi = ejeoi;
        this.ejeod = ejeod;
        this.dip = dip;
    }

    public Graduacion(int idGraduacion, double esferaod, double esferaoi, int cilindrood, int cilindrooi, int ejeoi, int ejeod, String dip) {
        this.idGraduacion = idGraduacion;
        this.esferaod = esferaod;
        this.esferaoi = esferaoi;
        this.cilindrood = cilindrood;
        this.cilindrooi = cilindrooi;
        this.ejeoi = ejeoi;
        this.ejeod = ejeod;
        this.dip = dip;
    }

    public int getIdGraduacion() {
        return idGraduacion;
    }

    public void setIdGraduacion(int idGraduacion) {
        this.idGraduacion = idGraduacion;
    }

    public double getEsferaod() {
        return esferaod;
    }

    public void setEsferaod(double esferaod) {
        this.esferaod = esferaod;
    }

    public double getEsferaoi() {
        return esferaoi;
    }

    public void setEsferaoi(double esferaoi) {
        this.esferaoi = esferaoi;
    }

    public int getCilindrood() {
        return cilindrood;
    }

    public void setCilindrood(int cilindrood) {
        this.cilindrood = cilindrood;
    }

    public int getCilindrooi() {
        return cilindrooi;
    }

    public void setCilindrooi(int cilindrooi) {
        this.cilindrooi = cilindrooi;
    }

    public int getEjeoi() {
        return ejeoi;
    }

    public void setEjeoi(int ejeoi) {
        this.ejeoi = ejeoi;
    }

    public int getEjeod() {
        return ejeod;
    }

    public void setEjeod(int ejeod) {
        this.ejeod = ejeod;
    }

    public String getDip() {
        return dip;
    }

    public void setDip(String dip) {
        this.dip = dip;
    }
    
    
    
    
}
