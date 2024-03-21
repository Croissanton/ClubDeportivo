package clubdeportivo;

public class Grupo {
	private String codigo;
	private String actividad;
	private int nplazas;
	private int nmatriculados;
	private double tarifa;
	
	public Grupo(String codigo, String actividad, int nplazas,  int matriculados, double tarifa) throws ClubException {
		//ERROR 3: SE PERMITE QUE EL NÚMERO DE MATRICULADOS SEA 0, PERO EN EL MENSAJE DE LA EXCEPCIÓN
		// SE DICE QUE LOS DATOS NUMÉRICOS NO PUEDEN SER MENORES O IGUALES QUE 0.
		// POR TANTO, CAMBIAMOS EL < POR UN <=.
		if (nplazas<=0 || matriculados<=0 || tarifa <=0) {
			throw new ClubException("ERROR: los datos numéricos no pueden ser menores o iguales que 0.");
		}
		if (matriculados>nplazas) {
			throw new ClubException("ERROR: El número de plazas es menor que el de matriculados.");
		}
		this.codigo=codigo;
		this.actividad=actividad;
		this.nplazas=nplazas;
		this.nmatriculados=matriculados;
		this.tarifa=tarifa;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getActividad() {
		return actividad;
	}

	public int getPlazas() {
		return nplazas;
	}

	public int getMatriculados() {
		return nmatriculados;
	}
	
	public double getTarifa() {
		return tarifa;
	}
	
	public int plazasLibres() {
		return nplazas-nmatriculados;
	}
	
	public void actualizarPlazas(int n) throws ClubException {
		//ERROR 1: SI EL NÚMERO DE PLAZAS ES MENOR QUE EL DE MATRICULADOS,
		// LA EXCEPCIÓN DEBE SER "ERROR: no hay plazas libres suficientes..."
		// POR TANTO, HACEMOS UN IF Y ELSE IF PARA COMPROBAR AMBOS CASOS.
		if (n<=0) {
			throw new ClubException("ERROR: número de plazas negativo.");
		}
		else if (n < nmatriculados)
			throw new ClubException("ERROR: no hay plazas libres suficientes, plazas libre: "+ plazasLibres()+ " y matriculas: "+n);
		nplazas=n;		
	}
	
	public void matricular(int n) throws ClubException {
		//ERROR 2: SI EL NÚMERO DE PLAZAS ES MENOR QUE 0, DEBE LANZAR UNA EXCEPCIÓN
		// CON EL MENSAJE "ERROR: número de plazas negativo."
		// POR TANTO, HACEMOS UN ELSE IF PARA COMPROBAR ESTE CASO.
		if (plazasLibres()< n) {
			throw new ClubException("ERROR: no hay plazas libres suficientes, plazas libre: "+ plazasLibres()+ " y matriculas: "+n);
		}
		else if (n<=0) {
			throw new ClubException("ERROR: número de plazas negativo.");
		}
		nmatriculados+=n;
	}
	
	public String toString() {
		return "("+ codigo + " - "+actividad+" - " + tarifa + " euros "+ "- P:" + nplazas +" - M:" +nmatriculados+")";
	}
	
	public boolean equals(Object o) {
		boolean ok=false;
		if (o instanceof Grupo) {
			Grupo otro = (Grupo) o;
			ok = this.codigo.equalsIgnoreCase(otro.codigo) && this.actividad.equalsIgnoreCase(otro.actividad);
		}
		return ok;
	}
	
	public int hashCode() {
		return codigo.toUpperCase().hashCode()+actividad.toUpperCase().hashCode();
	}
}
