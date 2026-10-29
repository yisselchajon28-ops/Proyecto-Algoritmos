import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class main {
    static Scanner teclado = new Scanner(System.in);
    static ArrayList<producto> productos = new ArrayList<>();
    static ArrayList<cliente> clientes = new ArrayList<>();
    static ArrayList<Empleado> empleados = new ArrayList<>();
    static ArrayList<Bodega> bodegas = new ArrayList<>();
    static ArrayList<Inventario> inventarios = new ArrayList<>();
    static ArrayList<OrdenCompra> ordenes = new ArrayList<>();

    public static void main(String[] args) {
        cargarDatos();

        int opcion = 0;
        while (opcion != 9) {
            System.out.println("\n=== SISTEMA DE VENTAS ===");
            System.out.println("1. Registrar producto");
            System.out.println("2. Registrar cliente");
            System.out.println("3. Registrar empleado");
            System.out.println("4. Registrar bodega");
            System.out.println("5. Agregar productos a bodega");
            System.out.println("6. Registrar venta");
            System.out.println("7. Ver inventario");
            System.out.println("8. Ver o cancelar órdenes");
            System.out.println("9. Salir");
            opcion = leerEntero("Elija una opción: ");

            switch (opcion) {
                case 1:
                    registrarProducto();
                    break;
                case 2:
                    registrarCliente();
                    break;
                case 3:
                    registrarEmpleado();
                    break;
                case 4:
                    registrarBodega();
                    break;
                case 5:
                    agregarInventario();
                    break;
                case 6:
                    registrarVenta();
                    break;
                case 7:
                    verInventario();
                    break;
                case 8:
                    verOrdenes();
                    break;
                case 9:
                    System.out.println("Hasta luego.");
                    break;
                default:
                    System.out.println("Opción incorrecta.");
            }
        }
    }

    static void registrarProducto() {
        System.out.println("\n--- Nuevo producto ---");
        String codigo = leerTexto("Código: ");
        if (buscarProducto(codigo) != null) {
            System.out.println("Ese código ya existe.");
            return;
        }
        String nombre = leerTexto("Nombre: ");
        String descripcion = leerTexto("Descripción: ");
        String marca = leerTexto("Marca: ");
        double precio = leerDecimal("Precio: ");
        if (precio < 0) {
            System.out.println("El precio no puede ser negativo.");
            return;
        }
        productos.add(new producto(codigo, nombre, descripcion, marca, precio));
        guardarDatos();
    }

    static void registrarCliente() {
        System.out.println("\n--- Nuevo cliente ---");
        String dpi = leerTexto("DPI: ");
        if (buscarCliente(dpi) != null) {
            System.out.println("Ese DPI ya existe.");
            return;
        }
        String nombre = leerTexto("Nombre: ");
        String direccion = leerTexto("Dirección: ");
        String fecha = leerTexto("Fecha de nacimiento (AAAA-MM-DD): ");
        String tipo = leerTexto("Tipo (Regular o VIP): ");
        if (!tipo.equalsIgnoreCase("Regular") && !tipo.equalsIgnoreCase("VIP")) {
            System.out.println("El tipo debe ser Regular o VIP.");
            return;
        }
        clientes.add(new cliente(dpi, nombre, direccion, fecha, tipo));
        guardarDatos();
    }

    static void registrarEmpleado() {
        System.out.println("\n--- Nuevo empleado ---");
        String id = leerTexto("ID empleado: ");
        if (buscarEmpleado(id) != null) {
            System.out.println("Ese ID ya existe.");
            return;
        }
        String nombre = leerTexto("Nombre: ");
        String dpi = leerTexto("DPI: ");
        String direccion = leerTexto("Dirección: ");
        String puesto = leerTexto("Puesto: ");
        double salario = leerDecimal("Salario: ");
        if (salario < 0) {
            System.out.println("El salario no puede ser negativo.");
            return;
        }
        empleados.add(new Empleado(id, nombre, dpi, direccion, puesto, salario));
        guardarDatos();
    }

    static void registrarBodega() {
        System.out.println("\n--- Nueva bodega ---");
        if (empleados.isEmpty()) {
            System.out.println("Primero registre un empleado.");
            return;
        }
        String id = leerTexto("ID bodega: ");
        if (buscarBodega(id) != null) {
            System.out.println("Ese ID ya existe.");
            return;
        }
        verEmpleados();
        String idEmpleado = leerTexto("ID del empleado encargado: ");
        if (buscarEmpleado(idEmpleado) == null) {
            System.out.println("No se encontró ese empleado.");
            return;
        }
        int capacidad = leerEntero("Capacidad en unidades: ");
        if (capacidad <= 0) {
            System.out.println("La capacidad debe ser mayor que cero.");
            return;
        }
        String direccion = leerTexto("Dirección: ");
        bodegas.add(new Bodega(id, idEmpleado, capacidad, direccion));
        guardarDatos();
    }

    static void agregarInventario() {
        System.out.println("\n--- Agregar productos a una bodega ---");
        if (productos.isEmpty() || bodegas.isEmpty()) {
            System.out.println("Registre primero productos y bodegas.");
            return;
        }
        verBodegas();
        String idBodega = leerTexto("ID bodega: ");
        Bodega bodega = buscarBodega(idBodega);
        if (bodega == null) {
            System.out.println("No se encontró esa bodega.");
            return;
        }
        verProductos();
        String codigo = leerTexto("Código de producto: ");
        if (buscarProducto(codigo) == null) {
            System.out.println("No se encontró ese producto.");
            return;
        }
        int cantidad = leerEntero("Cantidad a agregar: ");
        if (cantidad <= 0) {
            System.out.println("La cantidad debe ser mayor que cero.");
            return;
        }
        Inventario item = buscarInventario(idBodega, codigo);
        int cantidadActual = 0;
        if (item != null) {
            cantidadActual = item.getCantidad();
        }
        if (totalEnBodega(idBodega) + cantidad > bodega.getCapacidad()) {
            System.out.println("La cantidad supera la capacidad de la bodega.");
            return;
        }
        if (item == null) {
            inventarios.add(new Inventario(idBodega, codigo, cantidad));
        } else {
            item.setCantidad(cantidadActual + cantidad);
        }
        guardarDatos();
    }

    static void registrarVenta() {
        System.out.println("\n--- Nueva venta ---");
        if (clientes.isEmpty() || bodegas.isEmpty() || productos.isEmpty()) {
            System.out.println("Registre clientes, productos y bodegas antes de vender.");
            return;
        }
        verClientes();
        String dpi = leerTexto("DPI del cliente: ");
        if (buscarCliente(dpi) == null) {
            System.out.println("No se encontró ese cliente.");
            return;
        }
        verBodegas();
        String idBodega = leerTexto("ID bodega: ");
        if (buscarBodega(idBodega) == null) {
            System.out.println("No se encontró esa bodega.");
            return;
        }
        verProductos();
        String codigo = leerTexto("Código del producto: ");
        producto productoVendido = buscarProducto(codigo);
        if (productoVendido == null) {
            System.out.println("No se encontró ese producto.");
            return;
        }
        int cantidad = leerEntero("Cantidad: ");
        if (cantidad <= 0) {
            System.out.println("La cantidad debe ser mayor que cero.");
            return;
        }
        Inventario item = buscarInventario(idBodega, codigo);
        if (item == null || item.getCantidad() < cantidad) {
            System.out.println("No hay suficientes unidades en esa bodega.");
            return;
        }

        item.setCantidad(item.getCantidad() - cantidad);
        String idOrden = "ORD-" + (ordenes.size() + 1);
        double total = productoVendido.getPrecio() * cantidad;
        ordenes.add(new OrdenCompra(idOrden, dpi, codigo, cantidad,
                LocalDate.now().toString(), total, "Completada", idBodega));
        guardarDatos();
        System.out.println("Venta completada. Total: Q " + total);
    }

    static void verInventario() {
        System.out.println("\n--- Inventario ---");
        if (inventarios.isEmpty()) {
            System.out.println("No hay productos en inventario.");
        }
        for (Inventario item : inventarios) {
            producto productoActual = buscarProducto(item.getCodigoProducto());
            String nombre = "Producto no encontrado";
            if (productoActual != null) {
                nombre = productoActual.getNombre();
            }
            System.out.println("Bodega " + item.getIdBodega() + " | "
                    + item.getCodigoProducto() + " - " + nombre
                    + " | Cantidad: " + item.getCantidad());
        }
    }

    static void verOrdenes() {
        System.out.println("\n--- Órdenes ---");
        if (ordenes.isEmpty()) {
            System.out.println("No hay órdenes registradas.");
            return;
        }
        for (OrdenCompra orden : ordenes) {
            System.out.println(orden.getId() + " | Cliente: " + orden.getDpiCliente()
                    + " | Producto: " + orden.getCodigoProducto()
                    + " | Cantidad: " + orden.getCantidad()
                    + " | Fecha: " + orden.getFecha()
                    + " | Total: Q " + orden.getTotal()
                    + " | Estado: " + orden.getEstado());
        }
        String id = leerTexto("Escriba el ID de una orden para cancelarla, o 0 para volver: ");
        if (id.equals("0")) {
            return;
        }
        OrdenCompra orden = buscarOrden(id);
        if (orden == null) {
            System.out.println("No se encontró esa orden.");
        } else if (orden.getEstado().equals("Cancelada")) {
            System.out.println("La orden ya está cancelada.");
        } else {
            Bodega bodega = buscarBodega(orden.getIdBodega());
            if (bodega == null || totalEnBodega(orden.getIdBodega()) + orden.getCantidad()
                    > bodega.getCapacidad()) {
                System.out.println("No hay espacio suficiente para devolver el producto.");
                return;
            }
            Inventario item = buscarInventario(orden.getIdBodega(), orden.getCodigoProducto());
            if (item == null) {
                inventarios.add(new Inventario(orden.getIdBodega(),
                        orden.getCodigoProducto(), orden.getCantidad()));
            } else {
                item.setCantidad(item.getCantidad() + orden.getCantidad());
            }
            orden.setEstado("Cancelada");
            guardarDatos();
            System.out.println("Orden cancelada. El producto volvió al inventario.");
        }
    }

    static void verProductos() {
        System.out.println("--- Productos ---");
        for (producto item : productos) {
            System.out.println(item.getCodigo() + " - " + item.getNombre()
                    + " | Precio: Q " + item.getPrecio());
        }
    }

    static void verClientes() {
        System.out.println("--- Clientes ---");
        for (cliente item : clientes) {
            System.out.println(item.getDpi() + " - " + item.getNombre()
                    + " | Tipo: " + item.getTipo());
        }
    }

    static void verEmpleados() {
        System.out.println("--- Empleados ---");
        for (Empleado item : empleados) {
            System.out.println(item.getId() + " - " + item.getNombre());
        }
    }

    static void verBodegas() {
        System.out.println("--- Bodegas ---");
        for (Bodega item : bodegas) {
            System.out.println(item.getId() + " - " + item.getDireccion());
        }
    }

    static producto buscarProducto(String codigo) {
        for (producto item : productos) {
            if (item.getCodigo().equals(codigo)) {
                return item;
            }
        }
        return null;
    }

    static cliente buscarCliente(String dpi) {
        for (cliente item : clientes) {
            if (item.getDpi().equals(dpi)) {
                return item;
            }
        }
        return null;
    }

    static Empleado buscarEmpleado(String id) {
        for (Empleado item : empleados) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    static Bodega buscarBodega(String id) {
        for (Bodega item : bodegas) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    static Inventario buscarInventario(String idBodega, String codigo) {
        for (Inventario item : inventarios) {
            if (item.getIdBodega().equals(idBodega)
                    && item.getCodigoProducto().equals(codigo)) {
                return item;
            }
        }
        return null;
    }

    static OrdenCompra buscarOrden(String id) {
        for (OrdenCompra item : ordenes) {
            if (item.getId().equalsIgnoreCase(id)) {
                return item;
            }
        }
        return null;
    }

    static int totalEnBodega(String idBodega) {
        int total = 0;
        for (Inventario item : inventarios) {
            if (item.getIdBodega().equals(idBodega)) {
                total = total + item.getCantidad();
            }
        }
        return total;
    }

    static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        String texto = teclado.nextLine().trim();
        while (texto.isEmpty() || texto.contains("|")) {
            System.out.println("El texto no puede estar vacío ni incluir el símbolo |.");
            System.out.print(mensaje);
            texto = teclado.nextLine().trim();
        }
        return texto;
    }

    static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Escriba un número entero.");
            }
        }
    }

    static double leerDecimal(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Double.parseDouble(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Escriba un número válido.");
            }
        }
    }

    static void guardarDatos() {
        try (PrintWriter archivo = new PrintWriter(new FileWriter("datos.txt"))) {
            for (producto item : productos) {
                archivo.println("P|" + item.getCodigo() + "|" + item.getNombre() + "|"
                        + item.getDescripcion() + "|" + item.getMarca() + "|" + item.getPrecio());
            }
            for (cliente item : clientes) {
                archivo.println("C|" + item.getDpi() + "|" + item.getNombre() + "|"
                        + item.getDireccion() + "|" + item.getFechaNacimiento() + "|" + item.getTipo());
            }
            for (Empleado item : empleados) {
                archivo.println("E|" + item.getId() + "|" + item.getNombre() + "|" + item.getDpi()
                        + "|" + item.getDireccion() + "|" + item.getPuesto() + "|" + item.getSalario());
            }
            for (Bodega item : bodegas) {
                archivo.println("B|" + item.getId() + "|" + item.getIdEmpleado() + "|"
                        + item.getCapacidad() + "|" + item.getDireccion());
            }
            for (Inventario item : inventarios) {
                archivo.println("I|" + item.getIdBodega() + "|" + item.getCodigoProducto()
                        + "|" + item.getCantidad());
            }
            for (OrdenCompra item : ordenes) {
                archivo.println("O|" + item.getId() + "|" + item.getDpiCliente() + "|"
                        + item.getCodigoProducto() + "|" + item.getCantidad() + "|" + item.getFecha()
                        + "|" + item.getTotal() + "|" + item.getEstado() + "|" + item.getIdBodega());
            }
            System.out.println("Datos guardados en datos.txt.");
        } catch (IOException e) {
            System.out.println("No se pudieron guardar los datos: " + e.getMessage());
        }
    }

    static void cargarDatos() {
        File archivo = new File("datos.txt");
        if (!archivo.exists()) {
            return;
        }
        try (Scanner lector = new Scanner(archivo)) {
            while (lector.hasNextLine()) {
                String[] dato = lector.nextLine().split("\\|");
                if (dato[0].equals("P")) {
                    productos.add(new producto(dato[1], dato[2], dato[3], dato[4],
                            Double.parseDouble(dato[5])));
                } else if (dato[0].equals("C")) {
                    clientes.add(new cliente(dato[1], dato[2], dato[3], dato[4], dato[5]));
                } else if (dato[0].equals("E")) {
                    empleados.add(new Empleado(dato[1], dato[2], dato[3], dato[4],
                            dato[5], Double.parseDouble(dato[6])));
                } else if (dato[0].equals("B")) {
                    bodegas.add(new Bodega(dato[1], dato[2], Integer.parseInt(dato[3]), dato[4]));
                } else if (dato[0].equals("I")) {
                    inventarios.add(new Inventario(dato[1], dato[2], Integer.parseInt(dato[3])));
                } else if (dato[0].equals("O")) {
                    ordenes.add(new OrdenCompra(dato[1], dato[2], dato[3],
                            Integer.parseInt(dato[4]), dato[5], Double.parseDouble(dato[6]),
                            dato[7], dato[8]));
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudieron leer los datos: " + e.getMessage());
        }
    }
}
