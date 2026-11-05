// Registra una venta de un producto a un cliente.
public class OrdenCompra {
    // La orden conserva las referencias como identificadores para guardarlas fácilmente en texto.
    private String id;
    private String dpiCliente;
    private String codigoProducto;
    private int cantidad;
    private String fecha;
    private double total;
    private String estado;
    private String idBodega;

    // Crea una orden con el cliente, producto, cantidad, precio final, fecha y estado.
    public OrdenCompra(String id, String dpiCliente, String codigoProducto, int cantidad,
                       String fecha, double total, String estado, String idBodega) {
        this.id = id;
        this.dpiCliente = dpiCliente;
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
        this.idBodega = idBodega;
    }

    // Devuelve el identificador de la orden.
    public String getId() {
        return id;
    }

    // Cambia el identificador de la orden.
    public void setId(String id) {
        this.id = id;
    }

    // Devuelve el DPI del cliente que hizo la compra.
    public String getDpiCliente() {
        return dpiCliente;
    }

    // Cambia el DPI del cliente.
    public void setDpiCliente(String dpiCliente) {
        this.dpiCliente = dpiCliente;
    }

    // Devuelve el código del producto vendido.
    public String getCodigoProducto() {
        return codigoProducto;
    }

    // Cambia el código del producto.
    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    // Devuelve cuántas unidades se vendieron.
    public int getCantidad() {
        return cantidad;
    }

    // Cambia la cantidad vendida.
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    // Devuelve la fecha de la orden.
    public String getFecha() {
        return fecha;
    }

    // Cambia la fecha.
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    // Devuelve el total pagado por la orden.
    public double getTotal() {
        return total;
    }

    // Cambia el total.
    public void setTotal(double total) {
        this.total = total;
    }

    // Devuelve el estado: normalmente "Completada" o "Cancelada".
    public String getEstado() {
        return estado;
    }

    // Cambia el estado, por ejemplo cuando se cancela una venta.
    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Devuelve la bodega que surtió la orden.
    public String getIdBodega() {
        return idBodega;
    }

    // Cambia la bodega relacionada con la orden.
    public void setIdBodega(String idBodega) {
        this.idBodega = idBodega;
    }
}
