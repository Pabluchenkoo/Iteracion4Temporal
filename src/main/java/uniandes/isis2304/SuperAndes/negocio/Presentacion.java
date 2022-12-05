package uniandes.isis2304.SuperAndes.negocio;

import java.math.BigDecimal;

public class Presentacion implements VOPresentacion{
	private long idPresentacion;
	private BigDecimal precioPorUnidad;
	private String unidadDeMedida;
	private String especificacion;
	
	public Presentacion(long idPresentacion, BigDecimal precioPorUnidad, String unidadDeMedida, String especificacion) {
		super();
		this.idPresentacion = idPresentacion;
		this.precioPorUnidad = precioPorUnidad;
		this.unidadDeMedida = unidadDeMedida;
		this.especificacion = especificacion;
	}
	
	public Presentacion() {
		this.idPresentacion = 0;
		this.precioPorUnidad = BigDecimal.valueOf(0);
		this.unidadDeMedida = "";
		this.especificacion = "";
	}

	public long getIdPresentacion() {
		return idPresentacion;
	}

	public void setIdPresentacion(long idPresentacion) {
		this.idPresentacion = idPresentacion;
	}

	public BigDecimal getPrecioPorUnidad() {
		return precioPorUnidad;
	}

	public void setPrecioPorUnidad(BigDecimal precioPorUnidad) {
		this.precioPorUnidad = precioPorUnidad;
	}

	public String getUnidadDeMedida() {
		return unidadDeMedida;
	}

	public void setUnidadDeMedida(String unidadDeMedida) {
		this.unidadDeMedida = unidadDeMedida;
	}

	public String getEspecificacion() {
		return especificacion;
	}

	public void setEspecificacion(String especificacion) {
		this.especificacion = especificacion;
	}

	@Override
	public String toString() {
		return "Presentacion [idPresentacion=" + idPresentacion + ", precioPorUnidad=" + precioPorUnidad
				+ ", unidadDeMedida=" + unidadDeMedida + ", especificacion=" + especificacion + "]";
	}
	
}
