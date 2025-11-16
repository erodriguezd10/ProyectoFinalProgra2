/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.panels;

/**
 *
 * @author GRUPO_PROG_2_C_1_ 4
 */
import models.Producto;
import services.ProductoService;
import utils.GeneradorID;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import utils.Validaciones;

//panel para la gestión de productos
public class ProductosPanel extends JPanel {
    private ProductoService productoService;
    private JTable productosTable;
    private DefaultTableModel tableModel;
    private JTextField txtNombre, txtDescripcion, txtBuscar;
    private JSpinner spnPrecio, spnCantidad, spnCantidadComprar;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnLimpiar, btnBuscar, btnComprar;
    
    //constructor del panel de productos
    public ProductosPanel() {
        productoService = new ProductoService();
        initializeUI();
        cargarProductos();
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        //panel superior(búsqueda y formulario)
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.add(createSearchPanel(), BorderLayout.NORTH);
        topPanel.add(createFormPanel(), BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);
        
        //tabla de productos(centro)
        add(createTablePanel(), BorderLayout.CENTER);
        
        //botones de acción(abajo)
        add(createButtonsPanel(), BorderLayout.SOUTH);
    }
    
    //panel de búsqueda
    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Buscar productos"));
        
        searchPanel.add(new JLabel("Buscar por nombre:"));
        txtBuscar = new JTextField(20);
        searchPanel.add(txtBuscar);
        
        btnBuscar = createStyledButton("Buscar", new Color(123, 31, 162));
        btnBuscar.addActionListener(this::buscarProductos);
        searchPanel.add(btnBuscar);
        
        JButton btnMostrarTodos = createStyledButton("Mostrar todos", new Color(66, 66, 66));
        btnMostrarTodos.addActionListener(e -> cargarProductos());
        searchPanel.add(btnMostrarTodos);
        
