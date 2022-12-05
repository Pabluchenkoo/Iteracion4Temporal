package uniandes.isis2304.SuperAndes.negocio;

public class OfertaDescuento implements VOOfertaDescuento{

    private long iDOferta;

    private double descuento;

    public OfertaDescuento(long iDOferta,  double descuento) {
        this.iDOferta = iDOferta;
        this.descuento = descuento;
    }

    public long getIdOferta() {
        return iDOferta;
    }


    public double getDescuento() {
        return descuento;
    }

    public String toString() {
        return "OfertaDescuento [iDOferta=" + iDOferta + ", descuento=" + descuento + "]";
    }

    public void setiDOferta(long iDOferta) {
        this.iDOferta = iDOferta;
    }



    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }
}
