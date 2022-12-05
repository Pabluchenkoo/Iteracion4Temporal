package uniandes.isis2304.SuperAndes.negocio;

public class OrdenConsolidada implements VOOrdenConsolidada{
    private long idOrdenConsolidada;
    private long NITProveedor;
    private long idSucursal;

    public OrdenConsolidada(long idOrdenConsolidada, long NITProveedor, long idSucursal) {
        this.idOrdenConsolidada = idOrdenConsolidada;
        this.NITProveedor = NITProveedor;
        this.idSucursal = idSucursal;
    }

    public OrdenConsolidada() {
        this.idOrdenConsolidada = 0;
        this.NITProveedor = 0;
        this.idSucursal = 0;
    }

    public long getIdOrdenConsolidada() {
        return idOrdenConsolidada;
    }

    public void setIdOrdenConsolidada(long idPedidoConsolidado) {
        this.idOrdenConsolidada = idPedidoConsolidado;
    }

    public long getNITProveedor() {
        return NITProveedor;
    }

    public void setNITProveedor(long NITProveedor) {
        this.NITProveedor = NITProveedor;
    }

    public long getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(long idSucursal) {
        this.idSucursal = idSucursal;
    }

    @Override
    public String toString() {
        return "OrdenConsolidada [idOrdenConsolidada=" + idOrdenConsolidada + ", NITProveedor=" + NITProveedor + ", idSucursal=" + idSucursal + "]";
    }
}