        return searchPanel;
    }
    
    //panel del formulario
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Información sobre el producto"));
        
        //campos para el formulario
        formPanel.add(createLabel("Nombre:"));
        txtNombre = new JTextField();
        formPanel.add(txtNombre);
        
        formPanel.add(createLabel("Precio (Q.):"));
        spnPrecio = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 10000.0, 0.5));
        JSpinner.NumberEditor precioEditor = new JSpinner.NumberEditor(spnPrecio, "#,##0.00");
        spnPrecio.setEditor(precioEditor);
        formPanel.add(spnPrecio);
        
        formPanel.add(createLabel("Descripción:"));
        txtDescripcion = new JTextField();
        formPanel.add(txtDescripcion);
        
        formPanel.add(createLabel("Cantidad en stock:"));
        spnCantidad = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
        formPanel.add(spnCantidad);
        
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
        tablePanel.setBorder(BorderFactory.createTitledBorder("Productos en inventario (0)"));
        
        //modelo de tabla
        String[] columnNames = {"ID", "Nombre", "Precio (Q.)", "Descripción", "Stock", "Estado"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            //definir tipos de datos para cada columna
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 2) return Double.class;
                if (columnIndex == 4) return Integer.class;
                return String.class;
            }
        };
        
        productosTable = new JTable(tableModel);
        productosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productosTable.getSelectionModel().addListSelectionListener(e -> cargarDatosDesdeTabla());
        
        productosTable.setRowHeight(25);
        productosTable.setFont(new Font("Arial", Font.PLAIN, 11));
        productosTable.setAutoCreateRowSorter(true);
        
        //renderer personalizado para colores de el stock
        productosTable.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    String stockStr = table.getValueAt(row, 4).toString();
                    try {
                        int stock = Integer.parseInt(stockStr);
                        if (stock == 0) {
                            c.setBackground(new Color(255, 205, 210)); //rojo sin stock
                        } else if (stock < 10) {
                            c.setBackground(new Color(255, 245, 157)); //amarillo para stock bajo
                        } else {
                            c.setBackground(Color.WHITE); //blanco para stock normal
                        }
                    } catch (NumberFormatException e) {
                        c.setBackground(Color.WHITE);
                    }
                }
                
                return c;
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(productosTable);
        scrollPane.setPreferredSize(new Dimension(500, 250));
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        return tablePanel;
    }
    
    //panel de botones
    private JPanel createButtonsPanel() {
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnAgregar = createStyledButton("Agregar producto", new Color(46, 125, 50));
        btnActualizar = createStyledButton("Actualizar", new Color(25, 118, 210));
        btnEliminar = createStyledButton("Eliminar", new Color(198, 40, 40));
        btnLimpiar = createStyledButton("Limpiar", new Color(97, 97, 97));
        btnComprar = createStyledButton("Comprar producto", new Color(255, 152, 0));
        
        //paanel para la compra
        JPanel compraPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        compraPanel.add(new JLabel("Cantidad a comprar:"));
        spnCantidadComprar = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        spnCantidadComprar.setPreferredSize(new Dimension(60, 25));
        compraPanel.add(spnCantidadComprar);
        compraPanel.add(btnComprar);
        
        //acciones de los botones de compra
        btnAgregar.addActionListener(this::agregarProducto);
        btnActualizar.addActionListener(this::actualizarProducto);
        btnEliminar.addActionListener(this::eliminarProducto);
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnComprar.addActionListener(this::comprarProducto);
        
        buttonsPanel.add(btnAgregar);
        buttonsPanel.add(btnActualizar);
        buttonsPanel.add(btnEliminar);
        buttonsPanel.add(btnLimpiar);
        buttonsPanel.add(compraPanel);
        
        return buttonsPanel;
    }
    
    //método para crear botones estilizados
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setFont(new Font("Arial", Font.BOLD, 12));
        return button;
    }
    
    //método para agregar producto
    private void agregarProducto(ActionEvent e) {
    try {
        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        double precio = (Double) spnPrecio.getValue();
        int cantidad = (Integer) spnCantidad.getValue();

        // VALIDACIÓN: Nombre solo letras
        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            showError("Nombre inválido", 
                "El nombre del producto solo debe contener letras.\nEjemplo: 'Croquetas', 'Juguete'.");
            return;
        }

        // VALIDACIÓN: precio mayor que 0
        if (precio <= 0) {
            showError("Precio inválido", "El precio debe ser mayor a 0.");
            return;
        }

        // VALIDACIÓN: Cantidad no puede ser negativa
        if (cantidad < 0) {
            showError("Cantidad inválida", 
                "No se permiten cantidades negativas.\nIngrese una cantidad válida.");
            return;
        }

        // VALIDACIÓN: Solo enteros para stock
        if (cantidad % 1 != 0) {
            showError("Cantidad inválida", "La cantidad en stock debe ser un número entero.");
            return;
        }

        // Generación del ID automático
        String idProducto = GeneradorID.generarIDProducto();

        Producto producto = new Producto(idProducto, nombre, precio, descripcion, cantidad);
        String resultado = productoService.agregarProducto(producto);

        showSuccess("Producto agregado", 
            "<html><b>Producto agregado exitosamente</b><br>" +
            "ID: " + idProducto + "<br>" +
            "Nombre: " + nombre + "<br>" +
            "Precio: Q." + String.format("%,.2f", precio) + "<br>" +
            "Stock: " + cantidad + " unidades</html>");

        limpiarCampos();
        cargarProductos();

        } catch (Exception ex) {
        showError("Error al agregar producto", ex.getMessage());
        ex.printStackTrace();
        }
    }

    
    //método para actualizar producto
    private void actualizarProducto(ActionEvent e) {
    int selectedRow = productosTable.getSelectedRow();
    if (selectedRow == -1) {
        showWarning("Selección requerida", "Seleccione un producto para actualizar");
        return;
    }

    try {
        String id = tableModel.getValueAt(selectedRow, 0).toString();
        Producto producto = productoService.obtenerProducto(id);

        if (producto != null) {
            String nombre = txtNombre.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            double precio = (Double) spnPrecio.getValue();
            int cantidad = (Integer) spnCantidad.getValue();

            // VALIDACIÓN: Solo letras
            if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                showError("Nombre inválido", "El nombre solo debe contener letras.");
                return;
            }

            // VALIDACIÓN: precio mayor que 0
            if (precio <= 0) {
                showError("Precio inválido", "El precio debe ser mayor a 0.");
                return;
            }

            // VALIDACIÓN: cantidades negativas
            if (cantidad < 0) {
                showError("Cantidad inválida", "No se permiten cantidades negativas.");
                return;
            }

            // VALIDACIÓN: solo enteros
            if (cantidad % 1 != 0) {
                showError("Cantidad inválida", "La cantidad debe ser un número entero.");
                return;
            }

            // Setear valores
            producto.setNombre(nombre);
            producto.setDescripcion(descripcion);
            producto.setPrecio(precio);
            producto.setCantidad(cantidad);

            String resultado = productoService.actualizarProducto(producto);
            showSuccess("Producto actualizado", resultado);
            cargarProductos();
        }
            } catch (Exception ex) {
        showError("Error al actualizar producto", ex.getMessage());
        }
    }

    //método para eliminar producto
    private void eliminarProducto(ActionEvent e) {
        int selectedRow = productosTable.getSelectedRow();
        if (selectedRow == -1) {
            showWarning("Selección requerida", "seleccione un producto para eliminar");
            return;
        }
        
        String nombreProducto = tableModel.getValueAt(selectedRow, 1).toString();
        String idProducto = tableModel.getValueAt(selectedRow, 0).toString();
        int stock = Integer.parseInt(tableModel.getValueAt(selectedRow, 4).toString());
        
        if (stock > 0) {
            showWarning("Stock disponible", 
                "No se puede eliminar un producto con stock disponible.\n" +
                "Stock actual: " + stock + " unidades\n\n" +
                "Venda o ajuste el stock a cero antes de eliminar.");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "<html><b>¿Está seguro de eliminar este producto?</b><br>" +
            "Producto: <b>" + nombreProducto + "</b><br>" +
            "ID: " + idProducto + "<br><br>" +
            "Esta acción no se puede deshacer.</html>", 
            "Confirmar la eliminación", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String resultado = productoService.eliminarProducto(idProducto);
                showSuccess("Producto eliminado", resultado);
                cargarProductos();
                limpiarCampos();
            } catch (Exception ex) {
                showError("Error al eliminar producto", ex.getMessage());
            }
        }
    }
    //método para comprar producto
    private void comprarProducto(ActionEvent e) {
        int selectedRow = productosTable.getSelectedRow();
        if (selectedRow == -1) {
            showWarning("Selección requerida", "seleccione un producto para comprar");
            return;
        }
        
        String idProducto = tableModel.getValueAt(selectedRow, 0).toString();
        String nombreProducto = tableModel.getValueAt(selectedRow, 1).toString();
        int stockActual = Integer.parseInt(tableModel.getValueAt(selectedRow, 4).toString());
        int cantidadComprar = (Integer) spnCantidadComprar.getValue();
        
        if (cantidadComprar <= 0) {
            showError("Cantidad inválida", "la cantidad para comprar debe ser mayors a cero");
            return;
        }
        
        if (cantidadComprar > stockActual) {
            showError("Stock insuficiente", 
                "No hay suficiente stock para cumplir con la compra.\n" +
                "Stock actual: " + stockActual + " unidades\n" +
                "Intenta comprar: " + cantidadComprar + " unidades");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "<html><b>Confirmar Compra</b><br>" +
            "Producto: <b>" + nombreProducto + "</b><br>" +
            "Cantidad: " + cantidadComprar + " unidades<br>" +
            "Stock después: " + (stockActual - cantidadComprar) + " unidades<br><br>" +
            "¿Confirmar la compra?</html>", 
            "Confirmar compra", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String resultado = productoService.comprarProducto(idProducto, cantidadComprar);
                showSuccess("✅ Compra realizada", 
                    "<html><b>Compra exitosa</b><br>" +
                    "Producto: " + nombreProducto + "<br>" +
                    "Cantidad comprada: " + cantidadComprar + " unidades<br>" +
                    "Nuevo stock: " + (stockActual - cantidadComprar) + " unidades</html>");
                cargarProductos();
            } catch (Exception ex) {
                showError("Error al procesar la compra", ex.getMessage());
            }
        }
    }
    //método para buscar productos
    private void buscarProductos(ActionEvent e) {
        String nombreBuscar = txtBuscar.getText().trim();
        
        if (nombreBuscar.isEmpty()) {
            showWarning("Búsqueda vacía", "ingrese un nombre para la busqueda");
            return;
        }
        
        try {
            List<Producto> productos = productoService.buscarProductosPorNombre(nombreBuscar);
            tableModel.setRowCount(0);
            
            for (Producto producto : productos) {
                Object[] row = {
                    producto.getId(),
                    producto.getNombre(),
                    producto.getPrecio(),
                    producto.getDescripcion(),
                    producto.getCantidad(),
                    getEstadoStock(producto.getCantidad())
                };
                tableModel.addRow(row);
            }
            
            updateCounter(productos.size());
            showSuccess("Búsqueda completada", 
                "Se encontraron " + productos.size() + " productos con: '" + nombreBuscar + "'");
            
        } catch (Exception ex) {
            showError("Error al buscar productos", ex.getMessage());
        }
    }
    
    private String getEstadoStock(int cantidad) {
        if (cantidad == 0) return "Sin stock";
        if (cantidad < 10) return "Stock bajo";
        return "En stock";
    }
    //método para cargar datos desde la tabla a los campos
    private void cargarDatosDesdeTabla() {
        int selectedRow = productosTable.getSelectedRow();
        if (selectedRow != -1) {
            txtNombre.setText(tableModel.getValueAt(selectedRow, 1).toString());
            spnPrecio.setValue(Double.parseDouble(tableModel.getValueAt(selectedRow, 2).toString()));
            txtDescripcion.setText(tableModel.getValueAt(selectedRow, 3).toString());
            spnCantidad.setValue(Integer.parseInt(tableModel.getValueAt(selectedRow, 4).toString()));
        }
    }
    
    //método para limpiar campos del formulario
    private void limpiarCampos() {
        txtNombre.setText("");
        txtDescripcion.setText("");
        spnPrecio.setValue(0.0);
        spnCantidad.setValue(0);
        txtBuscar.setText("");
        spnCantidadComprar.setValue(1);
    }
    
    //método para cargar productos en la tabla
    public void cargarProductos() {
        try {
            tableModel.setRowCount(0);
            List<Producto> productos = productoService.obtenerTodosProductos();
            
            for (Producto producto : productos) {
                Object[] row = {
                    producto.getId(),
                    producto.getNombre(),
                    producto.getPrecio(),
                    producto.getDescripcion(),
                    producto.getCantidad(),
                    getEstadoStock(producto.getCantidad())
                };
                tableModel.addRow(row);
            }
            
            updateCounter(productos.size());
            
        } catch (Exception ex) {
            showError("Error al cargar productos", ex.getMessage());
        }
    }
    
    //método para actualizar el contador de productos en el borde del panel
    private void updateCounter(int count) {
        for (Component comp : getComponents()) {
            if (comp instanceof JPanel) {
                Border border = ((JPanel) comp).getBorder();
                if (border instanceof javax.swing.border.TitledBorder) {
                    String title = ((javax.swing.border.TitledBorder) border).getTitle();
                    if (title != null && title.contains("Productos en el inventario")) {
                        ((JPanel) comp).setBorder(
                            BorderFactory.createTitledBorder("?roductos en el inventario (" + count + ")")
                        );
                        break;
                    }
                }
            }
        }
    }
    
    //métodos para mostrar mensajes
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