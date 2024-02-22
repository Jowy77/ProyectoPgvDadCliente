/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javafx.proyectopgv_dad_servidor.modelo;


import java.io.Serializable;

/**
 *Esta clase representa un servicio del sistema.
 *Tuve que crearla para poder guardar los servicios ya que no se pueden serializar la propia clase de servicios del sistema.
 * @author Iproy
 */
public class Servicio implements Serializable{
    String nombre;
    String PID;
    String estadoDelServicio;

    public Servicio() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getEstadoDelServicio() {
        return estadoDelServicio;
    }

    public void setEstadoDelServicio(String estadoDelServicio) {
        this.estadoDelServicio = estadoDelServicio;
    }
    
    
}


