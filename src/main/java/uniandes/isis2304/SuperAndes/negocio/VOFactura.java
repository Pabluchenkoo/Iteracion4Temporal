package uniandes.isis2304.SuperAndes.negocio;

import java.sql.Timestamp;

public interface VOFactura {

	public long getNumDoc();
	public long getIdFactura();
	public Timestamp getFecha();
	public double getPrecio();
	public String toString();
}
