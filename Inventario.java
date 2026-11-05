// Relaciona una bodega y un producto con la cantidad que hay disponible.
public class Inventario {
    private String idBodega;
    private String codigoProducto;
    private int cantidad;

    // Crea una fila de inventario para un producto dentro de una bodega.
    public Inventario(String idBodega, String codigoProducto, int cantidad) {
        this.idBodega = idBodega;
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
    }

    // Devuelve el ID de la bodega donde está guardado el producto.
    public String getIdBodega() {
        return idBodega;
    }

    // Cambia la bodega de esta fila de inventario.
    public void setIdBodega(String idBodega) {
        this.idBodega = idBodega;
    }

    // Devuelve el código del producto almacenado.
    public String getCodigoProducto() {
        return codigoProducto;
    }

    // Cambia el código del producto.
    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    // Devuelve cuántas unidades están disponibles.
    public int getCantidad() {
        return cantidad;
    }

    // Cambia la cantidad; se usa al recibir, vender o devolver productos.
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
