package modelos;
import java.util.List;

public class Materia {
	MateriaEnum nombre;
	List<Double> notas;
	public MateriaEnum getNombre() {
		return nombre;
	}
	public void setNombre(MateriaEnum nombre) {
		this.nombre = nombre;
	}
	public List<Double> getNotas() {
		return notas;
	}
	public void setNotas(List<Double> notas) {
		this.notas = notas;
	}
	@Override
	public String toString() {
		return "[Materia: " + nombre + ", notas=" + notas + "]";
	}
	
	

	
	public Materia(MateriaEnum nombre, List<Double> notas) {
		
		this.nombre = nombre;
		this.notas = notas;
	}
	public Materia() {
		
	}
	
	
	
}
	
