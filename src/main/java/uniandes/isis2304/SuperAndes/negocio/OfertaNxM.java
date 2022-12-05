package uniandes.isis2304.SuperAndes.negocio;

public class OfertaNxM implements VOOfertaNxM{

    private long iDOferta;

    private int N;

    private int M;


    public OfertaNxM(long iDOferta, int n, int m) {
        this.iDOferta = iDOferta;
        N = n;
        M = m;
    }

    public long getIdOferta() {
        return iDOferta;
    }

    public int getN() {
        return N;
    }

    public int getM() {
        return M;
    }

    public void setiDOferta(long iDOferta) {
        this.iDOferta = iDOferta;
    }

    public void setN(int n) {
        N = n;
    }

    public void setM(int m) {
        M = m;
    }

    @Override
    public String toString() {
        return "OfertaNxM [iDOferta=" + iDOferta + ", N=" + N + ", M=" + M + "]";
    }



}
