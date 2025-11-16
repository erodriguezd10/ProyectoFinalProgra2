//v05_noviembre_15_todos
//Se finalizo el proyecto completando todos los requerimientos en las instrucciones dadas.
//Este proyecto fue concluído
package main;

import gui.MainWindow;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        // configurar para la interfaz gráfica
        setupModernLookAndFeel();

        // ejecución de la interfaz gráfica
        SwingUtilities.invokeLater(() -> {
            System.out.println("Iniciando el sistema.......");
            MainWindow mainWindow = new MainWindow();
            mainWindow.setVisible(true);
        });
    }

    private static void setupModernLookAndFeel() {
        try {
            // uso de FlatLaf (comodidad y estética)
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");

            // configuraciones estéticas
            UIManager.put("Button.arc", 10);
            UIManager.put("Component.arc", 8);
            UIManager.put("TextComponent.arc", 5);
            UIManager.put("ProgressBar.arc", 8);
            UIManager.put("ScrollBar.thumbArc", 8);
            UIManager.put("ScrollBar.thumbInsets", new java.awt.Insets(2, 2, 2, 2));

        } catch (Exception e) {
            System.err.println("No se pudo cargar FlatLaf, usando look and feel por defecto");

            // en caso de fallo, usar el look and feel del sistema operativo
            try {
                String systemLF = UIManager.getSystemLookAndFeelClassName();
                UIManager.setLookAndFeel(systemLF);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
// (3_3)