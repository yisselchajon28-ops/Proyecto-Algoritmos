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

// Esta clase muestra la ventana principal y conecta los botones con el sistema.
// JFrame es la ventana de Java Swing.
public class main extends JFrame {
    private static final long serialVersionUID = 1L;

    // Colores que se usan en las diferentes partes de la ventana.
    private static final Color AZUL = new Color(40, 61, 82);
    private static final Color VERDE = new Color(43, 135, 120);
    private static final Color FONDO = new Color(245, 247, 249);
    private static final Color BLANCO = Color.WHITE;
    private static final Color TEXTO = new Color(51, 65, 78);

    // Estas listas mantienen temporalmente los datos mientras el programa está abierto.
    // Cada lista contiene objetos de una clase diferente.
    static ArrayList<producto> productos = new ArrayList<>();
    static ArrayList<cliente> clientes = new ArrayList<>();
    static ArrayList<Empleado> empleados = new ArrayList<>();
    static ArrayList<Bodega> bodegas = new ArrayList<>();
    static ArrayList<Inventario> inventarios = new ArrayList<>();
    static ArrayList<OrdenCompra> ordenes = new ArrayList<>();

    // Cada modelo indica las columnas que se muestran en una tabla.
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

    // Las tablas muestran los datos que están guardados en las listas.
    private final JTable tablaProductos = crearTabla(modeloProductos);
    private final JTable tablaClientes = crearTabla(modeloClientes);
    private final JTable tablaEmpleados = crearTabla(modeloEmpleados);
    private final JTable tablaBodegas = crearTabla(modeloBodegas);
    private final JTable tablaInventario = crearTabla(modeloInventario);
    private final JTable tablaOrdenes = crearTabla(modeloOrdenes);

    // Las listas desplegables permiten elegir registros existentes en formularios.
    private final JComboBox<producto> productoInventario = new JComboBox<>();
    private final JComboBox<Bodega> bodegaInventario = new JComboBox<>();
    private final JComboBox<cliente> clienteVenta = new JComboBox<>();
    private final JComboBox<producto> productoVenta = new JComboBox<>();
    private final JComboBox<Bodega> bodegaVenta = new JComboBox<>();

    // Mensaje pequeño que aparece abajo para indicar el estado del programa.
    private final JLabel estado = new JLabel("Listo");

