/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javafx.proyectopgv_dad_servidor.modelo;

import java.io.Serializable;

/**
 * Esta clase representa un proceso del sistema.
 * Tuve que crearla para poder guardar los procesos ya que no se pueden serializar la propia clase de procesos del sistema.
 * @author Iproy
 */
public class Proceso implements Serializable{
    String PID;
    String nombre;
    String estado;
    String Prioridad;

    public Proceso() {
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPrioridad() {
        return Prioridad;
    }

    public void setPrioridad(String Prioridad) {
        this.Prioridad = Prioridad;
    }
    
    
}
