package uniandes.isis2304.SuperAndes.negocio;

public class ProductoAbandonado implements VOProductoAbandonado{

    private long idProductoAbandonado;
    private int cantidad;

    public ProductoAbandonado(long idProductoAbandonado, int cantidad) {
        this.idProductoAbandonado = idProductoAbandonado;
        this.cantidad = cantidad;
    }

    public ProductoAbandonado() {
        this.idProductoAbandonado = 0;
        this.cantidad = 0;
    }

    public long getIdProductoAbandonado() {
        return idProductoAbandonado;
    }

    public void setIdProductoAbandonado(long idProductoAbandonado) {
        this.idProductoAbandonado = idProductoAbandonado;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "ProductoAbandonado [idProductoAbandonado=" + idProductoAbandonado + ", cantidad=" + cantidad + "]";
    }

}
