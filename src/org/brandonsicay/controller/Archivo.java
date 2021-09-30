/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.brandonsicay.controller;

/**
 *
 * @author brand
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.brandonsicay.bean.DDR;
import org.brandonsicay.bean.Programa;
import org.brandonsicay.bean.SDR;
/**
 *
 * @author brand
 */
public class Archivo {
    private File archivoFisico;
    private int memoriaTotal;
    private int memoriaDisponible;
    private DDR memoryDDR;
    private SDR memorySDR;
    private Programa programa;
    private int totalUsado = 0;
    private ObservableList<Programa> listaProgramasPrevios;
    private ObservableList<Programa> listaProgramasActualizados;
    File archivoPrograma = new File("D:\\Cursos\\Java\\SimuladorRAM\\src\\org\\brandonsicay\\archivos\\programasIniciales.txt");
    File archivoTermiandos = new File("D:\\Cursos\\Java\\SimuladorRAM\\src\\org\\brandonsicay\\archivos\\programasFinalizados.txt");
    File archivoMemoria = new File("D:\\Cursos\\Java\\SimuladorRAM\\src\\org\\brandonsicay\\archivos\\datosMemoria.txt");
    ArrayList<Programa> lista = new ArrayList<Programa>();
    ArrayList<Programa> listaProgramas = new ArrayList<Programa>();
  

    public Archivo() {
    }
    
    public Archivo(File archivoFisico) {
		this.archivoFisico = archivoFisico;
	}
    
    public Archivo(File archivoFisico, DDR memoryDDR) {
		this.archivoFisico = archivoFisico;
		this.memoryDDR = memoryDDR;
    }
    
    public Archivo(File archivoFisico, SDR memorySDR) {
		this.archivoFisico = archivoFisico;
		this.memorySDR = memorySDR;
    }
    
    public Archivo(File archivoFisico, Programa programa) {
		this.archivoFisico = archivoFisico;
		this.programa = programa;
    }

    public ArrayList<Programa> getListaProgramas() {
        return listaProgramas;
    }
    
    
    public void GuardarInformacionPrograma() throws Exception{
        String fileContent = "" + programa.getNombre()+ "," + programa.getCantidadMemoria()+ "," + programa.getTiempoEjecucion() + "," + programa.getEstado()+ 
                "," + programa.getBloquesUtilizados() + "\r\n";
        FileWriter myWriter = new FileWriter(archivoFisico, true);
        myWriter.write(fileContent);
        myWriter.close();
        listaProgramas.add(new Programa(programa.getNombre(), programa.getCantidadMemoria(), programa.getTiempoEjecucion(), programa.getEstado(), 
        programa.getBloquesUtilizados()));
        
    }
    
    public ObservableList<Programa> GuardarInformacionProgramaInicializado() throws Exception{
        String fileContent = "" + programa.getNombre()+ "," + programa.getCantidadMemoria()+ "," + programa.getTiempoEjecucion() + "," + programa.getEstado()+ 
                "," + programa.getBloquesUtilizados() + "\r\n";
        FileWriter myWriter = new FileWriter(archivoFisico, true);
        myWriter.write(fileContent);
        myWriter.close();
        listaProgramas.add(new Programa(programa.getNombre(), programa.getCantidadMemoria(), programa.getTiempoEjecucion(), programa.getEstado(), 
        programa.getBloquesUtilizados()));
        return listaProgramasPrevios = FXCollections.observableList(lista);
    }
    
    
    public void GuardarInformacionDDR() throws Exception{
        String fileContent = "" + "DDR" + "," +  memoryDDR.getMemoriaTotal()+ "," + memoryDDR.getMemoriaDisponible() + "\r\n";
        FileWriter myWriter = new FileWriter(archivoFisico, true);
        myWriter.write(fileContent);
        myWriter.close();
    }
    
    public void GuardarInformacionSDR() throws Exception{
        String fileContent = "" + "SDR" + "," + memorySDR.getMemoriaTotal()+ "," + memorySDR.getMemoriaDisponible() +  "," +
                memorySDR.getBloques() + "\r\n";
        FileWriter myWriter = new FileWriter(archivoFisico, true);
        myWriter.write(fileContent);
        myWriter.close();
	
    }
    
