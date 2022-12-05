package uniandes.isis2304.SuperAndes.negocio;

import java.sql.Timestamp;

public interface VOLote {

	public long getIdAlmacenamiento();

	public long getIdLote();
	public int getUnidades();
	public Timestamp getFechaVencimiento();
	public double getPeso();
	public double getArea();
	public String toString();
}
