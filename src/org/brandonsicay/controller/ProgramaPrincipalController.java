/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.brandonsicay.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
public class ProgramaPrincipalController implements Initializable{
    private Principal escenarioPrincipal;
    private DDR memoryDDR;
    private SDR memorySDR;
    private ObservableList<Programa> listaProgramasPrevios = null;
    private ArrayList<String> datosProgramas;
    private ArrayList<String> listaDatosMemoria;
    private String listaMemoria[] = null;
    private int totalUsado;
    private int tiempoRestante;
    private int memoriaDispinible;
    private int cantidadMemoria;
    private int memoriaEnUso;
    private int nuevoDatoMemoria;
    private double valorBarra;
    private int tiempoEjecucion;
    private int bloquesDisponibles;
    private int memoriaUtilizadaActual;
    private String estadoPrograma;
    private Programa miPrograma;
    File archivo = new File("D:\\Cursos\\Java\\SimuladorRAM\\src\\org\\brandonsicay\\archivos\\programasIniciales.txt");
    @FXML private TextField txtNombre; 
    @FXML private TextField txtCantidad; 
    @FXML private TextField txtTiempo; 
    @FXML private Button btnAgregar;
    @FXML private Button btnIniciar;
    @FXML private TableView tblProgramas;
    @FXML private TableView tblProgramasFinalizados;
    @FXML private TableColumn colPrograma;
    @FXML private TableColumn colEspacio;
    @FXML private TableColumn colBloques;
    @FXML private TableColumn colEstado;
    @FXML private TableColumn colPrograma1;
    @FXML private TableColumn colEspacio1;
    @FXML private TableColumn colBloques1;
    @FXML private TableColumn colEstado1;
    @FXML private Label lblCantidad;
    @FXML private Label lblEnUso;
    @FXML private Label lblDisponible;
     @FXML private Label lblPorcentaje;
    @FXML private ProgressBar barUno;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnIniciar.setOnAction((event) -> { iniciarProcesos();
        });
        cargarDatos();
        actualizarDatosIniciales();
    }
    
    public void actualizarDatosIniciales(){
        obtenerMemoriaEnUso();
        cantidadMemoria = Integer.parseInt(getDatosMemoria()[1]) * 1024;
        lblCantidad.setText(Integer.toString(cantidadMemoria )+ " MB");
        if (memoriaEnUso > cantidadMemoria){
            lblEnUso.setText(Integer.toString(cantidadMemoria) + " MB");
        }else{
            lblEnUso.setText(Integer.toString(memoriaEnUso)+ " MB");
        }
        memoriaDispinible = cantidadMemoria - memoriaEnUso;
        if (memoriaDispinible > 0 ){
            lblDisponible.setText(Integer.toString(memoriaDispinible) + " MB");
        }else{
            memoriaDispinible = 0;
            lblDisponible.setText("0" + " MB");
        }
        bloquesDisponibles = (cantidadMemoria/64)-(memoriaEnUso/64);
    }
    
    public void cargarDatos(){
        tblProgramas.setItems(getProgramasPrevios());
        colPrograma.setCellValueFactory(new PropertyValueFactory<Programa, String>("nombre"));
        colEspacio.setCellValueFactory(new PropertyValueFactory<Programa, Integer>("cantidadMemoria"));
        colEstado.setCellValueFactory(new PropertyValueFactory<Programa, String>("Estado"));
        colBloques.setCellValueFactory(new PropertyValueFactory<Programa, Integer>("bloquesUtilizados"));
        
    }
   
    public void nuevo(){
        if(txtNombre.getText().isEmpty() || txtCantidad.getText().isEmpty() || txtTiempo.getText().isEmpty())
            JOptionPane.showMessageDialog(null, "UNO O MÁS CAMPOS ESTÁN VACIOS!!!");
        else{
            guardar();
        }
    }
    
    public void guardar(){
        Programa registro = new Programa();
        File archivoPrograma = new File("D:\\Cursos\\Java\\SimuladorRAM\\src\\org\\brandonsicay\\archivos\\programasIniciales.txt");
        registro.setNombre(txtNombre.getText());
        registro.setCantidadMemoria(Integer.parseInt(txtCantidad.getText()));
        registro.setTiempoEjecucion(Integer.parseInt(txtTiempo.getText()));
        int bloquesUtilizados =  (int)Math.ceil(registro.getCantidadMemoria()/64);
        registro.setBloquesUtilizados((int)bloquesUtilizados);
        if(bloquesUtilizados > (memoriaDispinible/64))
            registro.setEstado("En cola");
        else{
            registro.setEstado("En espera");
            memoriaEnUso = memoriaEnUso + Integer.parseInt(txtCantidad.getText());
            System.out.println(memoriaEnUso);
        }
        
        try {
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
            }else{
                if (!archivoPrograma.canRead()) {
                    System.out.println("No se pudo leer el archivo, intente más tarde");
                }else{
                    miPrograma = new Programa(registro.getNombre(), registro.getCantidadMemoria(), registro.getTiempoEjecucion(), registro.getEstado(), registro.getBloquesUtilizados());
                    Archivo datos = new Archivo(archivoPrograma, miPrograma);
                    try {
                        datos.GuardarInformacionProgramaInicializado();
                        listaProgramasPrevios = datos.getProgramasPrevios(archivoPrograma);
                        tblProgramas.setItems(listaProgramasPrevios);
                        System.out.println("Información guardada exitosamente");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        actualizarDatosMemoria();
    }
   
    
    public ObservableList<Programa> getProgramasPrevios(){
        File archivoPrograma = new File("D:\\Cursos\\Java\\SimuladorRAM\\src\\org\\brandonsicay\\archivos\\programasIniciales.txt");
        Archivo datos = new Archivo(archivoPrograma);
        try {
            listaProgramasPrevios = datos.getProgramasPrevios(archivoPrograma);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return listaProgramasPrevios;
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
    
    public ArrayList<String> getDatosProgramas(){
        File archivoMemoria = new File("D:\\Cursos\\Java\\SimuladorRAM\\src\\org\\brandonsicay\\archivos\\programasIniciales.txt");
        Archivo datos = new Archivo(archivoMemoria);
        try {
            datosProgramas = datos.getProgramas(archivoMemoria);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datosProgramas;
    }
    
    public int getDatosPrograma(){
        File archivoMemoria = new File("D:\\Cursos\\Java\\SimuladorRAM\\src\\org\\brandonsicay\\archivos\\programasIniciales.txt");
        Archivo datos = new Archivo(archivoMemoria);
        try {
            totalUsado = datos.getDatosPrograma(archivoMemoria);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalUsado;
    }
    
    public void obtenerMemoriaEnUso(){
        int m = 1;
        int j = 0;
        int i = 2;
        int l = 3;
        while(j<getDatosProgramas().size()/5){
            obtenerTiempoEjecucion(i, l, m);
            if(estadoPrograma.equals("En espera")){
                memoriaEnUso = memoriaEnUso + memoriaUtilizadaActual;
             }
            j++;
            m = m+5;
            i = i+5;
            l = l+5;
        }
        System.out.println(memoriaEnUso);
    }
    
    private void iniciarProcesos(){ //Crea un nuevo hilo
        Task tarea = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Programa registro = new Programa();
                int estado = 0;
                int j = 0;
                int i = 2;
                int k = 1;
                int l = 3;
                int m = 1;
                while(j<getDatosProgramas().size()/5){ //recorre todas las filas del archivo
                    btnIniciar.setDisable(true);
                    btnAgregar.setDisable(true);
                    obtenerTiempoEjecucion(i, l, m);
                    boolean puedeContinuar = true;
                    if ((memoriaEnUso-memoriaUtilizadaActual)<0) {
                        memoriaEnUso = memoriaEnUso + memoriaUtilizadaActual;
                        if(memoriaEnUso > cantidadMemoria){
                            JOptionPane.showMessageDialog(null, "El programa excede la cantidad de memoria actual");
                            puedeContinuar = false;
                            memoriaEnUso = memoriaEnUso - memoriaUtilizadaActual;
                        }else{
                            memoriaEnUso = memoriaEnUso - memoriaUtilizadaActual;
                        }
                        
                    }else{
                        memoriaEnUso = memoriaEnUso - memoriaUtilizadaActual;
                    }
                    
                    bloquesDisponibles = (cantidadMemoria/64)-(memoriaEnUso/64);
                    if(bloquesDisponibles > (cantidadMemoria/64)){
                        bloquesDisponibles = (cantidadMemoria/64);
                    }
                    
                    if (estadoPrograma.equals("En cola")) {
                        if(bloquesDisponibles > 0 && puedeContinuar){
                            ejecutarProceso(registro, estado, k);
                            actualizarDatosMemoria();
                        }
                    }else{
                        ejecutarProceso(registro, estado, k);
                        actualizarDatosMemoria();
                    }
                    
                    if(estado<getDatosProgramas().size()/5)//obtener la cantidad de filas en el archivo programasIniciales
                        estado++;
                    if(i<getDatosProgramas().size()-3)
                        i = i+5;
                    if(k<getDatosProgramas().size()-4){
                        k = k+5;
                    }
                    l = l + 5;
                    m = m + 5;
                    j++;
                    }
                btnIniciar.setDisable(false);
                btnAgregar.setDisable(false);
                return null;
            }
        };
        Thread hilo = new Thread(tarea);
        hilo.setDaemon(true);
        hilo.start();
    
    }
    
    public void ejecutarProceso(Programa registro, int estado, int k){
        while(tiempoRestante != 0){
            tiempoRestante--;
            Barra(tiempoEjecucion, tiempoRestante);
            Dormir();
            registro.setEstado("En proceso");
            miPrograma = new Programa(registro.getEstado());
            Archivo datosPersistentes = new Archivo(archivo, miPrograma);
            try {
                listaProgramasPrevios = datosPersistentes.actualizarDatosPrograma(archivo, estado);
                tblProgramas.setItems(listaProgramasPrevios);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        registro.setEstado("Finalizado");
        miPrograma = new Programa(registro.getEstado());
        Archivo datosPersistentes = new Archivo(archivo, miPrograma);
        try {
            listaProgramasPrevios = datosPersistentes.actualizarDatosPrograma(archivo, estado);
            //tblProgramas.setItems(listaProgramasPrevios);
            tblProgramasFinalizados.setItems(listaProgramasPrevios);
            colPrograma1.setCellValueFactory(new PropertyValueFactory<Programa, String>("nombre"));
            colEspacio1.setCellValueFactory(new PropertyValueFactory<Programa, Integer>("cantidadMemoria"));
            colEstado1.setCellValueFactory(new PropertyValueFactory<Programa, String>("Estado"));
            colBloques1.setCellValueFactory(new PropertyValueFactory<Programa, Integer>("bloquesUtilizados"));
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Error al guardar la información");
        }
        
    }
        
    
    public void actualizarDatosMemoria(){
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                //nuevoDatoMemoria = nuevoDatoMemoria + Integer.parseInt(getDatosProgramas().get(indice));
                    //System.out.println(memoriaDispinible + " DISPONIBLE");
                    //System.out.println(indice + " Este es el indice");
                nuevoDatoMemoria = cantidadMemoria;
                nuevoDatoMemoria = nuevoDatoMemoria - memoriaEnUso;
                System.out.println(nuevoDatoMemoria + " DATO");
                //nuevoDatoMemoria = nuevoDatoMemoria + Integer.parseInt(getDatosProgramas().get(indice));
               // System.out.println(nuevoDatoMemoria + "LOL");
                if (nuevoDatoMemoria>cantidadMemoria) {
                    lblDisponible.setText(Integer.toString(cantidadMemoria )+ " MB");
                }else{
                    lblDisponible.setText(Integer.toString(nuevoDatoMemoria)+ " MB");
                }
                lblEnUso.setText(Integer.toString(memoriaEnUso)+ " MB");

            }
        });
    }
 
    
    public void obtenerDatosPrograma(int indice){
         memoriaUtilizadaActual = Integer.parseInt(getDatosProgramas().get(indice));
         
    }
    
    public void obtenerTiempoEjecucion(int indice, int indiceDos, int indiceTres){
        tiempoEjecucion = Integer.parseInt(getDatosProgramas().get(indice));
        tiempoRestante = Integer.parseInt(getDatosProgramas().get(indice));
        estadoPrograma = getDatosProgramas().get(indiceDos);
        memoriaUtilizadaActual = Integer.parseInt(getDatosProgramas().get(indiceTres));
    }
    

    
    public void Dormir(){
    try{
        Thread.sleep(100); //Dormir sistema
    }catch(InterruptedException e){
        e.printStackTrace();
    }

    }
    
     public void Barra(double rafaga, int residuo){ //Calcula porcentaje de la barra y su progreso
         Platform.runLater(new Runnable(){
            @Override
            public void run() {
                double valor = 1/rafaga;
                double porcentaje = 1-(valor*residuo);
                valorBarra = porcentaje;
                lblPorcentaje.setText(String.valueOf(Math.round(valorBarra*100)+"%"));
                barUno.setProgress(valorBarra);
                }
        });
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
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    
}