    public ObservableList<Programa> getProgramasPrevios(File archivoFisico) throws Exception{
        Scanner myReader = new Scanner(archivoFisico);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            String[] datosPrograma = data.split(",", 5);
            lista.add(new Programa(datosPrograma[0], Integer.parseInt(datosPrograma[1]), Integer.parseInt(datosPrograma[2]), datosPrograma[3], Integer.parseInt(datosPrograma[4])));
        }
        return listaProgramasPrevios = FXCollections.observableList(lista);
    }
    
    public String[] getDatosMemoria(File archivoFisico) throws Exception{
        ArrayList<String> lista = new ArrayList<String>();
        String[] datosPrograma = null;
        Scanner myReader = new Scanner(archivoFisico);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            datosPrograma = data.split(",", 3);
            if("DDR".equals(datosPrograma[0])){
                lista.add("DDR");
                lista.add(datosPrograma[1]);
                lista.add(datosPrograma[2]);
            }else if("SDR".equals(datosPrograma[0])){
                lista.add("SDR");
                lista.add(datosPrograma[1]);
                lista.add(datosPrograma[2]);
            }
        }
        
        return datosPrograma;
    }
    
    public ArrayList<String> getProgramas(File archivoFisico) throws Exception{
        ArrayList<String> lista = new ArrayList<String>();
        String[] datosPrograma = null;
        Scanner myReader = new Scanner(archivoFisico);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            datosPrograma = data.split(",", 5);
            lista.add(datosPrograma[0]);
            lista.add(datosPrograma[1]);
            lista.add(datosPrograma[2]);
            lista.add(datosPrograma[3]);
            lista.add(datosPrograma[4]);
        }
        
        return lista;
    }
    
    
    public int getDatosPrograma(File archivoFisico) throws Exception{
        ArrayList<String> lista = new ArrayList<String>();
        String[] datosPrograma = null;
        Scanner myReader = new Scanner(archivoFisico);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            datosPrograma = data.split(",", 5);
            totalUsado = totalUsado + Integer.parseInt(datosPrograma[1]);
        }
        
        return totalUsado;
    }
    
    public ObservableList<Programa> actualizarDatosPrograma(File archivoFisico, int indice) throws Exception{
        archivoTermiandos.createNewFile();
        Scanner myReader = new Scanner(archivoFisico);
        FileWriter myWriter = new FileWriter(archivoTermiandos, true);
        String[] datosPrograma;
        for(int i = 0; i <= indice; i++){
            String data = myReader.nextLine();
            datosPrograma = data.split(",", 5);
            lista.add(new Programa(datosPrograma[0], Integer.parseInt(datosPrograma[1]), Integer.parseInt(datosPrograma[2]), programa.getEstado(), Integer.parseInt(datosPrograma[4])));
            String fileContent = "" + datosPrograma[0] + ","  + datosPrograma[1] + "," + datosPrograma[2] + ","  + programa.getEstado() + "," + datosPrograma[4] + "\r\n";
            myWriter.write(fileContent);
        }
//        lista.set(2, new Programa(programa.getEstado()));
        myWriter.close();
        return listaProgramasPrevios = FXCollections.observableList(lista);
    }
    public ObservableList<Programa> actualizarEstadoEjecucion(File archivoFisico) throws Exception{
        archivoTermiandos.createNewFile();
        Scanner myReader = new Scanner(archivoFisico);
        FileWriter myWriter = new FileWriter(archivoFisico, true);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            String[] datosPrograma = data.split(",", 5);
            lista.add(new Programa(datosPrograma[0], Integer.parseInt(datosPrograma[1]), Integer.parseInt(datosPrograma[2]), programa.getEstado(), Integer.parseInt(datosPrograma[4])));
            String fileContent = "" + datosPrograma[0] + ","  + datosPrograma[1] + "," + datosPrograma[2] + ","  + programa.getEstado() + "," + datosPrograma[4] + "\r\n";
            myWriter.write(fileContent);
        }
        return listaProgramasPrevios = FXCollections.observableList(lista);
    }
    
    
}
