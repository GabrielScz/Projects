/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.dsm.Model;


public class Ticket {
    
    private int idTicket;
    private int estatus;
    private String dispositivo;
    private String descripcion;
    private String fechaHora;
    private Empleado empleado;

    public Ticket(int idTicket) {
        this.idTicket = idTicket;
    }

    public Ticket(int estatus, String dispositivo, String descripcion, String fechaHora, Empleado empleado) {
        this.estatus = estatus;
        this.dispositivo = dispositivo;
        this.descripcion = descripcion;
        this.fechaHora = fechaHora;
        this.empleado = empleado;
    }

    public Ticket(int idTicket, int estatus, String dispositivo, String descripcion, String fechaHora, Empleado empleado) {
        this.idTicket = idTicket;
        this.estatus = estatus;
        this.dispositivo = dispositivo;
        this.descripcion = descripcion;
        this.fechaHora = fechaHora;
        this.empleado = empleado;
    }

    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public String getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(String dispositivo) {
        this.dispositivo = dispositivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    @Override
    public String toString() {
        return "Ticket{" + "idTicket=" + idTicket + ", estatus=" + estatus + ", dispositivo=" + dispositivo + ", descripcion=" + descripcion + ", fechaHora=" + fechaHora + ", empleado=" + empleado + '}';
    }
}
