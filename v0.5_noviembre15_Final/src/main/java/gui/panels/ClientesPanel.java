/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.panels;

/**
 *
 * @author GRUPO_PROG_2_C_1_ 4
 */
import models.Cliente;
import services.ClienteService;
import javax.swing.border.Border;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.concurrent.ExecutionException;
import utils.Validaciones;

public class ClientesPanel extends JPanel {
    private ClienteService clienteService;
    private JTable clientesTable;
    private DefaultTableModel tableModel;
    private JTextField txtNombre, txtDireccion, txtDpi, txtTelefono;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnLimpiar;
    
    public ClientesPanel() {
        clienteService = new ClienteService();
        initializeUI();
        cargarClientes();
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        //panel del formulario(arriba)
        add(createFormPanel(), BorderLayout.NORTH);
        
        //pabla para los dueños(centro)
        add(createTablePanel(), BorderLayout.CENTER);
        
        //Botones para acción(abajo)
        add(createButtonsPanel(), BorderLayout.SOUTH);
    }
    
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Agregar nuevo cliente"));
        
        //campos a llenar
        formPanel.add(createLabel("Nombre:"));
        txtNombre = new JTextField();
        formPanel.add(txtNombre);
        
        formPanel.add(createLabel("Dirección:"));
        txtDireccion = new JTextField();
        formPanel.add(txtDireccion);
        
        formPanel.add(createLabel("DPI:"));
        txtDpi = new JTextField();
        formPanel.add(txtDpi);
        
        formPanel.add(createLabel("Teléfono:"));
        txtTelefono = new JTextField();
        formPanel.add(txtTelefono);
        
