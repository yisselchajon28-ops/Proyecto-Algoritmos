public class Inventario {
    private String idBodega;
    private String codigoProducto;
    private int cantidad;

    public Inventario(String idBodega, String codigoProducto, int cantidad) {
        this.idBodega = idBodega;
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
    }

    public String getIdBodega() {
        return idBodega;
    }

    public void setIdBodega(String idBodega) {
        this.idBodega = idBodega;
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
}
