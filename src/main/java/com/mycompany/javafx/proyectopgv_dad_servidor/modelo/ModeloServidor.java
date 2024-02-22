package com.mycompany.javafx.proyectopgv_dad_servidor.modelo;

import java.io.Serializable;
import java.util.List;

/**
 * Clase para guardar los datos de los servidores recibidos
 * @author Iproy
 */
public class ModeloServidor implements Serializable {

    private String nombreServidor;
    private long memoriaRamTotal;
    private long memoriaRamUsada;
    private double porcentajeRamUsado;
    private String[] infoDisco;
    private double espacioTotalEnDisco;
    private double espacioDisponibleDisco;
    private String direccionMac;
    private String[] direccionIP;
    private Long usoTotalDeRed;
    private String porcentajeUsoProcesador;
    private Double porcentajeDoubleProcesador;
    private String[] obetenerInfoDelSistema;//S.O.,NOMBRE PROCESADOR,ALMACENAMIENTO,TARJETA DE RED,MEMORIA RAM TOTAL
    private List<Proceso> procesos;
    private List<Servicio> servicios;

    public ModeloServidor(String nombreServidor, long memoriaRamTotal, long memoriaRamUsada, double porcentajeRamUsado, String[] infoDisco, double espacioTotalEnDisco, double espacioDisponibleDisco, String direccionMac, String[] direccionIP, Long usoTotalDeRed, String porcentajeUsoProcesador, Double porcentajeDoubleProcesador, String[] obetenerInfoDelSistema) {
        this.nombreServidor = nombreServidor;
        this.memoriaRamTotal = memoriaRamTotal;
        this.memoriaRamUsada = memoriaRamUsada;
        this.porcentajeRamUsado = porcentajeRamUsado;
        this.infoDisco = infoDisco;
        this.espacioTotalEnDisco = espacioTotalEnDisco;
        this.espacioDisponibleDisco = espacioDisponibleDisco;
        this.direccionMac = direccionMac;
        this.direccionIP = direccionIP;
        this.usoTotalDeRed = usoTotalDeRed;
        this.porcentajeUsoProcesador = porcentajeUsoProcesador;
        this.porcentajeDoubleProcesador = porcentajeDoubleProcesador;
        this.obetenerInfoDelSistema = obetenerInfoDelSistema;
    }

    public ModeloServidor() {
    }

    public List<Proceso> getProcesos() {
        return procesos;
    }

    public void setProcesos(List<Proceso> procesos) {
        this.procesos = procesos;
    }

    public List<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
    }

    public Double getPorcentajeDoubleProcesador() {
        return porcentajeDoubleProcesador;
    }

    public void setPorcentajeDoubleProcesador(Double porcentajeDoubleProcesador) {
        this.porcentajeDoubleProcesador = porcentajeDoubleProcesador;
    }

    public String getNombreServidor() {
        return nombreServidor;
    }

    public void setNombreServidor(String nombreServidor) {
        this.nombreServidor = nombreServidor;
    }

    public long getMemoriaRamTotal() {
        return memoriaRamTotal;
    }

    public void setMemoriaRamTotal(long memoriaRamTotal) {
        this.memoriaRamTotal = memoriaRamTotal;
    }

    public long getMemoriaRamUsada() {
        return memoriaRamUsada;
    }

    public void setMemoriaRamUsada(long memoriaRamUsada) {
        this.memoriaRamUsada = memoriaRamUsada;
    }

    public double getPorcentajeRamUsado() {
        return porcentajeRamUsado;
    }

    public void setPorcentajeRamUsado(double porcentajeRamUsado) {
        this.porcentajeRamUsado = porcentajeRamUsado;
    }

    public String[] getInfoDisco() {
        return infoDisco;
    }

    public void setInfoDisco(String[] infoDisco) {
        this.infoDisco = infoDisco;
    }

    public double getEspacioTotalEnDisco() {
        return espacioTotalEnDisco;
    }

    public void setEspacioTotalEnDisco(double espacioTotalEnDisco) {
        this.espacioTotalEnDisco = espacioTotalEnDisco;
    }

    public double getEspacioDisponibleDisco() {
        return espacioDisponibleDisco;
    }

    public void setEspacioDisponibleDisco(double espacioDisponibleDisco) {
        this.espacioDisponibleDisco = espacioDisponibleDisco;
    }

    public String getDireccionMac() {
        return direccionMac;
    }

    public void setDireccionMac(String direccionMac) {
        this.direccionMac = direccionMac;
    }

    public String[] getDireccionIP() {
        return direccionIP;
    }

    public void setDireccionIP(String[] direccionIP) {
        this.direccionIP = direccionIP;
    }

    public Long getUsoTotalDeRed() {
        return usoTotalDeRed;
    }

    public void setUsoTotalDeRed(Long usoTotalDeRed) {
        this.usoTotalDeRed = usoTotalDeRed;
    }

    public String getPorcentajeUsoProcesador() {
        return porcentajeUsoProcesador;
    }

    public void setPorcentajeUsoProcesador(String porcentajeUsoProcesador) {
        this.porcentajeUsoProcesador = porcentajeUsoProcesador;
    }

    public String[] getObetenerInfoDelSistema() {
        return obetenerInfoDelSistema;
    }

    public void setObetenerInfoDelSistema(String[] obetenerInfoDelSistema) {
        this.obetenerInfoDelSistema = obetenerInfoDelSistema;
    }

}