        return formPanel;
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        return label;
    }
    
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Clientes registrados"));
        
        //modelo de  la tabla
        String[] columnNames = {"DPI", "Nombre", "Dirección", "Teléfono"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        clientesTable = new JTable(tableModel);
        clientesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        clientesTable.getSelectionModel().addListSelectionListener(e -> cargarDatosDesdeTabla());
        
        //modernización de la tabla
        clientesTable.setRowHeight(25);
        clientesTable.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(clientesTable);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        return tablePanel;
    }
    
    private JPanel createButtonsPanel() {
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnAgregar = createStyledButton("Agregar cliente", new Color(46, 125, 50));
        btnActualizar = createStyledButton("Actualizar", new Color(25, 118, 210));
        btnEliminar = createStyledButton("Eliminar", new Color(198, 40, 40));
        btnLimpiar = createStyledButton("Limpiar", new Color(97, 97, 97));
        
        //acciones de los botones
        btnAgregar.addActionListener(this::agregarCliente);
        btnActualizar.addActionListener(this::actualizarCliente);
        btnEliminar.addActionListener(this::eliminarCliente);
        btnLimpiar.addActionListener(e -> limpiarCampos());
        
        buttonsPanel.add(btnAgregar);
        buttonsPanel.add(btnActualizar);
        buttonsPanel.add(btnEliminar);
        buttonsPanel.add(btnLimpiar);
        
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
    
    private void agregarCliente(ActionEvent e) {
    try {
        String nombre = txtNombre.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String dpi = txtDpi.getText().trim();
        String telefono = txtTelefono.getText().trim();

        // ==========================
        // VALIDACIONES
        // ==========================

        // Nombre
        if (!Validaciones.validarNombreCliente(nombre)) {
            showError("Nombre inválido",
                "El nombre solo debe contener letras y tener mínimo 2 caracteres.");
            return;
        }

        // DPI
        if (!Validaciones.validarDPI(dpi)) {
            showError("DPI inválido",
                "El DPI debe contener exactamente 13 dígitos numéricos.");
            return;
        }

        // Teléfono
        if (!Validaciones.validarTelefono(telefono)) {
            showError("Teléfono inválido",
                "El teléfono debe contener 8 dígitos numéricos.");
            return;
        }

        // ==========================
        // SI TODO_ES_CORRECTO_AGREGAR_CLIENTE
        // ==========================

        Cliente cliente = new Cliente(nombre, direccion, dpi, telefono);
        String resultado = clienteService.agregarCliente(cliente);

        showSuccess("Cliente agregado", resultado);
        limpiarCampos();
        cargarClientes();

        } catch (Exception ex) {
        showError("Error al agregar cliente", ex.getMessage());
        }
    }

    //método de actualizar cliente
    // private void actualizarCliente(ActionEvent e) {
    //     int selectedRow = clientesTable.getSelectedRow();
    //     if (selectedRow == -1) {
    //         showWarning("Selección requerida", "seleccione un cliente para actualizar");
    //         return;
    //     }
        
    //     try {
    //         String dpi = tableModel.getValueAt(selectedRow, 0).toString();
    //         Cliente cliente = clienteService.obtenerCliente(dpi);
            
    //         if (cliente != null) {
    //             cliente.setNombre(txtNombre.getText());
    //             cliente.setDireccion(txtDireccion.getText());
    //             cliente.setNumeroTelefono(txtTelefono.getText());
                
    //             String resultado = clienteService.actualizarCliente(cliente);
    //             showSuccess("Cliente actualizado", resultado);
    //             cargarClientes();
    //         }
    //     } catch (Exception ex) {
    //         showError("Error al actualizar el cliente", ex.getMessage());
    //     }
    // }
    private void actualizarCliente(ActionEvent e) {
    int selectedRow = clientesTable.getSelectedRow();
    if (selectedRow == -1) {
        showWarning("Selección requerida", "Seleccione un cliente para actualizar.");
        return;
    }

    try {
        String dpi = tableModel.getValueAt(selectedRow, 0).toString();
        Cliente cliente = clienteService.obtenerCliente(dpi);

        if (cliente != null) {

            String nombre = txtNombre.getText().trim();
            String direccion = txtDireccion.getText().trim();
            String telefono = txtTelefono.getText().trim();

            // ==========================
            // VALIDACIONES
            // ==========================

            if (!Validaciones.validarNombreCliente(nombre)) {
                showError("Nombre inválido",
                    "El nombre solo debe contener letras y tener mínimo 2 caracteres.");
                return;
            }

            if (!Validaciones.validarTelefono(telefono)) {
                showError("Teléfono inválido",
                    "El teléfono debe contener 8 dígitos numéricos.");
                return;
            }

            // ==========================

            cliente.setNombre(nombre);
            cliente.setDireccion(direccion);
            cliente.setNumeroTelefono(telefono);

            String resultado = clienteService.actualizarCliente(cliente);
            showSuccess("Cliente actualizado", resultado);
            cargarClientes();
            }
        } catch (Exception ex) {
        showError("Error al actualizar el cliente", ex.getMessage());
        }
    }
    //método de eliminar cliente
    private void eliminarCliente(ActionEvent e) {
        int selectedRow = clientesTable.getSelectedRow();
        if (selectedRow == -1) {
            showWarning("Selección requerida", "seleccione un cliente para eliminar");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "<html><b>¿Está seguro de eliminar este cliente?</b><br>Esta acción no se puede deshacer.</html>", 
            "Confirmar Eliminación", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String dpi = tableModel.getValueAt(selectedRow, 0).toString();
                String resultado = clienteService.eliminarCliente(dpi);
                showSuccess("Cliente eliminado", resultado);
                cargarClientes();
                limpiarCampos();
            } catch (Exception ex) {
                showError("Error al eliminar el cliente", ex.getMessage());
            }
        }
    }
    //método para cargar datos desde la tabla a los campos de texto
    private void cargarDatosDesdeTabla() {
        int selectedRow = clientesTable.getSelectedRow();
        if (selectedRow != -1) {
            txtDpi.setText(tableModel.getValueAt(selectedRow, 0).toString());
            txtNombre.setText(tableModel.getValueAt(selectedRow, 1).toString());
            txtDireccion.setText(tableModel.getValueAt(selectedRow, 2).toString());
            txtTelefono.setText(tableModel.getValueAt(selectedRow, 3).toString());
        }
    }
    
    private void limpiarCampos() {
        txtNombre.setText("");
        txtDireccion.setText("");
        txtDpi.setText("");
        txtTelefono.setText("");
    }
    //método para cargar clientes en la tabla
    public void cargarClientes() {
        try {
            tableModel.setRowCount(0);
            List<Cliente> clientes = clienteService.obtenerTodosClientes();
            
            for (Cliente cliente : clientes) {
                Object[] row = {
                    cliente.getDpi(),
                    cliente.getNombre(),
                    cliente.getDireccion(),
                    cliente.getNumeroTelefono()
                };
                tableModel.addRow(row);
            }
            
            updateCounter(clientes.size());
            
        } catch (Exception ex) {
            showError("Error al cargar clientes", ex.getMessage());
        }
    }
    
    private void updateCounter(int count) {
        for (Component comp : getComponents()) {
            if (comp instanceof JPanel) {
                Border border = ((JPanel) comp).getBorder();
                if (border instanceof javax.swing.border.TitledBorder) {
                    if (((javax.swing.border.TitledBorder) border).getTitle().contains("Clientes registrados")) {
                        ((JPanel) comp).setBorder(
                            BorderFactory.createTitledBorder("Clientes registrados (" + count + ")")
                        );
                        break;
                    }
                }
            }
        }
    }
    
    //métodos extra para la visualización de los errores por medio de mensajes
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
//(*_*