    // Punto de entrada: carga los datos y luego abre la ventana.
    public static void main(String[] args) {
        try {
            // Se leen los registros guardados en datos.txt antes de mostrar la ventana.
            cargarDatos();
        } catch (IOException e) {
            // Si el archivo no se puede leer, se muestra un error entendible.
            JOptionPane.showMessageDialog(null,
                    "No se pudieron leer los datos.txt:\n" + e.getMessage(),
                    "Error al abrir los datos", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Swing crea la ventana en su propio hilo de eventos.
        SwingUtilities.invokeLater(() -> new main().setVisible(true));
    }

    // Constructor: configura el tamaño, el título y las partes principales de la ventana.
    public main() {
        setTitle("SISTEMA | Ventas e inventario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(790, 560));
        setSize(1100, 720);
        setLocationRelativeTo(null);

        JPanel principal = new JPanel(new BorderLayout(0, 0));
        principal.setBackground(FONDO);
        // La cabecera va arriba, las pestañas al centro y el mensaje de estado abajo.
        principal.add(crearEncabezado(), BorderLayout.NORTH);
        principal.add(crearPestanas(), BorderLayout.CENTER);

        estado.setBorder(BorderFactory.createEmptyBorder(9, 18, 9, 18));
        estado.setForeground(TEXTO);
        estado.setBackground(BLANCO);
        estado.setOpaque(true);
        principal.add(estado, BorderLayout.SOUTH);

        setContentPane(principal);
        // Llena las tablas y listas desplegables con los datos que se cargaron.
        actualizarPantalla();
    }

    // Crea la franja superior con el nombre del sistema.
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

    // Crea las pestañas que separan las funciones del programa.
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

    // Crea la pantalla inicial sencilla con el nombre y el saludo.
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

    // Prepara el formulario y la tabla para registrar productos.
    private JPanel crearTabProductos() {
        return crearTabSimple("Registrar producto", "Completa los datos básicos del producto.",
                new String[] { "Código", "Nombre", "Descripción", "Marca", "Precio" },
                new String[] { "Código", "Nombre", "Marca", "Precio" }, modeloProductos,
                tablaProductos, campos -> {
                    // El código identifica al producto y no debe repetirse.
                    String codigo = texto(campos, "Código");
                    if (buscarProducto(codigo) != null) {
                        throw new IllegalArgumentException("Ese código de producto ya existe.");
                    }
                    // Convertimos el precio de texto a número antes de crear el objeto.
                    double precio = decimal(texto(campos, "Precio"));
                    if (precio < 0) {
                        throw new IllegalArgumentException("El precio no puede ser negativo.");
                    }
                    producto nuevo = new producto(codigo, texto(campos, "Nombre"),
                            texto(campos, "Descripción"), texto(campos, "Marca"), precio);
                    productos.add(nuevo);
                    // Si guardar falla, se quita el producto recién agregado.
                    guardarConReversa(productos, nuevo);
                });
    }

    // Prepara el formulario y la tabla para registrar clientes.
    private JPanel crearTabClientes() {
        return crearTabSimple("Registrar cliente", "La fecha puede escribirse como día/mes/año.",
                new String[] { "DPI", "Nombre", "Dirección", "Fecha de nacimiento", "Tipo (Regular/VIP)" },
                new String[] { "DPI", "Nombre", "Dirección", "Nacimiento", "Tipo" }, modeloClientes,
                tablaClientes, campos -> {
                    // El DPI sirve para encontrar e identificar a cada cliente.
                    String dpi = texto(campos, "DPI");
                    if (buscarCliente(dpi) != null) {
                        throw new IllegalArgumentException("Ese DPI ya está registrado.");
                    }
                    // Solo aceptamos las dos categorías solicitadas para el cliente.
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

    // Prepara el formulario y la tabla para registrar empleados.
    private JPanel crearTabEmpleados() {
        return crearTabSimple("Registrar empleado", "Registra a la persona encargada de una bodega.",
                new String[] { "ID empleado", "Nombre", "DPI", "Dirección", "Puesto", "Salario" },
                new String[] { "ID", "Nombre", "DPI", "Puesto", "Salario" }, modeloEmpleados,
                tablaEmpleados, campos -> {
                    // Verificamos que el ID del empleado no esté ocupado.
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

    // Prepara el formulario y la tabla para registrar bodegas.
    private JPanel crearTabBodegas() {
        return crearTabSimple("Registrar bodega", "Asigna una bodega a un empleado registrado.",
                new String[] { "ID bodega", "ID empleado encargado", "Capacidad (unidades)", "Dirección" },
                new String[] { "ID", "Encargado", "Capacidad", "Dirección" }, modeloBodegas,
                tablaBodegas, campos -> {
                    // Una bodega necesita un ID único y un empleado ya registrado.
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

    // Método reutilizado por Productos, Clientes, Empleados y Bodegas.
    // Recibe etiquetas, columnas y una acción que guarda el tipo de dato elegido.
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

        // Se crea una fila de formulario por cada dato que se necesita pedir.
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
        // Al pulsar el botón, guarda los campos y luego los deja vacíos.
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

    // Crea la pestaña para agregar unidades de productos a una bodega.
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
            // Se comprueba que el usuario haya escogido una bodega y un producto.
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

    // Crea la pestaña de ventas y el botón para cancelar una orden seleccionada.
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
            // Se toman los elementos elegidos en las listas y la cantidad escrita.
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

    // Crea el contenedor base que usan las pestañas para mantener márgenes y color.
    private JPanel crearPanel() {
        JPanel panel = new JPanel(new BorderLayout(14, 14));
        panel.setBackground(FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));
        return panel;
    }

    // Dibuja el recuadro blanco que agrupa un formulario y su texto de ayuda.
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

    // Pone un título y una barra de desplazamiento alrededor de una tabla.
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

    // Añade una etiqueta y su campo en la misma fila de un formulario.
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

    // Crea una etiqueta con el mismo estilo que usan los demás formularios.
    private JLabel crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setForeground(TEXTO);
        etiqueta.setFont(new Font("SansSerif", Font.PLAIN, 13));
        return etiqueta;
    }

    // Crea el botón principal verde usado para guardar o registrar.
    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(VERDE);
        boton.setForeground(BLANCO);
        boton.setFont(new Font("SansSerif", Font.BOLD, 13));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(9, 14, 9, 14));
        return boton;
    }

    // Crea un botón menos llamativo para acciones secundarias.
    private JButton crearBotonSecundario(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(new Color(231, 238, 241));
        boton.setForeground(AZUL);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        return boton;
    }

    // Aplica letra, color y borde al campo de texto recibido.
    private void estilizarCampo(JTextField campo) {
        campo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        campo.setForeground(TEXTO);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 219, 226)),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));
    }

    // Prepara una tabla con las columnas recibidas y bloquea la edición manual.
    private static DefaultTableModel crearModelo(String... columnas) {
        return new DefaultTableModel(columnas, 0) {
            @Override
            // Evita que el usuario cambie datos directamente en una celda.
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
    }

    // Construye una tabla con el modelo y el estilo común del sistema.
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

    // Vacía y vuelve a llenar tablas y listas desplegables desde las listas de datos.
    // Se llama al iniciar y después de cada operación exitosa.
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

    // Ejecuta una acción de la interfaz, actualiza la pantalla y muestra errores.
    private void ejecutar(Operacion operacion) {
        try {
            operacion.ejecutar();
            actualizarPantalla();
            estado.setText("  Cambios guardados en datos.txt");
        } catch (IllegalArgumentException e) {
            // Estos errores son datos incorrectos o reglas de negocio, no fallos de archivo.
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Revisa los datos", JOptionPane.WARNING_MESSAGE);
        } catch (IOException e) {
            // Este error ocurre cuando Java no puede leer o escribir el archivo.
            JOptionPane.showMessageDialog(this, "No se pudieron guardar los datos:\n" + e.getMessage(),
                    "Error al guardar", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Obtiene el texto de un campo del formulario y lo valida.
    private static String texto(Map<String, JTextField> campos, String nombre) {
        return validarTexto(campos.get(nombre).getText(), nombre);
    }

    // Rechaza campos vacíos y el separador que utiliza el archivo de datos.
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

    // Convierte el texto a número entero; si no se puede, informa al usuario.
    private static int entero(String valor) {
        try {
            return Integer.parseInt(valor.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Escribe una cantidad entera.");
        }
    }

    // Convierte el texto a decimal y permite escribir coma o punto.
    private static double decimal(String valor) {
        try {
            return Double.parseDouble(valor.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Escribe un número válido.");
        }
    }

    // Devuelve el objeto elegido de una lista; informa si aún no hay opciones.
    private static <T> T elemento(JComboBox<T> combo, String mensaje) {
        if (combo.getSelectedIndex() < 0) {
            throw new IllegalArgumentException(mensaje);
        }
        return combo.getItemAt(combo.getSelectedIndex());
    }

    // Borra las opciones viejas y copia las opciones actuales a una lista desplegable.
    private static <T> void reemplazarElementos(JComboBox<T> combo, List<T> datos) {
        combo.removeAllItems();
        for (T dato : datos) {
            combo.addItem(dato);
        }
    }

    // Busca el producto por código; devuelve null cuando no existe.
    private static producto buscarProducto(String codigo) {
        for (producto item : productos) {
            if (item.getCodigo().equalsIgnoreCase(codigo)) {
                return item;
            }
        }
        return null;
    }

    // Busca el cliente por DPI; devuelve null cuando no existe.
    private static cliente buscarCliente(String dpi) {
        for (cliente item : clientes) {
            if (item.getDpi().equalsIgnoreCase(dpi)) {
                return item;
            }
        }
        return null;
    }

    // Busca el empleado por su ID; devuelve null cuando no existe.
    private static Empleado buscarEmpleado(String id) {
        for (Empleado item : empleados) {
            if (item.getId().equalsIgnoreCase(id)) {
                return item;
            }
        }
        return null;
    }

    // Busca la bodega por ID; devuelve null cuando no existe.
    private static Bodega buscarBodega(String id) {
        for (Bodega item : bodegas) {
            if (item.getId().equalsIgnoreCase(id)) {
                return item;
            }
        }
        return null;
    }

    // Busca las existencias de un producto dentro de una bodega específica.
    private static Inventario buscarInventario(String idBodega, String codigo) {
        for (Inventario item : inventarios) {
            if (item.getIdBodega().equalsIgnoreCase(idBodega)
                    && item.getCodigoProducto().equalsIgnoreCase(codigo)) {
                return item;
            }
        }
        return null;
    }

    // Suma cuántas unidades de todos los productos tiene una bodega.
    private static int unidadesEnBodega(String idBodega) {
        int total = 0;
        for (Inventario item : inventarios) {
            if (item.getIdBodega().equalsIgnoreCase(idBodega)) {
                total += item.getCantidad();
            }
        }
        return total;
    }

    // Agrega stock, revisando que la cantidad y capacidad de bodega sean válidas.
    private void agregarExistencias(Bodega bodega, producto productoElegido, int cantidad)
            throws IOException {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }
        if (unidadesEnBodega(bodega.getId()) + cantidad > bodega.getCapacidad()) {
            throw new IllegalArgumentException("La bodega no tiene capacidad para esas unidades.");
        }
        // Si el producto ya tiene una fila de inventario, se suman las unidades.
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

    // Registra una orden completada y descuenta del inventario las unidades vendidas.
    static void completarVenta(cliente clienteElegido, Bodega bodegaElegida,
            producto productoElegido, int cantidad) throws IOException {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }
        Inventario item = buscarInventario(bodegaElegida.getId(), productoElegido.getCodigo());
        if (item == null || item.getCantidad() < cantidad) {
            throw new IllegalArgumentException("No hay suficientes unidades en esa bodega.");
        }

        // Conservamos la cantidad anterior para poder deshacer el cambio si falla el guardado.
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

    // Lee la fila que se seleccionó en la tabla y manda a cancelar esa orden.
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

    // Cambia el estado de una orden y devuelve su cantidad al inventario.
    static void cancelarVenta(OrdenCompra orden) throws IOException {
        if (orden.getEstado().equals("Cancelada")) {
            throw new IllegalArgumentException("La orden ya fue cancelada.");
        }
        Bodega bodega = buscarBodega(orden.getIdBodega());
        if (bodega == null || unidadesEnBodega(bodega.getId()) + orden.getCantidad() > bodega.getCapacidad()) {
            throw new IllegalArgumentException("No hay espacio en la bodega para devolver el producto.");
        }

        // Si ya no existe la fila del producto en inventario, se crea otra.
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

    // Encuentra una orden por su identificador.
    private static OrdenCompra buscarOrden(String id) {
        for (OrdenCompra item : ordenes) {
            if (item.getId().equalsIgnoreCase(id)) {
                return item;
            }
        }
        return null;
    }

    // Si guardar falla, quita el elemento agregado para que la memoria no mienta.
    private static <T> void guardarConReversa(List<T> lista, T elemento) throws IOException {
        try {
            guardarDatos();
        } catch (IOException e) {
            lista.remove(elemento);
            throw e;
        }
    }

    // Lee datos.txt al iniciar y reconstruye las listas de objetos del programa.
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

        // Primero se leen las líneas completas; después se interpreta cada registro.
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

    // Convierte una línea del archivo en un objeto y lo agrega a la lista correcta.
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

    // Obtiene una columna de la línea; también quita etiquetas antiguas como "Código:".
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

    // Convierte todos los objetos a líneas de texto y las escribe en datos.txt.
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
        // Cada llamada reescribe el archivo con la versión más reciente de las listas.
        Files.write(Paths.get("datos.txt"), lineas, StandardCharsets.UTF_8);
    }

    // Define cómo cada formulario guarda sus campos; la interfaz funcional permite usar lambdas.
    @FunctionalInterface
    private interface AccionFormulario {
        // Recibe los campos y ejecuta el registro que corresponda.
        void guardar(Map<String, JTextField> campos) throws IOException;
    }

    // Define una operación que puede fallar al guardar datos.
    @FunctionalInterface
    private interface Operacion {
        // Se ejecuta al pulsar un botón de la ventana.
        void ejecutar() throws IOException;
    }
}
