package uniandes.isis2304.SuperAndes.negocio;

import java.sql.Timestamp;

public class Factura implements VOFactura{

	private long numDoc;
	private long idFactura;
	private Timestamp fecha;
	private double precio;
	
	public Factura( long numDoc, long idFactura, Timestamp fecha, double precio) {

		this.numDoc = numDoc;
		this.idFactura = idFactura;
		this.fecha = fecha;
		this.precio = precio;
	}
	
	public Factura() {

		this.numDoc = 0;
		this.idFactura = 0;
		this.fecha = new Timestamp (0);
		this.precio = 0;
	}



	public long getNumDoc() {
		return numDoc;
	}

	public void setNumDoc(long numDoc) {
		this.numDoc = numDoc;
	}

	public long getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(long idFactura) {
		this.idFactura = idFactura;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "Factura [numDoc=" + numDoc + ", idFactura=" + idFactura + ", fecha=" + fecha
				+ ", precio=" + precio + "]";
	}
	
	
}
