package utilidades;
import java.util.List;
import java.util.Map;

import modelos.Alumno;

public class Utilidad {
    
	
	public static int obtenerIndiceObjeto(List<Alumno> a, String rut){
		int indice;
		for(int i = 0; i<a.size();i++) {
			if(a.get(i).getRut().equals(rut)) {
				indice = i;
				return indice;
			}
			
		}
		
		return -1;
		
	}
	
	
	public static void formatoListarAlumnos(Map<String,Alumno> listaAlumnos) {		
		
		
		listaAlumnos.forEach((k,v) ->{ 
						System.out.println("Datos Alumno\n	rut: " + v.getRut() + " nombre: " + v.getNombre() + " apellido: " 
		                                                  + v.getApellido() + " direccion: " + v.getDireccion()+"\n");
					    if(v.getMaterias()==null) {
					    	System.out.println("	No tiene materias");
					    }else {
					    	System.out.print("	Materias:\n\n");
					    	v.getMaterias().forEach(materia->{
					    				
					    				if(materia.getNotas()==null) {
					    					System.out.print("	" + materia.getNombre() + " - No tiene notas\n");	
					    				}else {
										   System.out.print("	" + materia.getNombre() + " notas:" + materia.getNotas()+"\n");
					    		        }
										   });
						}
						System.out.println();
						
			     		});
		
	}
	
}
