/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.brandonsicay.controller;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javax.swing.JOptionPane;
import org.brandonsicay.bean.DDR;
import org.brandonsicay.bean.Programa;
import org.brandonsicay.bean.SDR;
import org.brandonsicay.sistema.Principal;

/**
 *
 * @author brand
 */

public class ProgramaController implements Initializable{
    private Principal escenarioPrincipal;
    private Programa miPrograma;
    private DDR memoryDDR;
    private SDR memorySDR;
    private String listaMemoria[] = null;
    private String path = "/org/brandonsicay/archivos";
    private int bloquesMemoria = 0;
    private int bloquesUtilizados = 0;
    private int bloquesDisponibles;
    private int contador = 0;
    private int bloquesPrograma;
    @FXML private TextField txtNombre; 
    @FXML private TextField txtCantidad; 
    @FXML private TextField txtTiempo; 
    @FXML private ComboBox cmbMemoria;
    @FXML private ComboBox cmbTamanio;
    @FXML private Button btnGuardar;
    @FXML private Button btnIniciar;
   

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbMemoria.getItems().addAll("DDR", "SDR");
        cmbTamanio.getItems().addAll("4", "8", "12", "16", "32", "64");
    }
    
    public void soloLetras(KeyEvent event){
       try{
           char tecla = event.getCharacter().charAt(0);
           if(!(Character.isLetter(tecla) || Character.isSpaceChar(tecla))){
               event.consume();
           }
       }catch(Exception e){
           e.printStackTrace();
       }
   
    }
    
    public void soloNumeros(KeyEvent event){
       try{
           char tecla = event.getCharacter().charAt(0);
           if(!(Character.isDigit(tecla))){
               event.consume();
           }
       }catch(Exception e){
           e.printStackTrace();
       }
   
    }
    
    public void nuevo(){
        try {
            String jarPath = ProgramaController.class
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI()
                    .getPath();
            System.out.println(jarPath);
        } catch (URISyntaxException ex) {
            Logger.getLogger(ProgramaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(txtNombre.getText().isEmpty() || txtCantidad.getText().isEmpty() || txtTiempo.getText().isEmpty()|| cmbMemoria.getSelectionModel().isEmpty() || cmbTamanio.getSelectionModel().isEmpty())
            JOptionPane.showMessageDialog(null, "UNO O MÁS CAMPOS ESTÁN VACIOS!!!");
        else{
            cmbMemoria.setDisable(true);
            cmbTamanio.setDisable(true);
            guardar();
        }
    }
    
    public void guardar(){
        
        
        //URL  archivoPrograma = ProgramaController.class.getResource(path+"programasPrevios.txt"); 
        Programa registro = new Programa();
        SDR memoriaSDR = new SDR();
        DDR memoriaDDR = new DDR();
        File archivoPrograma = new File("D:\\Cursos\\Java\\SimuladorRAM\\src\\org\\brandonsicay\\archivos\\programasIniciales.txt");
        File archivoMemoria = new File("D:\\Cursos\\Java\\SimuladorRAM\\src\\org\\brandonsicay\\archivos\\datosMemoria.txt");
       // File archivoProgramaDos = new File("C:\\Parking");
        System.out.println(System.getProperty("user.dir"));
        registro.setNombre(txtNombre.getText());
        registro.setCantidadMemoria(Integer.parseInt(txtCantidad.getText()));
        registro.setTiempoEjecucion(Integer.parseInt(txtTiempo.getText()));
       
        
        if(cmbMemoria.getSelectionModel().getSelectedIndex() == 0 && contador == 0){
            memoriaDDR.setMemoriaTotal(Integer.parseInt(cmbTamanio.getValue().toString()));
            memoriaDDR.setMemoriaDisponible(Integer.parseInt(cmbTamanio.getValue().toString()));
            bloquesMemoria = (memoriaDDR.getMemoriaTotal()*1024)/64;
            bloquesDisponibles = bloquesMemoria;
            contador++;
        }else if(cmbMemoria.getSelectionModel().getSelectedIndex() == 1 && contador == 0){
            memoriaSDR.setMemoriaTotal(Integer.parseInt(cmbTamanio.getValue().toString()));
            memoriaSDR.setMemoriaDisponible(Integer.parseInt(cmbTamanio.getValue().toString()));
            memoriaSDR.setTipo(cmbMemoria.getSelectionModel().getSelectedItem().toString());
            bloquesMemoria = (memoriaSDR.getMemoriaTotal()*1024)/64;
            memoriaSDR.setBloques(bloquesMemoria);
            bloquesDisponibles = bloquesMemoria;
            contador++;
            
        }
        bloquesUtilizados =  (int)Math.ceil(registro.getCantidadMemoria()/64);
        bloquesDisponibles = Math.round(bloquesDisponibles - (int)bloquesUtilizados);
        registro.setBloquesUtilizados((int)bloquesUtilizados);
        bloquesPrograma = bloquesPrograma + bloquesUtilizados;
        if(bloquesPrograma > bloquesMemoria)
            registro.setEstado("En cola");
        else{
            registro.setEstado("En espera");
        }
        
        
        try{
            
            if (!archivoPrograma.exists()){
                try {
                archivoPrograma.createNewFile();
                miPrograma = new Programa(registro.getNombre(), registro.getCantidadMemoria(), registro.getTiempoEjecucion(), registro.getEstado(), registro.getBloquesUtilizados());
                Archivo datosPersistentes = new Archivo(archivoPrograma, miPrograma);
                try {
                    datosPersistentes.GuardarInformacionPrograma();
                    System.out.println("Información guardada exitosamente");
                } catch(Exception e) {
                    System.out.println("Error al guardar la información");
                }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("No se pudo crear el archivo, intente más tarde");
                }
                
                if (!archivoMemoria.exists()){ 
                    try {
                    archivoMemoria.createNewFile();
                    if(cmbMemoria.getSelectionModel().getSelectedIndex() == 0){
                        memoryDDR = new DDR("DDR", memoriaDDR.getMemoriaTotal(), memoriaDDR.getMemoriaDisponible());
                        Archivo datosPersistentes = new Archivo(archivoMemoria, memoryDDR);
                         try {
                            datosPersistentes.GuardarInformacionDDR();
                            System.out.println("Información guardada exitosamente");
                        } catch(Exception e) {
                            System.out.println("Error al guardar la información");
                        }
                        
                    }else if(cmbMemoria.getSelectionModel().getSelectedIndex() == 1){
                        memorySDR = new SDR("SDR", memoriaSDR.getMemoriaTotal(), memoriaSDR.getMemoriaDisponible(), bloquesMemoria);
                        Archivo datosPersistentes = new Archivo(archivoMemoria, memorySDR);
                         try {
                            datosPersistentes.GuardarInformacionSDR();
                            System.out.println("Información guardada exitosamente");
                        } catch(Exception e) {
                            System.out.println("Error al guardar la información");
                        }

                    }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("No se pudo crear el archivo, intente más tarde");
                    }
                    
                }
                
            }else{
                if (!archivoPrograma.canRead()) {
                    System.out.println("No se pudo leer el archivo, intente más tarde");
                }else{
                    miPrograma = new Programa(registro.getNombre(), registro.getCantidadMemoria(), registro.getTiempoEjecucion(), registro.getEstado(), registro.getBloquesUtilizados());
                    Archivo datos = new Archivo(archivoPrograma, miPrograma);
                    try {
                        datos.GuardarInformacionPrograma();
                        System.out.println("Información guardada exitosamente");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    
                    if (!archivoMemoria.exists()){
                        try {
                            archivoMemoria.createNewFile();
                            if(cmbMemoria.getSelectionModel().getSelectedIndex() == 0){
                                memoryDDR = new DDR("DDR", memoriaDDR.getMemoriaTotal(), memoriaDDR.getMemoriaDisponible());
                                Archivo datosPersistentes = new Archivo(archivoMemoria, memoryDDR);
                                 try {
                                    datosPersistentes.GuardarInformacionDDR();
                                    System.out.println("Información guardada exitosamente");
                                } catch(Exception e) {
                                    System.out.println("Error al guardar la información");
                                }

                            }else if(cmbMemoria.getSelectionModel().getSelectedIndex() == 1){
                                memorySDR = new SDR("SDR", memoriaSDR.getMemoriaTotal(), memoriaSDR.getMemoriaDisponible(), bloquesMemoria);
                                Archivo datosPersistentes = new Archivo(archivoMemoria, memorySDR);
                                 try {
                                    datosPersistentes.GuardarInformacionSDR();
                                    System.out.println("Información guardada exitosamente");
                                } catch(Exception e) {
                                    System.out.println("Error al guardar la información");
                                }

                            }
                        } catch (Exception e) {
                                e.printStackTrace();
                                System.out.println("No se pudo crear el archivo, intente más tarde");
                            }
                    }
                     
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public String[] getDatosMemoria(){
        File archivoMemoria = new File("D:\\Cursos\\Java\\SimuladorRAM\\src\\org\\brandonsicay\\archivos\\datosMemoria.txt");
        Archivo datos = new Archivo(archivoMemoria);
        try {
            listaMemoria = datos.getDatosMemoria(archivoMemoria);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return listaMemoria;
    }
    
    public void principal(){
        File archivoPrograma = new File("D:\\Cursos\\Java\\SimuladorRAM\\src\\org\\brandonsicay\\archivos\\programasIniciales.txt");
        File archivoMemoria = new File("D:\\Cursos\\Java\\SimuladorRAM\\src\\org\\brandonsicay\\archivos\\datosMemoria.txt");
        if (!archivoPrograma.canRead() || !archivoMemoria.canRead()) {
            JOptionPane.showMessageDialog(null, "DEBE DE INGRESAR DATOS");
        }else{
            escenarioPrincipal.programaPrincipal();
        }
        
    }
    
    
    public Principal getEscenarioPrincipal() {
        
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
}
