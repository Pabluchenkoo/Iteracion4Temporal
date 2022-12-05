package uniandes.isis2304.SuperAndes.negocio;

public class CarritosAbandonados implements VOCarritosAbandonados{

    private long numDocCliente;
    private long IDProductoAbandonado;

    public CarritosAbandonados() {
        this.numDocCliente = 0;
        this.IDProductoAbandonado = 0;
    }

    public CarritosAbandonados(long numDocCliente, long IDProductoAbandonado) {
        this.numDocCliente = numDocCliente;
        this.IDProductoAbandonado = IDProductoAbandonado;
    }

    public long getNumDocCliente() {
        return numDocCliente;
    }

    public void setNumDocCliente(long numDocCliente) {
        this.numDocCliente = numDocCliente;
    }

    public long getIDProductoAbandonado() {
        return IDProductoAbandonado;
    }

    public void setIDProductoAbandonado(long IDProductoAbandonado) {
        this.IDProductoAbandonado = IDProductoAbandonado;
    }

    @Override
    public String toString() {
        return "CarritosAbandonados [numDocCliente=" + numDocCliente + ", IDProductoAbandonado=" + IDProductoAbandonado + "]";
    }

}
