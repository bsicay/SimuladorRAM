/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.brandonsicay.sistema;

import java.io.InputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.brandonsicay.controller.ProgramaController;
import org.brandonsicay.controller.ProgramaPrincipalController;

/**
 *
 * @author brand
 */

public class Principal extends Application {
    private final String PAQUETE_VISTA = "/org/brandonsicay/view/";
    private Stage escenarioPrincipal;
    private Scene escena; 
    Path p1 = Paths.get("D:\\Cursos\\Java\\SimuladorRAM\\src\\org\\brandonsicay\\archivos\\programasIniciales.txt");   
    File archivoPrograma = new File("D:\\Cursos\\Java\\SimuladorRAM\\src\\org\\brandonsicay\\archivos\\programasIniciales.txt");
    File archivoMemoria = new File("D:\\Cursos\\Java\\SimuladorRAM\\src\\org\\brandonsicay\\archivos\\datosMemoria.txt");
    @Override
    public void start(Stage escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
        escenarioPrincipal.setTitle("RAM");
        escenarioPrincipal.getIcons().add(new Image("/org/brandonsicay/images/ram.png"));
        iniciar();
        escenarioPrincipal.show();
        
       
    }
    
    public void iniciar(){
        try{
            ProgramaController iniciar = (ProgramaController)cambiarEscena("inicialesView.fxml", 794, 629);
            iniciar.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    
    }
    
    public void programaPrincipal(){
        try{
            ProgramaPrincipalController principal = (ProgramaPrincipalController)cambiarEscena("programaPrincipalView.fxml", 790, 876);
            principal.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    
    }
    
    
    
    public Initializable cambiarEscena(String fxml, int ancho, int alto) throws Exception{
        Initializable resultado = null; 
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA+fxml); 
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA+fxml));  
        escena = new Scene((AnchorPane)cargadorFXML.load(archivo), ancho, alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable)cargadorFXML.getController();
        return resultado; 
            
    }  
    
    @Override
    public void stop(){
        try{
            archivoPrograma.deleteOnExit();
            archivoMemoria.deleteOnExit();
            //Files.delete(p1);
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Stage is closing");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }
    
}
