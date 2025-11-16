/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package gui.panels;

/**
 *
 * @author GRUPO_PROG_2_C_1_4
 */
import models.Mascota;
import services.MascotaService;
import services.ClienteService;
import utils.GeneradorID;
import javax.swing.border.Border;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.HierarchyEvent;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import utils.Validaciones;

//panel para gestionar a las mascotas
public class MascotasPanel extends JPanel {
    private MascotaService mascotaService;
    private ClienteService clienteService;
    private JTable mascotasTable;
    private DefaultTableModel tableModel;
    private JTextField txtNombre, txtEspecie, txtColor;
    private JComboBox<String> cmbTipoAnimal, cmbSexo, cmbCliente;
    private JSpinner spnEdad;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnLimpiar, btnBuscarPorCliente;
    
    public MascotasPanel() {
        mascotaService = new MascotaService();
        clienteService = new ClienteService();
        initializeUI();
        cargarMascotas();
        cargarClientesComboBox();
        
        //agregar listener para actualizar automáticamente cuando el panel se muestra evitando recargas innecesarias
        this.addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) {
                if (this.isShowing()) {
                    //se ejecutara automáticamente cuando el panel se muestra
                    cargarClientesComboBox();
                }
            }
        });
    }
    
     //método público para refrescar la lista de clientes
    public void refrescarClientes() {
        cargarClientesComboBox();
    }
    
    //método público para refrescar todos los datos del panel
    public void refrescarDatos() {
        cargarClientesComboBox();
        cargarMascotas();
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        //panel del formulario(arriba)
        add(createFormPanel(), BorderLayout.NORTH);
        
        //tabla de mascotas(centro)
        add(createTablePanel(), BorderLayout.CENTER);
        
        //botones de acción(abajo)
        add(createButtonsPanel(), BorderLayout.SOUTH);
    }
    
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Agregar nueva mascota"));
        
        //Campos de el formulario
        formPanel.add(createLabel("Nombre:"));
        txtNombre = new JTextField();
        formPanel.add(txtNombre);
        
        formPanel.add(createLabel("Tipo de animal:"));
        cmbTipoAnimal = new JComboBox<>(new String[]{"Terrestre", "Acuático", "Aéreo"});
        formPanel.add(cmbTipoAnimal);
        
        formPanel.add(createLabel("Especie:"));
        txtEspecie = new JTextField();
        formPanel.add(txtEspecie);
        
        formPanel.add(createLabel("Sexo:"));
        cmbSexo = new JComboBox<>(new String[]{"Macho", "Hembra"});
        formPanel.add(cmbSexo);
        
        formPanel.add(createLabel("Edad: (años)"));
        spnEdad = new JSpinner(new SpinnerNumberModel(0.1, 0.1, 200.0, 0.1));
        formPanel.add(spnEdad);
        
        formPanel.add(createLabel("Color:"));
        txtColor = new JTextField();
        formPanel.add(txtColor);
        
        formPanel.add(createLabel("Cliente:"));
        cmbCliente = new JComboBox<>();
        cmbCliente.setToolTipText("Seleccione el cliente de la mascota para agregarlo");
        formPanel.add(cmbCliente);
        
        return formPanel;
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        return label;
    }
    
    //panel de la tabla
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Mascotas Registradas en el sistema"));
        
        //modelación de tabla
        String[] columnNames = {"ID", "Nombre", "Especie", "Tipo", "Sexo", "Edad", "Color", "Cliente DPI"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        mascotasTable = new JTable(tableModel);
        mascotasTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mascotasTable.getSelectionModel().addListSelectionListener(e -> cargarDatosDesdeTabla());
        
        // Mejorar apariencia de la tabla
        mascotasTable.setRowHeight(25);
        mascotasTable.setFont(new Font("Arial", Font.PLAIN, 11));
        
        JScrollPane scrollPane = new JScrollPane(mascotasTable);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        return tablePanel;
    }
    
    //panel de botones
    private JPanel createButtonsPanel() {
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnAgregar = createStyledButton("Agregar Mascota", new Color(46, 125, 50));
        btnActualizar = createStyledButton("Actualizar", new Color(25, 118, 210));
        btnEliminar = createStyledButton("Eliminar", new Color(198, 40, 40));
        btnLimpiar = createStyledButton("Limpiar", new Color(97, 97, 97));
        btnBuscarPorCliente = createStyledButton("Busqueda por cliente", new Color(123, 31, 162));
        
        //acciones de los botones del panel
        btnAgregar.addActionListener(this::agregarMascota);
        btnActualizar.addActionListener(this::actualizarMascota);
        btnEliminar.addActionListener(this::eliminarMascota);
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnBuscarPorCliente.addActionListener(this::buscarPorCliente);
        
        buttonsPanel.add(btnAgregar);
        buttonsPanel.add(btnActualizar);
        buttonsPanel.add(btnEliminar);
        buttonsPanel.add(btnLimpiar);
        buttonsPanel.add(btnBuscarPorCliente);
        
        return buttonsPanel;
    }
    
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setFont(new Font("Arial", Font.BOLD, 12));
        return button;
    }
    
    //método para agregar mascota
    private void agregarMascota(ActionEvent e) {
    try {
        String nombre = txtNombre.getText().trim();
        String especie = txtEspecie.getText().trim();
        String color = txtColor.getText().trim();
        String tipoAnimal = cmbTipoAnimal.getSelectedItem().toString();
        String sexo = cmbSexo.getSelectedItem().toString();
        double edad = (double) spnEdad.getValue();
        String getDpiCliente = cmbCliente.getSelectedItem() != null ? cmbCliente.getSelectedItem().toString() : "";

        // ==========================
        // VALIDACIONES
        // ==========================

        // Cliente seleccionado
        if (getDpiCliente.equals("No hay clientes registrados") || getDpiCliente.equals("Error al cargar clientes")) {
            showError("Cliente requerido", "Debe registrar al menos un cliente antes de agregar una mascota.");
            return;
        }

        // Nombre (solo letras)
        if (!Validaciones.validarNombreMascota(nombre)) {
            showError("Nombre inválido", "El nombre de la mascota solo debe contener letras.");
            return;
        }

        // Especie (solo letras)
        if (!Validaciones.validarEspecie(especie)) {
            showError("Especie inválida", "La especie solo debe contener letras.");
            return;
        }

        // Color (solo letras)
        if (!Validaciones.validarColor(color)) {
            showError("Color inválido", "El color solo debe contener letras.");
            return;
        }

        // Edad (validada en Spinner, adicionalmente revisada)
        if (edad < 0) {
            showError("Edad inválida", "Tu mascota no ha nacido.");
            return;
        }

        if (edad > 100) {
            showError("Edad exagerada", "La edad ingresada es demasiado exagerada.");
            return;
        }

        // ==========================
        // AGREGAR MASCOTA
        // ==========================

        String idMascota = GeneradorID.generarIDMascota();

        Mascota mascota = new Mascota(idMascota, tipoAnimal, nombre, especie, sexo,
                edad, color, new Date(), getDpiCliente);

        String resultado = mascotaService.agregarMascota(mascota);

        showSuccess("Mascota agregada", "ID: " + idMascota + "\n" + resultado);
        limpiarCampos();
        cargarMascotas();

        } catch (Exception ex) {
        showError("Error al agregar mascota", ex.getMessage());
        ex.printStackTrace();
        }
    }

    
    //método para actualizar mascota
    private void actualizarMascota(ActionEvent e) {
    int selectedRow = mascotasTable.getSelectedRow();
    if (selectedRow == -1) {
        showWarning("Selección requerida", "Seleccione una mascota para actualizar.");
        return;
    }

    try {
        String id = tableModel.getValueAt(selectedRow, 0).toString();
        Mascota mascota = mascotaService.obtenerMascota(id);

        if (mascota != null) {

            String nombre = txtNombre.getText().trim();
            String especie = txtEspecie.getText().trim();
            String color = txtColor.getText().trim();
            String tipoAnimal = cmbTipoAnimal.getSelectedItem().toString();
            String sexo = cmbSexo.getSelectedItem().toString();
            double edad = (double) spnEdad.getValue();
            String dpiCliente = cmbCliente.getSelectedItem().toString();

            // ==========================
            // VALIDACIONES
            // ==========================

            if (!Validaciones.validarNombreMascota(nombre)) {
                showError("Nombre inválido", "El nombre de la mascota solo debe contener letras.");
                return;
            }

            if (!Validaciones.validarEspecie(especie)) {
                showError("Especie inválida", "La especie solo debe contener letras.");
                return;
            }

            if (!Validaciones.validarColor(color)) {
                showError("Color inválido", "El color solo debe contener letras.");
                return;
            }

            if (edad < 0) {
                showError("Edad inválida", "Tu mascota no ha nacido.");
                return;
            }

            if (edad > 100) {
                showError("Edad exagerada", "La edad ingresada es demasiado exagerada.");
                return;
            }

            // ==========================
            // SI VALIDÓ → ACTUALIZAR
            // ==========================

            mascota.setNombre(nombre);
            mascota.setEspecie(especie);
            mascota.setColor(color);
            mascota.setTipoAnimal(tipoAnimal);
            mascota.setSexo(sexo);
            mascota.setEdad(edad);
            mascota.setDpiCliente(dpiCliente);

            String resultado = mascotaService.actualizarMascota(mascota);
            showSuccess("Mascota actualizada", resultado);
            cargarMascotas();
            }
        } catch (Exception ex) {
        showError("Error al actualizar mascota", ex.getMessage());
        }
    }

    
    //método para eliminar mascota
    private void eliminarMascota(ActionEvent e) {
        int selectedRow = mascotasTable.getSelectedRow();
        if (selectedRow == -1) {
            showWarning("Selección necesaria", "Seleccione una mascota para eliminar");
            return;
        }
        
        String nombreMascota = tableModel.getValueAt(selectedRow, 1).toString();
        String idMascota = tableModel.getValueAt(selectedRow, 0).toString();
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "<html><b>¿Está seguro de eliminar esta mascota?</b><br>" +
            "Mascota: <b>" + nombreMascota + "</b><br>" +
            "ID: " + idMascota + "<br><br>" +
            "Esta acción no se puede deshacer.</html>", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String resultado = mascotaService.eliminarMascota(idMascota);
                showSuccess("Mascota eliminada", resultado);
                cargarMascotas();
                limpiarCampos();
            } catch (Exception ex) {
                showError("Error al eliminar mascota", ex.getMessage());
            }
        }
    }
    
    //método para buscar mascota por cliente
    private void buscarPorCliente(ActionEvent e) {
        String dpiCliente = cmbCliente.getSelectedItem() != null ? cmbCliente.getSelectedItem().toString() : "";
        
        if (dpiCliente.isEmpty() || dpiCliente.equals("No hay clientes registrados") || dpiCliente.equals("Error al cargar clientes")) {
            showWarning("Selección requerida", "seleccione un cliente válido para buscar sus mascotas");
            return;
        }
        
        try {
            List<Mascota> mascotas = mascotaService.obtenerMascotasPorCliente(dpiCliente);
            tableModel.setRowCount(0);
            
            for (Mascota mascota : mascotas) {
                Object[] row = {
                    mascota.getId(),
                    mascota.getNombre(),
                    mascota.getEspecie(),
                    mascota.getTipoAnimal(),
                    mascota.getSexo(),
                    mascota.getEdad(),
                    mascota.getColor(),
                    mascota.getDpiCliente()
                };
                tableModel.addRow(row);
            }
            
            updateCounter(mascotas.size());
            showSuccess("Búsqueda completada", "se encontraron " + mascotas.size() + " mascotas para el cliente: " + dpiCliente);
            
        } catch (Exception ex) {
            showError("Error al buscar mascotas", ex.getMessage());
        }
    }
    
    //método para cargar datos desde la tabla a los campos de texto
    private void cargarDatosDesdeTabla() {
        int selectedRow = mascotasTable.getSelectedRow();
        if (selectedRow != -1) {
            txtNombre.setText(tableModel.getValueAt(selectedRow, 1).toString());
            txtEspecie.setText(tableModel.getValueAt(selectedRow, 2).toString());
            cmbTipoAnimal.setSelectedItem(tableModel.getValueAt(selectedRow, 3).toString());
            cmbSexo.setSelectedItem(tableModel.getValueAt(selectedRow, 4).toString());
            spnEdad.setValue(Double.parseDouble(tableModel.getValueAt(selectedRow, 5).toString()));
            txtColor.setText(tableModel.getValueAt(selectedRow, 6).toString());
            cmbCliente.setSelectedItem(tableModel.getValueAt(selectedRow, 7).toString());
        }
    }
    
    //método para limpiar campos
    private void limpiarCampos() {
        txtNombre.setText("");
        txtEspecie.setText("");
        txtColor.setText("");
        cmbTipoAnimal.setSelectedIndex(0);
        cmbSexo.setSelectedIndex(0);
        spnEdad.setValue(0.1);
        if (cmbCliente.getItemCount() > 0 && !cmbCliente.getItemAt(0).toString().contains("No hay") && !cmbCliente.getItemAt(0).toString().contains("Error")) {
            cmbCliente.setSelectedIndex(0);
        }
    }
    
    //método para cargar mascotas en la tabla
    public void cargarMascotas() {
        try {
            tableModel.setRowCount(0);
            List<Mascota> mascotas = mascotaService.obtenerTodasMascotas();
            
            for (Mascota mascota : mascotas) {
                Object[] row = {
                    mascota.getId(),
                    mascota.getNombre(),
                    mascota.getEspecie(),
                    mascota.getTipoAnimal(),
                    mascota.getSexo(),
                    mascota.getEdad(),
                    mascota.getColor(),
                    mascota.getDpiCliente()
                };
                tableModel.addRow(row);
            }
            
            updateCounter(mascotas.size());
            
        } catch (Exception ex) {
            showError("Error al cargar mascotas", ex.getMessage());
        }
    }
    
    //método para cargar clientes en el combo box
    private void cargarClientesComboBox() {
        try {
            String seleccionActual = cmbCliente.getSelectedItem() != null ? 
                                   cmbCliente.getSelectedItem().toString() : "";
            
            cmbCliente.removeAllItems();
            var clientes = clienteService.obtenerTodosClientes();
            
            if (clientes.isEmpty()) {
                cmbCliente.addItem("No hay clientes registrados");
                cmbCliente.setEnabled(false);
            } else {
                cmbCliente.setEnabled(true);
                for (var cliente : clientes) {
                    cmbCliente.addItem(cliente.getDpi());
                }
                
                if (!seleccionActual.isEmpty() && !seleccionActual.equals("No hay clientes registrados") && !seleccionActual.equals("Error al cargar clientes")) {
                    for (int i = 0; i < cmbCliente.getItemCount(); i++) {
                        if (cmbCliente.getItemAt(i).equals(seleccionActual)) {
                            cmbCliente.setSelectedIndex(i);
                            break;
                        }
                    }
                } else if (cmbCliente.getItemCount() > 0) {
                    cmbCliente.setSelectedIndex(0);
                }
            }
            
        } catch (Exception ex) {
            showError("Error al cargar clientes", ex.getMessage());
            cmbCliente.addItem("Error al cargar clientes");
            cmbCliente.setEnabled(false);
        }
    }
    
    private void updateCounter(int count) {
        for (Component comp : getComponents()) {
            if (comp instanceof JPanel) {
                Border border = ((JPanel) comp).getBorder();
                if (border instanceof javax.swing.border.TitledBorder) {
                    if (((javax.swing.border.TitledBorder) border).getTitle().contains("Mascotas Registradas")) {
                        ((JPanel) comp).setBorder(
                            BorderFactory.createTitledBorder("Mascotas registradas en el sistma (" + count + ")")
                        );
                        break;
                    }
                }
            }
        }
    }
    
    //métodos extras para mostrar mensajes
    private void showError(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }
    
    private void showSuccess(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showWarning(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
    }
}