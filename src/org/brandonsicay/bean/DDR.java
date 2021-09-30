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
public class DDR {
    private String tipo;
    private int memoriaTotal;
    private int memoriaDisponible;
    private int memoriaEnUso;
    private ArrayList<Programa[]> ejecucion;
    private ArrayList<Programa> enCola;

    public DDR() {
    }

    public DDR(String tipo, int memoriaTotal, int memoriaDisponible) {
        this.tipo = tipo;
        this.memoriaTotal = memoriaTotal;
        this.memoriaDisponible = memoriaDisponible;
    }

    public DDR(int memoriaTotal, int memoriaDisponible, int memoriaEnUso) {
        this.memoriaTotal = memoriaTotal;
        this.memoriaDisponible = memoriaDisponible;
        this.memoriaEnUso = memoriaEnUso;
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
    
    
    
}
