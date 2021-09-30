/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.brandonsicay.bean;

/**
 *
 * @author brand
 */
public class Programa {
    private String nombre;
    private int cantidadMemoria;
    private int tiempoEjecucion;
    private String estado;
    private int bloquesUtilizados;

    public Programa() {
    }

    public Programa(String estado) {
        this.estado = estado;
    }
    
    
    
    public Programa(String nombre, int cantidadMemoria, int tiempoEjecucion, String estado, int bloquesUtilizados) {
        this.nombre = nombre;
        this.cantidadMemoria = cantidadMemoria;
        this.tiempoEjecucion = tiempoEjecucion;
        this.estado = estado;
        this.bloquesUtilizados = bloquesUtilizados;
    }
 
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidadMemoria() {
        return cantidadMemoria;
    }

    public void setCantidadMemoria(int cantidadMemoria) {
        this.cantidadMemoria = cantidadMemoria;
    }

    public int getTiempoEjecucion() {
        return tiempoEjecucion;
    }

    public void setTiempoEjecucion(int tiempoEjecucion) {
        this.tiempoEjecucion = tiempoEjecucion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getBloquesUtilizados() {
        return bloquesUtilizados;
    }

    public void setBloquesUtilizados(int bloquesUtilizados) {
        this.bloquesUtilizados = bloquesUtilizados;
    }
    
        
    
    
}
