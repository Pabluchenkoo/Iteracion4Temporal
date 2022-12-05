package uniandes.isis2304.SuperAndes.negocio;

public class OfertaPagueXLleveY implements VOOfertaPagueXLleveY {
    
    private long iDOferta;
    

    private int x;
    
    private int y;

    public OfertaPagueXLleveY(long iDOferta,  int x, int y) {
        this.iDOferta = iDOferta;
        this.x = x;
        this.y = y;
    }

    public long getIdOferta() {
        // TODO Auto-generated method stub
        return iDOferta;
    }


    public int getX() {
        // TODO Auto-generated method stub
        return x;
    }

    public int getY() {
        // TODO Auto-generated method stub
        return y;
    }

    public void setiDOferta(long iDOferta) {
        this.iDOferta = iDOferta;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "OfertaPagueXLleveY [iDOferta=" + iDOferta + ", x=" + x + ", y=" + y + "]";
    }
    
    

}
