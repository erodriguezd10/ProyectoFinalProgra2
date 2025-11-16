/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.panels;

/**
 *
 * @author GRUPO_PROG_2_C_1_ 4
 */
import models.HistorialMedico;
import models.Vacuna;
import services.HistorialMedicoService;
import services.MascotaService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.List;

//clase principal del panel de historial médico
public class HistorialPanel extends JPanel {
    private HistorialMedicoService historialService;
    private MascotaService mascotaService;
    private JTable historialTable, vacunasTable, diagnosticosTable;
    private DefaultTableModel historialModel, vacunasModel, diagnosticosModel;
    private JTextField txtIdMascota, txtDiagnostico, txtTratamiento, txtResultadoExamen, txtTipoVacuna;
    private JTextArea txtObservaciones;
    private JButton btnCrearHistorial, btnBuscarHistorial, btnAgregarDiagnostico, btnAgregarTratamiento;
    private JButton btnAgregarResultado, btnAgregarVacuna, btnLimpiar;
    
    public HistorialPanel() {
        historialService = new HistorialMedicoService();
        mascotaService = new MascotaService();
        initializeUI();
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        //paanel de la izquierda(búsqueda y formularios)
        add(createLeftPanel(), BorderLayout.WEST);
        
        //panel central(tablas de información)
        add(createCenterPanel(), BorderLayout.CENTER);
    }
    
    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(400, 700));
        
        //panel para la búsqueda
        leftPanel.add(createSearchPanel());
        leftPanel.add(Box.createVerticalStrut(10));
        
        //panel de diagnósticos
        leftPanel.add(createDiagnosticosPanel());
        leftPanel.add(Box.createVerticalStrut(10));
        
        //Panel de lostratamientos
        leftPanel.add(createTratamientosPanel());
        leftPanel.add(Box.createVerticalStrut(10));
        
        //panel de resultados de exámenes
        leftPanel.add(createExamenesPanel());
        leftPanel.add(Box.createVerticalStrut(10));
        
        //panel de las vacunas
        leftPanel.add(createVacunasPanel());
        
        return leftPanel;
    }
    
    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Buscar historial por mascota"));
        
        searchPanel.add(createLabel("ID de la mascota:"));
        txtIdMascota = new JTextField();
        searchPanel.add(txtIdMascota);
        
        btnCrearHistorial = createStyledButton("Crear historial", new Color(46, 125, 50));
        btnCrearHistorial.addActionListener(this::crearHistorial);
        searchPanel.add(btnCrearHistorial);
        
        btnBuscarHistorial = createStyledButton("Buscar historial", new Color(25, 118, 210));
        btnBuscarHistorial.addActionListener(this::buscarHistorial);
        searchPanel.add(btnBuscarHistorial);
        
        btnLimpiar = createStyledButton("Limpiar", new Color(97, 97, 97));
        btnLimpiar.addActionListener(e -> limpiarCampos());
        searchPanel.add(btnLimpiar);
        
        //botón para la carga de todas las mascotas
        JButton btnCargarMascotas = createStyledButton("Ver modas las mascotas", new Color(123, 31, 162));
        btnCargarMascotas.addActionListener(this::cargarTodasMascotas);
        searchPanel.add(btnCargarMascotas);
        
        return searchPanel;
    }

    //botones y campos de texto para los diagnósticos
    private JPanel createDiagnosticosPanel() {
        JPanel diagnosticosPanel = new JPanel(new BorderLayout(5, 5));
        diagnosticosPanel.setBorder(BorderFactory.createTitledBorder("Agregar diagnóstico"));
        
        JPanel formPanel = new JPanel(new BorderLayout(5, 5));
        formPanel.add(createLabel("Diagnóstico:"), BorderLayout.NORTH);
        txtDiagnostico = new JTextField();
        formPanel.add(txtDiagnostico, BorderLayout.CENTER);
        
        btnAgregarDiagnostico = createStyledButton("Agregar diagnóstico", new Color(46, 125, 50));
        btnAgregarDiagnostico.addActionListener(this::agregarDiagnostico);
        formPanel.add(btnAgregarDiagnostico, BorderLayout.SOUTH);
        
        diagnosticosPanel.add(formPanel, BorderLayout.CENTER);
        return diagnosticosPanel;
    }
    
    //botones y campos de texto para los tratamientos
    private JPanel createTratamientosPanel() {
        JPanel tratamientosPanel = new JPanel(new BorderLayout(5, 5));
        tratamientosPanel.setBorder(BorderFactory.createTitledBorder("Agregar tratamiento"));
        
        JPanel formPanel = new JPanel(new BorderLayout(5, 5));
        formPanel.add(createLabel("Tratamiento:"), BorderLayout.NORTH);
        txtTratamiento = new JTextField();
        formPanel.add(txtTratamiento, BorderLayout.CENTER);
        
        btnAgregarTratamiento = createStyledButton("Agregar tratamiento", new Color(255, 152, 0));
        btnAgregarTratamiento.addActionListener(this::agregarTratamiento);
        formPanel.add(btnAgregarTratamiento, BorderLayout.SOUTH);
        
        tratamientosPanel.add(formPanel, BorderLayout.CENTER);
        return tratamientosPanel;
    }
    
    //botones y campos de texto para los resultados de exámenes
    private JPanel createExamenesPanel() {
        JPanel examenesPanel = new JPanel(new BorderLayout(5, 5));
        examenesPanel.setBorder(BorderFactory.createTitledBorder("Resultados de exámenes"));
        
        JPanel formPanel = new JPanel(new BorderLayout(5, 5));
        formPanel.add(createLabel("Resultado:"), BorderLayout.NORTH);
        txtResultadoExamen = new JTextField();
        formPanel.add(txtResultadoExamen, BorderLayout.CENTER);
        
        btnAgregarResultado = createStyledButton("Agregar resultado", new Color(0, 150, 136));
        btnAgregarResultado.addActionListener(this::agregarResultadoExamen);
        formPanel.add(btnAgregarResultado, BorderLayout.SOUTH);
        
        examenesPanel.add(formPanel, BorderLayout.CENTER);
        return examenesPanel;
    }
    
    //botones y campos de texto para las vacunas
    private JPanel createVacunasPanel() {
        JPanel vacunasPanel = new JPanel(new BorderLayout(5, 5));
        vacunasPanel.setBorder(BorderFactory.createTitledBorder("Registrar vacuna"));
        
        JPanel formPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        formPanel.add(createLabel("Descripción de vacuna:"));
        txtTipoVacuna = new JTextField();
        formPanel.add(txtTipoVacuna);
        
        btnAgregarVacuna = createStyledButton("Agregar vacuna", new Color(156, 39, 176));
        btnAgregarVacuna.addActionListener(this::agregarVacuna);
        formPanel.add(btnAgregarVacuna);
        
        vacunasPanel.add(formPanel, BorderLayout.CENTER);
        return vacunasPanel;
    }
    

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        
        //tabla de toda la información general del historial
        centerPanel.add(createHistorialTablePanel());
        
        //tabla de diagnósticos
        centerPanel.add(createDiagnosticosTablePanel());
        
        //tabla para vacunas
        centerPanel.add(createVacunasTablePanel());
        
        return centerPanel;
    }
    
    //tabla de información general del historial
    private JPanel createHistorialTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Información de el historial"));
        
        String[] columnNames = {"Campo", "Descripción"};
        historialModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        historialTable = new JTable(historialModel);
        historialTable.setRowHeight(25);
        historialTable.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(historialTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    //tabla de diagnósticos y tratamientos
    private JPanel createDiagnosticosTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Diagnósticos y tratamientos"));
        
        String[] columnNames = {"Tipo", "Descripción", "Fecha"};
        diagnosticosModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        diagnosticosTable = new JTable(diagnosticosModel);
        diagnosticosTable.setRowHeight(25);
        diagnosticosTable.setFont(new Font("Arial", Font.PLAIN, 11));
        
        JScrollPane scrollPane = new JScrollPane(diagnosticosTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    //tabla de vacunas aplicadas
    private JPanel createVacunasTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Vacunas aplicadas"));
        
        String[] columnNames = {"Tipo de Vacuna", "Fecha de Aplicación"};
        vacunasModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        vacunasTable = new JTable(vacunasModel);
        vacunasTable.setRowHeight(25);
        vacunasTable.setFont(new Font("Arial", Font.PLAIN, 11));
        
        JScrollPane scrollPane = new JScrollPane(vacunasTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        return label;
    }
    
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        button.setFont(new Font("Arial", Font.BOLD, 11));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }
    
    private void crearHistorial(ActionEvent e) {
        String idMascota = txtIdMascota.getText().trim();
        
        if (idMascota.isEmpty()) {
            showError("Campo requerido", "Ingrese el ID de la mascota");
            return;
        }
        
        try {
            var mascota = mascotaService.obtenerMascota(idMascota);
            if (mascota == null) {
                showError("Mascota no encontrada", 
                    "No se encontró una mascota con ID: " + idMascota + 
                    "\nVerifique el ID e intente una vez más.");
                return;
            }
            
            HistorialMedico historial = new HistorialMedico(idMascota);
            String resultado = historialService.crearHistorialMedico(historial);
            
            showSuccess("Historial creado", 
                "<html><b>Historial médico creado exitosamente</b><br>" +
                "Para mascota: " + idMascota + "<br>" +
                "Nombre: " + mascota.getNombre() + "</html>");
            
                //historial en tiempo real para mayor practicidad
            buscarHistorial(e);
            
        } catch (Exception ex) {
            showError("Error al crear historial", ex.getMessage());
        }
    }
    
    private void buscarHistorial(ActionEvent e) {
        String idMascota = txtIdMascota.getText().trim();
        
        if (idMascota.isEmpty()) {
            showError("Campo requerido", "Ingrese el ID de la mascota");
            return;
        }
        
        try {
            HistorialMedico historial = historialService.obtenerHistorialMedico(idMascota);
            
            if (historial != null) {
                mostrarInformacionHistorial(historial, idMascota);
            } else {
                limpiarTablas();
                showWarning("Historial no encontrado", 
                    "No se encontró historial para la mascota: " + idMascota +
                    "\nPuede crear un nuevo historial usando el botón 'Crear historial'");
            }
            
        } catch (Exception ex) {
            showError("Error al buscar historial", ex.getMessage());
        }
    }
    
    //método para cargar todas las mascotas registradas en el sistema
    private void cargarTodasMascotas(ActionEvent e) {
        try {
            var mascotas = mascotaService.obtenerTodasMascotas();
            
            if (mascotas.isEmpty()) {
                showInfo("¡Sin mascotas!", "No hay mascotas registradas en el sistema");
                return;
            }
            
            StringBuilder mensaje = new StringBuilder();
            mensaje.append("<html><b>Mascotas Registradas:</b><br><br>");
            
            for (var mascota : mascotas) {
                mensaje.append(" <b>").append(mascota.getNombre())
                      .append("</b> - ID: ").append(mascota.getId())
                      .append(" - Cliente: ").append(mascota.getDpiCliente())
                      .append("<br>");
            }
            mensaje.append("</html>");
            
            JOptionPane.showMessageDialog(this, mensaje.toString(), 
                "Lista de todas las mascotas", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception ex) {
            showError("Error al cargar las mascotas", ex.getMessage());
        }
    }
    
    //métodos para agregar diagnósticos, tratamientos, resultados de exámenes y vacunas
    private void agregarDiagnostico(ActionEvent e) {
        String idMascota = txtIdMascota.getText().trim();
        String diagnostico = txtDiagnostico.getText().trim();
        
        if (idMascota.isEmpty() || diagnostico.isEmpty()) {
            showError("Campos requeridos", "ID de mascota y diagnóstico son obligatorios");
            return;
        }
        
        try {
            String resultado = historialService.agregarDiagnostico(idMascota, diagnostico);
            showSuccess("Diagnóstico agregado", resultado);
            txtDiagnostico.setText("");
            //actualización de la vista
            buscarHistorial(e); 
            
        } catch (Exception ex) {
            showError("Error al agregar diagnóstico", ex.getMessage());
        }
    }
    
    //método para agregar tratamientos
    private void agregarTratamiento(ActionEvent e) {
        String idMascota = txtIdMascota.getText().trim();
        String tratamiento = txtTratamiento.getText().trim();
        
        if (idMascota.isEmpty() || tratamiento.isEmpty()) {
            showError("Campos requeridos", "ID de mascota y tratamiento son obligatorios");
            return;
        }
        
        try {
            String resultado = historialService.agregarTratamiento(idMascota, tratamiento);
            showSuccess("Tratamiento agregado", resultado);
            txtTratamiento.setText("");
            //actualizar la vista
            buscarHistorial(e); 
            
        } catch (Exception ex) {
            showError("Error al agregar tratamiento", ex.getMessage());
        }
    }
    
    //método para agregar resultados de exámenes
    private void agregarResultadoExamen(ActionEvent e) {
        String idMascota = txtIdMascota.getText().trim();
        String resultadoExamen = txtResultadoExamen.getText().trim();
        
        if (idMascota.isEmpty() || resultadoExamen.isEmpty()) {
            showError("Campos requeridos", "ID de mascota y resultado son obligatorios");
            return;
        }
        
        try {
            String resultado = historialService.agregarResultadoExamen(idMascota, resultadoExamen);
            showSuccess("Resultado agregado", resultado);
            txtResultadoExamen.setText("");
            buscarHistorial(e);
            
        } catch (Exception ex) {
            showError("Error al agregar resultado", ex.getMessage());
        }
    }
    
    //método para agregar vacunas
    private void agregarVacuna(ActionEvent e) {
        String idMascota = txtIdMascota.getText().trim();
        String tipoVacuna = txtTipoVacuna.getText().trim();
        
        if (idMascota.isEmpty() || tipoVacuna.isEmpty()) {
            showError("Campos requeridos", "ID de mascota y tipo de vacuna son obligatorios");
            return;
        }
        
        try {
            Vacuna vacuna = new Vacuna(new Date(), tipoVacuna);
            String resultado = historialService.agregarVacuna(idMascota, vacuna);
            showSuccess("Vacuna agregada", resultado);
            txtTipoVacuna.setText("");
            buscarHistorial(e);
            
        } catch (Exception ex) {
            showError("Error al agregar vacuna", ex.getMessage());
        }
    }
    
    private void mostrarInformacionHistorial(HistorialMedico historial, String idMascota) {
        //limpieza de tablas
        historialModel.setRowCount(0);
        diagnosticosModel.setRowCount(0);
        vacunasModel.setRowCount(0);
        
        try {
            //obtener información de la mascota
            var mascota = mascotaService.obtenerMascota(idMascota);
            String nombreMascota = mascota != null ? mascota.getNombre() : "No encontrada";
            
            //información general del historial
            historialModel.addRow(new Object[]{"ID Mascota", idMascota});
            historialModel.addRow(new Object[]{"Nombre mascota", nombreMascota});
            historialModel.addRow(new Object[]{"Total de diagnósticos", 
                historial.getDiagnosticosAnteriores().size()});
            historialModel.addRow(new Object[]{"Total de tratamientos", 
                historial.getTratamientosAnteriores().size()});
            historialModel.addRow(new Object[]{"Total de exámenes", 
                historial.getResultadosExamenesFisicos().size()});
            historialModel.addRow(new Object[]{"Total de vacunas", 
                historial.getVacunas().size()});
            
            //cargar de diagnósticos y tratamientos
            for (String diagnostico : historial.getDiagnosticosAnteriores()) {
                diagnosticosModel.addRow(new Object[]{"Diagnóstico", diagnostico, new Date()});
            }
            
            for (String tratamiento : historial.getTratamientosAnteriores()) {
                diagnosticosModel.addRow(new Object[]{"Tratamiento", tratamiento, new Date()});
            }
            
            for (String resultado : historial.getResultadosExamenesFisicos()) {
                diagnosticosModel.addRow(new Object[]{"Examen", resultado, new Date()});
            }
            
            //cargar de las vacunas
            for (Vacuna vacuna : historial.getVacunas()) {
                vacunasModel.addRow(new Object[]{
                    vacuna.getTipoVacuna(),
                    vacuna.getFechaAdministracion()
                });
            }
            
        } catch (Exception ex) {
            showError("Error en la carga de la información", ex.getMessage());
        }
    }
    
    //método para limpiar los campos de texto y las tablas
    private void limpiarCampos() {
        txtIdMascota.setText("");
        txtDiagnostico.setText("");
        txtTratamiento.setText("");
        txtResultadoExamen.setText("");
        txtTipoVacuna.setText("");
        limpiarTablas();
    }
    
    private void limpiarTablas() {
        historialModel.setRowCount(0);
        diagnosticosModel.setRowCount(0);
        vacunasModel.setRowCount(0);
    }
    
    //métodos auxiliares para mostrar mensajes de error, éxito, advertencia e información
    private void showError(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }
    
    private void showSuccess(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showWarning(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
    }
    
    private void showInfo(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}