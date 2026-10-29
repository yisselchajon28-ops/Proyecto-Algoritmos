public class OrdenCompra {
    private String id;
    private String dpiCliente;
    private String codigoProducto;
    private int cantidad;
    private String fecha;
    private double total;
    private String estado;
    private String idBodega;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDpiCliente() {
        return dpiCliente;
    }

    public void setDpiCliente(String dpiCliente) {
        this.dpiCliente = dpiCliente;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdBodega() {
        return idBodega;
    }

    public void setIdBodega(String idBodega) {
        this.idBodega = idBodega;
    }
}
