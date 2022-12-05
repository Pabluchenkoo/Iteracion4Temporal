package uniandes.isis2304.SuperAndes.negocio;

import java.sql.Timestamp;

public class Lote implements VOLote{
	private long idAlmacenamiento;

	private long idLote;
	private int unidades;
	private Timestamp fechaVencimiento;
	private double peso;
	private double area;

	public Lote() {
		this.idAlmacenamiento = 0;
		this.idLote = 0;
		this.unidades = 0;
		this.fechaVencimiento = new Timestamp(0);
		this.peso = 0;
		this.area = 0;
	}

	public Lote(long idAlmacenamiento, long idLote, int unidades, Timestamp fechaVencimiento, double peso, double area) {
		this.idAlmacenamiento = idAlmacenamiento;
		this.idLote = idLote;
		this.unidades = unidades;
		this.fechaVencimiento = fechaVencimiento;
		this.peso = peso;
		this.area = area;
	}

	@Override
	public long getIdAlmacenamiento() {
		return idAlmacenamiento;
	}

	public void setIdAlmacenamiento(long idAlmacenamiento) {
		this.idAlmacenamiento = idAlmacenamiento;
	}

	@Override
	public long getIdLote() {
		return idLote;
	}

	public void setIdLote(long idLote) {
		this.idLote = idLote;
	}

	@Override
	public int getUnidades() {
		return unidades;
	}

	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}

	@Override
	public Timestamp getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Timestamp fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	@Override
	public String toString() {
		return "Lote [idAlmacenamiento=" + idAlmacenamiento + ", idLote=" + idLote + ", unidades=" + unidades
				+ ", fechaVencimiento=" + fechaVencimiento + ", peso=" + peso + ", area=" + area + "]";
	}
}
