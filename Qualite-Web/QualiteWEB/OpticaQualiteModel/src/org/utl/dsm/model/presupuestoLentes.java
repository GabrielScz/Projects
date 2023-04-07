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
public class presupuestoLentes {
    private int idPresupuestoLentes;
    private int alturaOblea;
    private Presupuesto presupuesto;
    private TipoMica tipoMica;
    private Material material;
    private Armazon armazon;
    private List<Tratamiento> listaTratamiento;

    public presupuestoLentes() {
    }

    public presupuestoLentes(int alturaOblea, Presupuesto presupuesto, TipoMica tipoMica, Material material, Armazon armazon, List<Tratamiento> listaTratamiento) {
        this.alturaOblea = alturaOblea;
        this.presupuesto = presupuesto;
        this.tipoMica = tipoMica;
        this.material = material;
        this.armazon = armazon;
        this.listaTratamiento = listaTratamiento;
    }

    public presupuestoLentes(int idPresupuestoLentes, int alturaOblea, Presupuesto presupuesto, TipoMica tipoMica, Material material, Armazon armazon, List<Tratamiento> listaTratamiento) {
        this.idPresupuestoLentes = idPresupuestoLentes;
        this.alturaOblea = alturaOblea;
        this.presupuesto = presupuesto;
        this.tipoMica = tipoMica;
        this.material = material;
        this.armazon = armazon;
        this.listaTratamiento = listaTratamiento;
    }

    public int getIdPresupuestoLentes() {
        return idPresupuestoLentes;
    }

    public void setIdPresupuestoLentes(int idPresupuestoLentes) {
        this.idPresupuestoLentes = idPresupuestoLentes;
    }

    public int getAlturaOblea() {
        return alturaOblea;
    }

    public void setAlturaOblea(int alturaOblea) {
        this.alturaOblea = alturaOblea;
    }

    public Presupuesto getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Presupuesto presupuesto) {
        this.presupuesto = presupuesto;
    }

    public TipoMica getTipoMica() {
        return tipoMica;
    }

    public void setTipoMica(TipoMica tipoMica) {
        this.tipoMica = tipoMica;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Armazon getArmazon() {
        return armazon;
    }

    public void setArmazon(Armazon armazon) {
        this.armazon = armazon;
    }

    public List<Tratamiento> getListaTratamiento() {
        return listaTratamiento;
    }

    public void setListaTratamiento(List<Tratamiento> listaTratamiento) {
        this.listaTratamiento = listaTratamiento;
    }

    @Override
    public String toString() {
        return "presupuestoLentes{" + "idPresupuestoLentes=" + idPresupuestoLentes + ", alturaOblea=" + alturaOblea + ", presupuesto=" + presupuesto + ", tipoMica=" + tipoMica + ", material=" + material + ", armazon=" + armazon + ", listaTratamiento=" + listaTratamiento + '}';
    }
}
