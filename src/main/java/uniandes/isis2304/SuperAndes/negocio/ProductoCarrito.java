package uniandes.isis2304.SuperAndes.negocio;

public class ProductoCarrito implements VOProductoCarrito {
    private String codigoBarras;
    private long idCarrito;
    private int cantidad;

    public ProductoCarrito(String codigoBarras, long idCarrito, int cantidad) {
        this.codigoBarras = codigoBarras;
        this.idCarrito = idCarrito;
        this.cantidad = cantidad;
    }

    public ProductoCarrito() {
        this.codigoBarras = "";
        this.idCarrito = 0;
        this.cantidad = 0;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public long getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(long idCarrito) {
        this.idCarrito = idCarrito;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "ProductoCarrito [codigoBarras=" + codigoBarras + ", idCarrito=" + idCarrito + ", cantidad=" + cantidad + "]";
    }
}
