package servicios;

import java.io.*;
import java.util.*;
import servicios.PromedioServicioImp;
import modelos.*;


public class ArchivosServicio {
	public static final List<Alumno> alumnosACargar = new ArrayList<Alumno>();
	PromedioServicioImp promediosServicioImp;
	
	 public List<Alumno> cargarDatos(String ruta) throws Exception {
		//variables para que el método funcione correctamente
		
		Materia asgtr;
		List<Double> notas;
		List<Materia> asignaturas;
		List<Alumno> alumnosACargar=new ArrayList<Alumno>();
		
		
		BufferedReader br = new BufferedReader(new FileReader(ruta));
		
		
		String linea = br.readLine();
		String[] a; // elementos que hay por cada línea del .csv 
		
		
		while(linea!=null) {
			
			
			//se resetean las variables por cada iteración
			asgtr = new Materia();
			notas = new ArrayList<Double>();
			asignaturas = new ArrayList<Materia>();
		    alumnosACargar = new ArrayList<Alumno>();
			a = linea.split(",");
			
			for (MateriaEnum materia: MateriaEnum.values() ) {
				if(a[4].equals(materia.name())) {
				
					asgtr.setNombre(materia);
					asgtr.setNotas(notas);
					asgtr.getNotas().add(Double.parseDouble(a[5]));
					asignaturas.add(asgtr);
					alumnosACargar.add(new Alumno(a[0],a[1],a[2],a[3],asignaturas));
					break;
				}
				
			}
			
	
			if(ArchivosServicio.alumnosACargar.isEmpty()){
				ArchivosServicio.alumnosACargar.addAll(alumnosACargar);
		    
			//condición que evalúa si el rut del último alumno almacenado es igual al rut de la línea recién leída. 	
			}else if(ArchivosServicio.alumnosACargar.get(ArchivosServicio.alumnosACargar.size()-1).getRut().equals(a[0])){
				
				//ciclo que recorre la lista de materias del último alumno almacenado. La condición if valida si hay alguna coincidencia entre el nombre de materia de la línea recién leída y los nombres de las materias del último alumno almacenado.
				for (int i = 0; i<ArchivosServicio.alumnosACargar.get(ArchivosServicio.alumnosACargar.size()-1).getMaterias().size(); i++) {
					if(alumnosACargar.get(alumnosACargar.size()-1).getMaterias().get(0).getNombre()==ArchivosServicio.alumnosACargar.get(ArchivosServicio.alumnosACargar.size()-1).getMaterias().get(i).getNombre()) {
					
			
						ArchivosServicio.alumnosACargar.get(ArchivosServicio.alumnosACargar.size()-1).getMaterias().get(i).getNotas().add(Double.parseDouble(a[5]));
						break;
					
					//condición que se valida sólo si el ciclo for está en su última iteración y no hay coincidencias entre el nombre de materia de la línea recién leída y los nombres de las materias del último alumno almacenado.	
					}else if(i==ArchivosServicio.alumnosACargar.get(ArchivosServicio.alumnosACargar.size()-1).getMaterias().size()-1 && alumnosACargar.get(alumnosACargar.size()-1).getMaterias().get(0).getNombre()!=ArchivosServicio.alumnosACargar.get(ArchivosServicio.alumnosACargar.size()-1).getMaterias().get(i).getNombre() ){
						ArchivosServicio.alumnosACargar.get(ArchivosServicio.alumnosACargar.size()-1).getMaterias().add(asgtr);
						break;
					}
				}
		    
			}else {
				ArchivosServicio.alumnosACargar.add(new Alumno(a[0],a[1],a[2],a[3],asignaturas));
		     }
						
			linea = br.readLine();
			
			
		}   br.close();
		 	return alumnosACargar;
	}


    
    	
	
	
	
    public void exportarDatos(String ruta, Map<String,Alumno> coleccionAlumnos)throws Exception {
    		 //ruta debiera ser "archivo.Archivo" generaría un archivo en la carpeta SistemaCalificaciones
    		
    			 FileWriter fw = new FileWriter(ruta); //creamos el archivo
    			 PrintWriter pw = new PrintWriter(fw);
    		 
    		 
    		 
    		 
    		 coleccionAlumnos.forEach((k,v)->{ 
    			 pw.println("alumno : " + v.getRut() + " - "  + v.getNombre());
    			 if(v.getMaterias()==null) {
						  pw.printf("	No tiene materias%n");
				 }else{
    			 v.getMaterias().forEach(materia->{
    				 if(materia.getNotas()==null) {
							pw.printf("	MATERIA : %1$s - No tiene notas %n", materia.getNombre().name());
					 }else {
    				 		pw.printf("	MATERIA : %1$s - Promedio : %2$.1f%n",  materia.getNombre(), PromedioServicioImp.calcularPromedio(materia.getNotas()));
					 }
    			 });
				 }
    		 });
    		 
    		 	pw.close();
    		  	fw.close();	 
    	    	
    	}
  
    		 
    		 
    		
    		
    	




	public PromedioServicioImp getPromediosServicioImp() {
		return promediosServicioImp;
	}

	public void setPromediosServicioImp(PromedioServicioImp promediosServicioImp) {
		this.promediosServicioImp = promediosServicioImp;
	}


}
