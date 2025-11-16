/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

/**
 *
 * @author GRUPO_PROG_2_C_1_ 4
 */
import gui.panels.*;
import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private JTabbedPane tabbedPane;
    
    public MainWindow() {
        initializeUI();
        setupWindow();
    }
    
    private void initializeUI() {
        setTitle("Sistema veterinaria");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        //centrar la ventana
        setLocationRelativeTo(null); 
        
        //crear pestañas con iconos
        tabbedPane = new JTabbedPane();
        
        //agregar paneles a las pestañas
        tabbedPane.addTab("Clientes ", new ClientesPanel());
        tabbedPane.addTab("Mascotas", new MascotasPanel());
        tabbedPane.addTab("Productos", new ProductosPanel());
        tabbedPane.addTab("Historial médico", new HistorialPanel());
        
        add(tabbedPane);
        
        //menú superior
        createMenuBar();
    }
    
    private void setupWindow() {
        //hacerlo visible
        setVisible(true);
    }
    
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        //menú de Archivo
        JMenu archivoMenu = new JMenu("Archivo");
        JMenuItem salirItem = new JMenuItem("Salir");
        salirItem.addActionListener(e -> System.exit(0));
        archivoMenu.add(salirItem);
        
        //menú para ver
        JMenu verMenu = new JMenu("Ver");
        JMenuItem actualizarItem = new JMenuItem("Actualizar Datos");
        actualizarItem.addActionListener(e -> actualizarTodosLosPaneles());
        verMenu.add(actualizarItem);
        
        //menú de Ayuda
        JMenu ayudaMenu = new JMenu("Ayuda");
        JMenuItem acercaItem = new JMenuItem("Acerca de");
        acercaItem.addActionListener(e -> showAboutDialog());
        ayudaMenu.add(acercaItem);
        
        menuBar.add(archivoMenu);
        menuBar.add(verMenu);
        menuBar.add(ayudaMenu);
        
        setJMenuBar(menuBar);
    }
    
    private void actualizarTodosLosPaneles() {
        //actualización de todos los paneles
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            Component component = tabbedPane.getComponentAt(i);
            if (component instanceof ClientesPanel) {
                ((ClientesPanel) component).cargarClientes();
            } else if (component instanceof MascotasPanel) {
                ((MascotasPanel) component).cargarMascotas();
            } else if (component instanceof ProductosPanel) {
                ((ProductosPanel) component).cargarProductos();
            }
        }
        JOptionPane.showMessageDialog(this, "Datos actualizados correctamente");
    }
    
    //método para mostrar el diálogo de acerca de
    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this,
            "<html><div style='text-align: center;'>" +
            "<h2>Sistema veterinaria firebase</h2>" +
            "<b>Versión Final(0.5)</b><br><br>" +
            "Desarrollado para:<br>" +
            "<b>Programación 2 - UMG</b><br><br>" +
            "Grupo: GRUPO_PROG_2_C_1_4<br>" +
            "Tecnologías: Java, Firebase, Swing<br>" +
            "2025" +
            "</div></html>",
            "Acerca del sistema",
            JOptionPane.INFORMATION_MESSAGE);
    }
}