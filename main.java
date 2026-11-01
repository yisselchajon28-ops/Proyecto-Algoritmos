import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class main extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final Color AZUL = new Color(40, 61, 82);
    private static final Color VERDE = new Color(43, 135, 120);
    private static final Color FONDO = new Color(245, 247, 249);
    private static final Color BLANCO = Color.WHITE;
    private static final Color TEXTO = new Color(51, 65, 78);

    static ArrayList<producto> productos = new ArrayList<>();
    static ArrayList<cliente> clientes = new ArrayList<>();
    static ArrayList<Empleado> empleados = new ArrayList<>();
    static ArrayList<Bodega> bodegas = new ArrayList<>();
    static ArrayList<Inventario> inventarios = new ArrayList<>();
    static ArrayList<OrdenCompra> ordenes = new ArrayList<>();

    private final DefaultTableModel modeloProductos = crearModelo(
            "Código", "Nombre", "Marca", "Precio");
    private final DefaultTableModel modeloClientes = crearModelo(
            "DPI", "Nombre", "Dirección", "Nacimiento", "Tipo");
    private final DefaultTableModel modeloEmpleados = crearModelo(
            "ID", "Nombre", "DPI", "Puesto", "Salario");
    private final DefaultTableModel modeloBodegas = crearModelo(
            "ID", "Encargado", "Capacidad", "Dirección");
    private final DefaultTableModel modeloInventario = crearModelo(
            "Bodega", "Código", "Producto", "Existencias");
    private final DefaultTableModel modeloOrdenes = crearModelo(
            "Orden", "Cliente", "Producto", "Cantidad", "Fecha", "Total", "Estado");

    private final JTable tablaProductos = crearTabla(modeloProductos);
    private final JTable tablaClientes = crearTabla(modeloClientes);
    private final JTable tablaEmpleados = crearTabla(modeloEmpleados);
    private final JTable tablaBodegas = crearTabla(modeloBodegas);
    private final JTable tablaInventario = crearTabla(modeloInventario);
    private final JTable tablaOrdenes = crearTabla(modeloOrdenes);

    private final JComboBox<producto> productoInventario = new JComboBox<>();
    private final JComboBox<Bodega> bodegaInventario = new JComboBox<>();
    private final JComboBox<cliente> clienteVenta = new JComboBox<>();
    private final JComboBox<producto> productoVenta = new JComboBox<>();
    private final JComboBox<Bodega> bodegaVenta = new JComboBox<>();
    private final JLabel estado = new JLabel("Listo");

    public static void main(String[] args) {
        try {
            cargarDatos();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "No se pudieron leer los datos.txt:\n" + e.getMessage(),
                    "Error al abrir los datos", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SwingUtilities.invokeLater(() -> new main().setVisible(true));
    }

    public main() {
        setTitle("SISTEMA | Ventas e inventario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(790, 560));
        setSize(1100, 720);
        setLocationRelativeTo(null);

        JPanel principal = new JPanel(new BorderLayout(0, 0));
        principal.setBackground(FONDO);
        principal.add(crearEncabezado(), BorderLayout.NORTH);
        principal.add(crearPestanas(), BorderLayout.CENTER);

        estado.setBorder(BorderFactory.createEmptyBorder(9, 18, 9, 18));
        estado.setForeground(TEXTO);
        estado.setBackground(BLANCO);
        estado.setOpaque(true);
        principal.add(estado, BorderLayout.SOUTH);

        setContentPane(principal);
        actualizarPantalla();
    }

    private JPanel crearEncabezado() {
        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(AZUL);
        encabezado.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));

        JLabel titulo = new JLabel("SISTEMA DE INVENTARIO Y VENTAS");
        titulo.setForeground(BLANCO);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));

        JLabel subtitulo = new JLabel("Ventas e inventario");
        subtitulo.setForeground(new Color(220, 231, 238));
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JPanel textos = new JPanel(new BorderLayout(0, 4));
        textos.setOpaque(false);
        textos.add(titulo, BorderLayout.NORTH);
        textos.add(subtitulo, BorderLayout.SOUTH);
        encabezado.add(textos, BorderLayout.WEST);
        return encabezado;
    }

    private JTabbedPane crearPestanas() {
        JTabbedPane pestanas = new JTabbedPane();
        pestanas.setFont(new Font("SansSerif", Font.BOLD, 13));
        pestanas.setBackground(BLANCO);
        pestanas.addTab("Inicio", crearInicio());
        pestanas.addTab("Productos", crearTabProductos());
        pestanas.addTab("Clientes", crearTabClientes());
        pestanas.addTab("Empleados", crearTabEmpleados());
        pestanas.addTab("Bodegas", crearTabBodegas());
        pestanas.addTab("Inventario", crearTabInventario());
        pestanas.addTab("Ventas", crearTabVentas());
        return pestanas;
    }

    private JPanel crearInicio() {
        JPanel panel = crearPanel();
        panel.setLayout(new GridBagLayout());

        JPanel bienvenida = new JPanel(new BorderLayout(0, 10));
        bienvenida.setOpaque(false);
        JLabel titulo = new JLabel("INVENTARIO", JLabel.CENTER);
        titulo.setForeground(AZUL);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        JLabel texto = new JLabel("Bienvenido al sistema de ventas", JLabel.CENTER);
        texto.setForeground(TEXTO);
        texto.setFont(new Font("SansSerif", Font.PLAIN, 15));
        bienvenida.add(titulo, BorderLayout.NORTH);
        bienvenida.add(texto, BorderLayout.CENTER);
        panel.add(bienvenida);
        return panel;
    }

    private JPanel crearTabProductos() {
        return crearTabSimple("Registrar producto", "Completa los datos básicos del producto.",
                new String[] { "Código", "Nombre", "Descripción", "Marca", "Precio" },
                new String[] { "Código", "Nombre", "Marca", "Precio" }, modeloProductos,
                tablaProductos, campos -> {
                    String codigo = texto(campos, "Código");
                    if (buscarProducto(codigo) != null) {
                        throw new IllegalArgumentException("Ese código de producto ya existe.");
                    }
                    double precio = decimal(texto(campos, "Precio"));
                    if (precio < 0) {
                        throw new IllegalArgumentException("El precio no puede ser negativo.");
                    }
                    producto nuevo = new producto(codigo, texto(campos, "Nombre"),
                            texto(campos, "Descripción"), texto(campos, "Marca"), precio);
                    productos.add(nuevo);
                    guardarConReversa(productos, nuevo);
                });
    }

    private JPanel crearTabClientes() {
        return crearTabSimple("Registrar cliente", "La fecha puede escribirse como día/mes/año.",
                new String[] { "DPI", "Nombre", "Dirección", "Fecha de nacimiento", "Tipo (Regular/VIP)" },
                new String[] { "DPI", "Nombre", "Dirección", "Nacimiento", "Tipo" }, modeloClientes,
                tablaClientes, campos -> {
                    String dpi = texto(campos, "DPI");
                    if (buscarCliente(dpi) != null) {
                        throw new IllegalArgumentException("Ese DPI ya está registrado.");
                    }
                    String tipo = texto(campos, "Tipo (Regular/VIP)");
                    if (!tipo.equalsIgnoreCase("Regular") && !tipo.equalsIgnoreCase("VIP")) {
                        throw new IllegalArgumentException("El tipo debe ser Regular o VIP.");
                    }
                    cliente nuevo = new cliente(dpi, texto(campos, "Nombre"),
                            texto(campos, "Dirección"), texto(campos, "Fecha de nacimiento"),
                            tipo.equalsIgnoreCase("VIP") ? "VIP" : "Regular");
                    clientes.add(nuevo);
                    guardarConReversa(clientes, nuevo);
                });
    }

    private JPanel crearTabEmpleados() {
        return crearTabSimple("Registrar empleado", "Registra a la persona encargada de una bodega.",
                new String[] { "ID empleado", "Nombre", "DPI", "Dirección", "Puesto", "Salario" },
                new String[] { "ID", "Nombre", "DPI", "Puesto", "Salario" }, modeloEmpleados,
                tablaEmpleados, campos -> {
                    String id = texto(campos, "ID empleado");
                    if (buscarEmpleado(id) != null) {
                        throw new IllegalArgumentException("Ese ID de empleado ya existe.");
                    }
                    double salario = decimal(texto(campos, "Salario"));
                    if (salario < 0) {
                        throw new IllegalArgumentException("El salario no puede ser negativo.");
                    }
                    Empleado nuevo = new Empleado(id, texto(campos, "Nombre"),
                            texto(campos, "DPI"), texto(campos, "Dirección"),
                            texto(campos, "Puesto"), salario);
                    empleados.add(nuevo);
                    guardarConReversa(empleados, nuevo);
                });
    }

    private JPanel crearTabBodegas() {
        return crearTabSimple("Registrar bodega", "Asigna una bodega a un empleado registrado.",
                new String[] { "ID bodega", "ID empleado encargado", "Capacidad (unidades)", "Dirección" },
                new String[] { "ID", "Encargado", "Capacidad", "Dirección" }, modeloBodegas,
                tablaBodegas, campos -> {
                    String id = texto(campos, "ID bodega");
                    if (buscarBodega(id) != null) {
                        throw new IllegalArgumentException("Ese ID de bodega ya existe.");
                    }
                    String idEmpleado = texto(campos, "ID empleado encargado");
                    if (buscarEmpleado(idEmpleado) == null) {
                        throw new IllegalArgumentException("Primero registra al empleado encargado.");
                    }
                    int capacidad = entero(texto(campos, "Capacidad (unidades)"));
                    if (capacidad <= 0) {
                        throw new IllegalArgumentException("La capacidad debe ser mayor que cero.");
                    }
                    Bodega nueva = new Bodega(id, idEmpleado, capacidad, texto(campos, "Dirección"));
                    bodegas.add(nueva);
                    guardarConReversa(bodegas, nueva);
                });
    }

    private JPanel crearTabSimple(String botonTexto, String ayuda, String[] nombresCampos,
            String[] columnas, DefaultTableModel modelo, JTable tabla,
            AccionFormulario accion) {
        JPanel panel = crearPanel();
        JPanel tarjeta = crearTarjeta(botonTexto, ayuda);
        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBackground(BLANCO);
        Map<String, JTextField> campos = new HashMap<>();
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 4, 5, 12);
        c.anchor = GridBagConstraints.WEST;

        for (int i = 0; i < nombresCampos.length; i++) {
            String nombre = nombresCampos[i];
            JTextField campo = new JTextField(22);
            estilizarCampo(campo);
            campos.put(nombre, campo);
            c.gridy = i;
            c.gridx = 0;
            c.weightx = 0;
            c.fill = GridBagConstraints.NONE;
            formulario.add(crearEtiqueta(nombre), c);
            c.gridx = 1;
            c.weightx = 1;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.insets = new Insets(5, 0, 5, 4);
            formulario.add(campo, c);
            c.insets = new Insets(5, 4, 5, 12);
        }

        JButton guardar = crearBoton(botonTexto);
        c.gridy = nombresCampos.length;
        c.gridx = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(12, 0, 0, 4);
        formulario.add(guardar, c);
        guardar.addActionListener(evento -> ejecutar(() -> {
            accion.guardar(campos);
            for (JTextField campo : campos.values()) {
                campo.setText("");
            }
        }));

        tarjeta.add(formulario, BorderLayout.CENTER);
        panel.add(tarjeta, BorderLayout.NORTH);
        panel.add(crearTablaConTitulo(tabla, "Registros"), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearTabInventario() {
        JPanel panel = crearPanel();
        JPanel tarjeta = crearTarjeta("Agregar existencias",
                "El total de unidades no puede superar la capacidad de la bodega.");
        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBackground(BLANCO);

        JTextField cantidad = new JTextField(12);
        estilizarCampo(cantidad);
        agregarFila(formulario, 0, "Bodega", bodegaInventario);
        agregarFila(formulario, 1, "Producto", productoInventario);
        agregarFila(formulario, 2, "Cantidad", cantidad);

        JButton agregar = crearBoton("Agregar al inventario");
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 3;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(12, 0, 0, 4);
        formulario.add(agregar, c);
        agregar.addActionListener(evento -> ejecutar(() -> {
            Bodega bodega = elemento(bodegaInventario, "Registra una bodega primero.");
            producto productoElegido = elemento(productoInventario, "Registra un producto primero.");
            int unidades = entero(cantidad.getText());
            if (unidades <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
            }
            agregarExistencias(bodega, productoElegido, unidades);
            cantidad.setText("");
        }));

        tarjeta.add(formulario, BorderLayout.CENTER);
        panel.add(tarjeta, BorderLayout.NORTH);
        panel.add(crearTablaConTitulo(tablaInventario, "Existencias"), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearTabVentas() {
        JPanel panel = crearPanel();
        JPanel tarjeta = crearTarjeta("Completar venta",
                "Cada orden lleva al menos un producto. El inventario se actualiza al vender.");
        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBackground(BLANCO);

        JTextField cantidad = new JTextField(12);
        estilizarCampo(cantidad);
        agregarFila(formulario, 0, "Cliente", clienteVenta);
        agregarFila(formulario, 1, "Bodega", bodegaVenta);
        agregarFila(formulario, 2, "Producto", productoVenta);
        agregarFila(formulario, 3, "Cantidad", cantidad);

        JButton vender = crearBoton("Registrar venta");
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 4;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(12, 0, 0, 4);
        formulario.add(vender, c);
        vender.addActionListener(evento -> ejecutar(() -> {
            cliente clienteElegido = elemento(clienteVenta, "Registra un cliente primero.");
            Bodega bodegaElegida = elemento(bodegaVenta, "Registra una bodega primero.");
            producto productoElegido = elemento(productoVenta, "Registra un producto primero.");
            int unidades = entero(cantidad.getText());
            completarVenta(clienteElegido, bodegaElegida, productoElegido, unidades);
            cantidad.setText("");
            JOptionPane.showMessageDialog(this, "La venta se guardó correctamente.");
        }));

        JButton cancelar = crearBotonSecundario("Cancelar orden seleccionada");
        cancelar.addActionListener(evento -> ejecutar(() -> cancelarOrdenSeleccionada()));
        JPanel botones = new JPanel(new BorderLayout());
        botones.setOpaque(false);
        botones.add(cancelar, BorderLayout.EAST);

        tarjeta.add(formulario, BorderLayout.CENTER);
        tarjeta.add(botones, BorderLayout.SOUTH);
        panel.add(tarjeta, BorderLayout.NORTH);
        panel.add(crearTablaConTitulo(tablaOrdenes, "Órdenes"), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanel() {
        JPanel panel = new JPanel(new BorderLayout(14, 14));
        panel.setBackground(FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));
        return panel;
    }

    private JPanel crearTarjeta(String titulo, String ayuda) {
        JPanel tarjeta = new JPanel(new BorderLayout(0, 12));
        tarjeta.setBackground(BLANCO);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 231, 236)),
                BorderFactory.createEmptyBorder(15, 17, 15, 17)));

        JPanel encabezado = new JPanel(new BorderLayout(0, 4));
        encabezado.setOpaque(false);
        JLabel tituloLabel = new JLabel(titulo);
        tituloLabel.setForeground(AZUL);
        tituloLabel.setFont(new Font("SansSerif", Font.BOLD, 17));
        JLabel ayudaLabel = new JLabel(ayuda);
        ayudaLabel.setForeground(new Color(105, 118, 130));
        ayudaLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        encabezado.add(tituloLabel, BorderLayout.NORTH);
        encabezado.add(ayudaLabel, BorderLayout.SOUTH);
        tarjeta.add(encabezado, BorderLayout.NORTH);
        return tarjeta;
    }

    private JPanel crearTablaConTitulo(JTable tabla, String titulo) {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setOpaque(false);
        JLabel etiqueta = new JLabel(titulo);
        etiqueta.setForeground(AZUL);
        etiqueta.setFont(new Font("SansSerif", Font.BOLD, 16));
        panel.add(etiqueta, BorderLayout.NORTH);

        JScrollPane desplazamiento = new JScrollPane(tabla);
        desplazamiento.setBorder(BorderFactory.createLineBorder(new Color(225, 231, 236)));
        desplazamiento.getViewport().setBackground(BLANCO);
        panel.add(desplazamiento, BorderLayout.CENTER);
        return panel;
    }

    private void agregarFila(JPanel panel, int fila, String nombre, java.awt.Component campo) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = fila;
        c.gridx = 0;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(5, 4, 5, 12);
        panel.add(crearEtiqueta(nombre), c);
        c.gridx = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 0, 5, 4);
        panel.add(campo, c);
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setForeground(TEXTO);
        etiqueta.setFont(new Font("SansSerif", Font.PLAIN, 13));
        return etiqueta;
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(VERDE);
        boton.setForeground(BLANCO);
        boton.setFont(new Font("SansSerif", Font.BOLD, 13));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(9, 14, 9, 14));
        return boton;
    }

    private JButton crearBotonSecundario(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(new Color(231, 238, 241));
        boton.setForeground(AZUL);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        return boton;
    }

    private void estilizarCampo(JTextField campo) {
        campo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        campo.setForeground(TEXTO);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 219, 226)),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));
    }

    private static DefaultTableModel crearModelo(String... columnas) {
        return new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
    }

    private static JTable crearTabla(DefaultTableModel modelo) {
        JTable tabla = new JTable(modelo);
        tabla.setFillsViewportHeight(true);
        tabla.setRowHeight(26);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tabla.setForeground(TEXTO);
        tabla.setSelectionBackground(new Color(218, 238, 233));
        tabla.setSelectionForeground(AZUL);
        tabla.setShowVerticalLines(false);
        tabla.setGridColor(new Color(232, 237, 241));
        JTableHeader encabezado = tabla.getTableHeader();
        encabezado.setBackground(AZUL);
        encabezado.setForeground(BLANCO);
        encabezado.setFont(new Font("SansSerif", Font.BOLD, 12));
        return tabla;
    }

    private void actualizarPantalla() {
        modeloProductos.setRowCount(0);
        for (producto item : productos) {
            modeloProductos.addRow(new Object[] { item.getCodigo(), item.getNombre(),
                    item.getMarca(), String.format("Q %.2f", item.getPrecio()) });
        }

        modeloClientes.setRowCount(0);
        for (cliente item : clientes) {
            modeloClientes.addRow(new Object[] { item.getDpi(), item.getNombre(),
                    item.getDireccion(), item.getFechaNacimiento(), item.getTipo() });
        }

        modeloEmpleados.setRowCount(0);
        for (Empleado item : empleados) {
            modeloEmpleados.addRow(new Object[] { item.getId(), item.getNombre(),
                    item.getDpi(), item.getPuesto(), String.format("Q %.2f", item.getSalario()) });
        }

        modeloBodegas.setRowCount(0);
        for (Bodega item : bodegas) {
            Empleado encargado = buscarEmpleado(item.getIdEmpleado());
            String nombre = encargado == null ? item.getIdEmpleado() : encargado.getNombre();
            modeloBodegas.addRow(new Object[] { item.getId(), nombre,
                    item.getCapacidad(), item.getDireccion() });
        }

        modeloInventario.setRowCount(0);
        for (Inventario item : inventarios) {
            producto encontrado = buscarProducto(item.getCodigoProducto());
            String nombre = encontrado == null ? item.getCodigoProducto() : encontrado.getNombre();
            modeloInventario.addRow(new Object[] { item.getIdBodega(),
                    item.getCodigoProducto(), nombre, item.getCantidad() });
        }

        modeloOrdenes.setRowCount(0);
        for (OrdenCompra item : ordenes) {
            modeloOrdenes.addRow(new Object[] { item.getId(), item.getDpiCliente(),
                    item.getCodigoProducto(), item.getCantidad(), item.getFecha(),
                    String.format("Q %.2f", item.getTotal()), item.getEstado() });
        }

        reemplazarElementos(productoInventario, productos);
        reemplazarElementos(productoVenta, productos);
        reemplazarElementos(clienteVenta, clientes);
        reemplazarElementos(bodegaInventario, bodegas);
        reemplazarElementos(bodegaVenta, bodegas);

    }

    private void ejecutar(Operacion operacion) {
        try {
            operacion.ejecutar();
            actualizarPantalla();
            estado.setText("  Cambios guardados en datos.txt");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Revisa los datos", JOptionPane.WARNING_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "No se pudieron guardar los datos:\n" + e.getMessage(),
                    "Error al guardar", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static String texto(Map<String, JTextField> campos, String nombre) {
        return validarTexto(campos.get(nombre).getText(), nombre);
    }

    private static String validarTexto(String valor, String nombre) {
        String texto = valor.trim();
        if (texto.isEmpty()) {
            throw new IllegalArgumentException("Completa el campo " + nombre + ".");
        }
        if (texto.contains("|")) {
            throw new IllegalArgumentException("No uses el símbolo | en los datos.");
        }
        return texto;
    }

    private static int entero(String valor) {
        try {
            return Integer.parseInt(valor.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Escribe una cantidad entera.");
        }
    }

    private static double decimal(String valor) {
        try {
            return Double.parseDouble(valor.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Escribe un número válido.");
        }
    }

    private static <T> T elemento(JComboBox<T> combo, String mensaje) {
        if (combo.getSelectedIndex() < 0) {
            throw new IllegalArgumentException(mensaje);
        }
        return combo.getItemAt(combo.getSelectedIndex());
    }

    private static <T> void reemplazarElementos(JComboBox<T> combo, List<T> datos) {
        combo.removeAllItems();
        for (T dato : datos) {
            combo.addItem(dato);
        }
    }

    private static producto buscarProducto(String codigo) {
        for (producto item : productos) {
            if (item.getCodigo().equalsIgnoreCase(codigo)) {
                return item;
            }
        }
        return null;
    }

    private static cliente buscarCliente(String dpi) {
        for (cliente item : clientes) {
            if (item.getDpi().equalsIgnoreCase(dpi)) {
                return item;
            }
        }
        return null;
    }

    private static Empleado buscarEmpleado(String id) {
        for (Empleado item : empleados) {
            if (item.getId().equalsIgnoreCase(id)) {
                return item;
            }
        }
        return null;
    }

    private static Bodega buscarBodega(String id) {
        for (Bodega item : bodegas) {
            if (item.getId().equalsIgnoreCase(id)) {
                return item;
            }
        }
        return null;
    }

    private static Inventario buscarInventario(String idBodega, String codigo) {
        for (Inventario item : inventarios) {
            if (item.getIdBodega().equalsIgnoreCase(idBodega)
                    && item.getCodigoProducto().equalsIgnoreCase(codigo)) {
                return item;
            }
        }
        return null;
    }

    private static int unidadesEnBodega(String idBodega) {
        int total = 0;
        for (Inventario item : inventarios) {
            if (item.getIdBodega().equalsIgnoreCase(idBodega)) {
                total += item.getCantidad();
            }
        }
        return total;
    }

    private void agregarExistencias(Bodega bodega, producto productoElegido, int cantidad)
            throws IOException {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }
        if (unidadesEnBodega(bodega.getId()) + cantidad > bodega.getCapacidad()) {
            throw new IllegalArgumentException("La bodega no tiene capacidad para esas unidades.");
        }
        Inventario item = buscarInventario(bodega.getId(), productoElegido.getCodigo());
        if (item == null) {
            item = new Inventario(bodega.getId(), productoElegido.getCodigo(), cantidad);
            inventarios.add(item);
            guardarConReversa(inventarios, item);
        } else {
            int anterior = item.getCantidad();
            item.setCantidad(anterior + cantidad);
            try {
                guardarDatos();
            } catch (IOException e) {
                item.setCantidad(anterior);
                throw e;
            }
        }
    }

    static void completarVenta(cliente clienteElegido, Bodega bodegaElegida,
            producto productoElegido, int cantidad) throws IOException {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }
        Inventario item = buscarInventario(bodegaElegida.getId(), productoElegido.getCodigo());
        if (item == null || item.getCantidad() < cantidad) {
            throw new IllegalArgumentException("No hay suficientes unidades en esa bodega.");
        }

        int anterior = item.getCantidad();
        item.setCantidad(anterior - cantidad);
        String id = "ORD-" + (ordenes.size() + 1);
        OrdenCompra orden = new OrdenCompra(id, clienteElegido.getDpi(),
                productoElegido.getCodigo(), cantidad, LocalDate.now().toString(),
                productoElegido.getPrecio() * cantidad, "Completada", bodegaElegida.getId());
        ordenes.add(orden);
        try {
            guardarDatos();
        } catch (IOException e) {
            item.setCantidad(anterior);
            ordenes.remove(orden);
            throw e;
        }
    }

    private void cancelarOrdenSeleccionada() throws IOException {
        int fila = tablaOrdenes.getSelectedRow();
        if (fila < 0) {
            throw new IllegalArgumentException("Selecciona una orden de la tabla.");
        }
        String id = modeloOrdenes.getValueAt(fila, 0).toString();
        OrdenCompra orden = buscarOrden(id);
        if (orden == null) {
            throw new IllegalArgumentException("La orden no existe o ya fue cancelada.");
        }
        cancelarVenta(orden);
    }

    static void cancelarVenta(OrdenCompra orden) throws IOException {
        if (orden.getEstado().equals("Cancelada")) {
            throw new IllegalArgumentException("La orden ya fue cancelada.");
        }
        Bodega bodega = buscarBodega(orden.getIdBodega());
        if (bodega == null || unidadesEnBodega(bodega.getId()) + orden.getCantidad() > bodega.getCapacidad()) {
            throw new IllegalArgumentException("No hay espacio en la bodega para devolver el producto.");
        }

        Inventario item = buscarInventario(orden.getIdBodega(), orden.getCodigoProducto());
        int cantidadAnterior = 0;
        if (item == null) {
            item = new Inventario(orden.getIdBodega(), orden.getCodigoProducto(), orden.getCantidad());
            inventarios.add(item);
        } else {
            cantidadAnterior = item.getCantidad();
            item.setCantidad(cantidadAnterior + orden.getCantidad());
        }

        orden.setEstado("Cancelada");
        try {
            guardarDatos();
        } catch (IOException e) {
            orden.setEstado("Completada");
            if (cantidadAnterior == 0) {
                inventarios.remove(item);
            } else {
                item.setCantidad(cantidadAnterior);
            }
            throw e;
        }
    }

    private static OrdenCompra buscarOrden(String id) {
        for (OrdenCompra item : ordenes) {
            if (item.getId().equalsIgnoreCase(id)) {
                return item;
            }
        }
        return null;
    }

    private static <T> void guardarConReversa(List<T> lista, T elemento) throws IOException {
        try {
            guardarDatos();
        } catch (IOException e) {
            lista.remove(elemento);
            throw e;
        }
    }

    static void cargarDatos() throws IOException {
        productos.clear();
        clientes.clear();
        empleados.clear();
        bodegas.clear();
        inventarios.clear();
        ordenes.clear();

        if (!Files.exists(Paths.get("datos.txt"))) {
            return;
        }

        List<String> lineas = Files.readAllLines(Paths.get("datos.txt"), StandardCharsets.UTF_8);
        for (int i = 0; i < lineas.size(); i++) {
            String linea = lineas.get(i).trim();
            if (linea.isEmpty()) {
                continue;
            }
            try {
                leerRegistro(linea);
            } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
                throw new IOException("Revisa la línea " + (i + 1) + " de datos.txt.", e);
            }
        }
    }

    private static void leerRegistro(String linea) {
        String[] partes = linea.split("\\|", -1);
        String tipo = partes[0].trim();

        if (tipo.equals("P") || tipo.equals("PRODUCTOS")) {
            productos.add(new producto(campo(partes, 1), campo(partes, 2), campo(partes, 3),
                    campo(partes, 4), Double.parseDouble(campo(partes, 5))));
        } else if (tipo.equals("C") || tipo.equals("CLIENTES")) {
            clientes.add(new cliente(campo(partes, 1), campo(partes, 2), campo(partes, 3),
                    campo(partes, 4), campo(partes, 5)));
        } else if (tipo.equals("E") || tipo.equals("EMPLEADOS")) {
            empleados.add(new Empleado(campo(partes, 1), campo(partes, 2), campo(partes, 3),
                    campo(partes, 4), campo(partes, 5), Double.parseDouble(campo(partes, 6))));
        } else if (tipo.equals("B") || tipo.equals("BODEGAS")) {
            bodegas.add(new Bodega(campo(partes, 1), campo(partes, 2),
                    Integer.parseInt(campo(partes, 3)), campo(partes, 4)));
        } else if (tipo.equals("I") || tipo.equals("INVENTARIO")) {
            inventarios.add(new Inventario(campo(partes, 1), campo(partes, 2),
                    Integer.parseInt(campo(partes, 3))));
        } else if (tipo.equals("O") || tipo.trim().equals("ORDENES")) {
            ordenes.add(new OrdenCompra(campo(partes, 1), campo(partes, 2), campo(partes, 3),
                    Integer.parseInt(campo(partes, 4)), campo(partes, 5),
                    Double.parseDouble(campo(partes, 6)), campo(partes, 7), campo(partes, 8)));
        } else {
            throw new IllegalArgumentException("Tipo de registro desconocido.");
        }
    }

    private static String campo(String[] partes, int posicion) {
        if (posicion >= partes.length) {
            throw new IllegalArgumentException("Faltan datos.");
        }
        String valor = partes[posicion].trim();
        int dosPuntos = valor.indexOf(':');
        if (dosPuntos >= 0) {
            valor = valor.substring(dosPuntos + 1).trim();
        }
        return valor;
    }

    static void guardarDatos() throws IOException {
        ArrayList<String> lineas = new ArrayList<>();
        for (producto item : productos) {
            lineas.add("P|" + item.getCodigo() + "|" + item.getNombre() + "|"
                    + item.getDescripcion() + "|" + item.getMarca() + "|" + item.getPrecio());
        }
        for (cliente item : clientes) {
            lineas.add("C|" + item.getDpi() + "|" + item.getNombre() + "|" + item.getDireccion()
                    + "|" + item.getFechaNacimiento() + "|" + item.getTipo());
        }
        for (Empleado item : empleados) {
            lineas.add("E|" + item.getId() + "|" + item.getNombre() + "|" + item.getDpi()
                    + "|" + item.getDireccion() + "|" + item.getPuesto() + "|" + item.getSalario());
        }
        for (Bodega item : bodegas) {
            lineas.add("B|" + item.getId() + "|" + item.getIdEmpleado() + "|"
                    + item.getCapacidad() + "|" + item.getDireccion());
        }
        for (Inventario item : inventarios) {
            lineas.add("I|" + item.getIdBodega() + "|" + item.getCodigoProducto()
                    + "|" + item.getCantidad());
        }
        for (OrdenCompra item : ordenes) {
            lineas.add("O|" + item.getId() + "|" + item.getDpiCliente() + "|"
                    + item.getCodigoProducto() + "|" + item.getCantidad() + "|" + item.getFecha()
                    + "|" + item.getTotal() + "|" + item.getEstado() + "|" + item.getIdBodega());
        }
        Files.write(Paths.get("datos.txt"), lineas, StandardCharsets.UTF_8);
    }

    @FunctionalInterface
    private interface AccionFormulario {
        void guardar(Map<String, JTextField> campos) throws IOException;
    }

    @FunctionalInterface
    private interface Operacion {
        void ejecutar() throws IOException;
    }
}
