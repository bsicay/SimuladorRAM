/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.brandonsicay.bean;

import java.util.ArrayList;

/**
 *
 * @author brand
 */
public class SDR {
    private String tipo;
    private int memoriaTotal;
    private int memoriaDisponible;
    private int memoriaEnUso;
    private int bloques;
    private Programa ejecucion[];
    private ArrayList<Programa> enCola;

    public SDR() {
    }

    public SDR(String tipo, int memoriaTotal, int memoriaDisponible) {
        this.tipo = tipo;
        this.memoriaTotal = memoriaTotal;
        this.memoriaDisponible = memoriaDisponible;
    }
    

    public SDR(String tipo, int memoriaTotal, int memoriaDisponible, int bloques) {
        this.memoriaTotal = memoriaTotal;
        this.memoriaDisponible = memoriaDisponible;
        this.memoriaEnUso = memoriaEnUso;
        this.bloques = bloques;
    }
    
    
    public int getMemoriaTotal() {
        return memoriaTotal;
    }

    public void setMemoriaTotal(int memoriaTotal) {
        this.memoriaTotal = memoriaTotal;
    }

    public int getMemoriaDisponible() {
        return memoriaDisponible;
    }

    public void setMemoriaDisponible(int memoriaDisponible) {
        this.memoriaDisponible = memoriaDisponible;
    }

    public int getMemoriaEnUso() {
        return memoriaEnUso;
    }

    public void setMemoriaEnUso(int memoriaEnUso) {
        this.memoriaEnUso = memoriaEnUso;
    }

    public int getBloques() {
        return bloques;
    }

    public void setBloques(int bloques) {
        this.bloques = bloques;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    

    public ArrayList<Programa> getEnCola() {
        return enCola;
    }

    public void setEnCola(ArrayList<Programa> enCola) {
        this.enCola = enCola;
    }
    
    
    
}
