package servicios;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import modelos.Alumno;
import modelos.Materia;
import utilidades.Utilidad;

public class AlumnoServicio {
	  private Map<String, Alumno> listaAlumnos;
	  
	  
	  
	  public void crearAlumno(Alumno al){
		  if(listaAlumnos==null) {listaAlumnos = new LinkedHashMap<String, Alumno>();}
		  this.listaAlumnos.put(al.getRut(), al);
		  System.out.println("alumno exitosamente creado");
	  }
	  
	  
	  public void agregarMateria(String rutAlumno, Materia currentMate){
	       
	
		  //condicion que ejecuta su bloque solo si se ejecutó la opción "Crear Alumnos" o "listar alumnos" luego de "cargar datos"  del menú principal.
		  if(listaAlumnos!=null && listaAlumnos.containsKey(rutAlumno)) {
			  
			  if(listaAlumnos.get(rutAlumno).getMaterias()==null) {
		
				  listaAlumnos.get(rutAlumno).setMaterias(new ArrayList<Materia>(Arrays.asList(currentMate)));
			  	  System.out.println("Materia Agregada \nVolviendo al Menu principal");
			  	  
			  }else {
				 
				  for(int i = 0; i < listaAlumnos.get(rutAlumno).getMaterias().size(); i++) {
					
					  if(listaAlumnos.get(rutAlumno).getMaterias().get(i).getNombre() == currentMate.getNombre()) {
                          System.out.println("el alumno ya cursa esa materia. Intente añadiendo otra distinta.\n Volviendo al menú principal");		
                          break;
                          
					  }else if(i == listaAlumnos.get(rutAlumno).getMaterias().size()-1 && listaAlumnos.get(rutAlumno).getMaterias().get(i).getNombre() != currentMate.getNombre()){
						  listaAlumnos.get(rutAlumno).getMaterias().add(currentMate);
						  System.out.println("Materia Agregada \nVolviendo al Menu principal");
						  break;				  
					  }
				  }
			  }
		  
		  //condición que ejecuta su bloque sólo si se ejecutó  la opción "Cargar Datos" del menú principal.
		  }else if(!ArchivosServicio.alumnosACargar.isEmpty()){
			  
			  //para recorrer los alumnos que han sido cargados pero no listados; 
			  for(int i = 0; i < ArchivosServicio.alumnosACargar.size(); i++ ) {
				  
				  //Y encontrar al alumno que corresponde según el rut ingresado.
			  	  if(ArchivosServicio.alumnosACargar.get(i).getRut().equals(rutAlumno)) {
			  		  
			  		  //recorre la lista de materias que  tiene el alumno 
			  		  for(int z = 0; z<ArchivosServicio.alumnosACargar.get(i).getMaterias().size(); z++) {

			  			  //condición que busca una coincidencia entre el nombre de alguna de las materias de la lista de materias del alumno y el nombre de la que quiere añadir el usuario.
			  			 if(ArchivosServicio.alumnosACargar.get(i).getMaterias().get(z).getNombre()==currentMate.getNombre()) {		  			 
			  				 System.out.println("el alumno ya cursa esa materia. Intente añadiendo otra distinta.\n Volviendo al menú principal");		
			  				 break;			  		  
	
			  			 //condición que verifica que en la ultima iteración del for anidado no haya coincidencia entre el nombre de la última materia del listado de materias del alumno y el nombre de la que quiere añadir el usuario.	 
			  			 }else if(z == ArchivosServicio.alumnosACargar.get(i).getMaterias().size()-1 && ArchivosServicio.alumnosACargar.get(i).getMaterias().get(z).getNombre()!=currentMate.getNombre()) {
			  				 ArchivosServicio.alumnosACargar.get(i).getMaterias().add(currentMate);
			  				 System.out.println("Materia Agregada \nVolviendo al Menu principal");
			  				 break;
			  		     }
			  		  } 
				   }
			  	}			  
		     }
	   }
	   
	
	  
	  
	  
	  public List<Materia> materiasPorAlumnos(String rutAlumno){
		  List<Materia> materiasDelAlumno = new ArrayList<Materia>();
		  
		        //valida si el alumno se encuentra en 'listaAlumnos'('a' se vuelve true si está aquí) o 'alumnosACargar'('b' se vuelve true si está aquí).            
		  		Boolean a = listaAlumnos!=null && this.getListaAlumnos().containsKey(rutAlumno);
			    Boolean b = false;
			    if(!ArchivosServicio.alumnosACargar.isEmpty()){
					  Iterator<Alumno> lit = ArchivosServicio.alumnosACargar.iterator();
					  while(lit.hasNext()) {
					  	  if(lit.next().getRut().equals(rutAlumno)) {
					  		b = true;  
					  	  } 	  
					  }		  
			    }
			    
			    if(!a && !b) {
			    	System.out.println("no hay ningún alumno con ese rut \nVolviendo al Menu principal");
			    	
			    }else {
		  
			    	if(listaAlumnos!=null && listaAlumnos.containsKey(rutAlumno)) {
			    		if(listaAlumnos.get(rutAlumno).getMaterias()!=null) {
			    			materiasDelAlumno.addAll(listaAlumnos.get(rutAlumno).getMaterias());
			    		}else {
			    			System.out.println("El alumno no tiene ninguna materia agregada");
			    		}
			    	}else{
			    		Iterator<Alumno> lit = ArchivosServicio.alumnosACargar.iterator();
			    		while(lit.hasNext()) {
			    			if(lit.next().getRut().equals(rutAlumno)) {
			    				materiasDelAlumno.addAll(lit.next().getMaterias());
			  	  
			    			}
			    		}
			    	}
			    }
		  return materiasDelAlumno; 
		  }


	  public  Map<String, Alumno> listarAlumnos(){
		//condición que verifica que se haya ejecutado la opción "5 - Cargar Datos " del menú principal. También verifica que el bloque
		//de la condición se ejecute sólo una vez durante todo el programa, para ello se evalúa si el map "listaAlumnos" ya contiene el primer rut del archivo "notas.csv" 
		  if(listaAlumnos==null) {listaAlumnos = new LinkedHashMap<String, Alumno>();}
		  if(!ArchivosServicio.alumnosACargar.isEmpty() && !listaAlumnos.containsKey(ArchivosServicio.alumnosACargar.get(0).getRut())) {
			  Iterator<Alumno> lit = ArchivosServicio.alumnosACargar.iterator();
			  Alumno alumno;
			  while(lit.hasNext()) {
			  	  alumno = lit.next();
				  listaAlumnos.put(alumno.getRut(),alumno);
              }		       			
		  }
		  
		  return listaAlumnos;
	 }


	

	  public Map<String, Alumno> getListaAlumnos() {
		return listaAlumnos;
	}

	  public void agregarNota(List<Materia> materias, String rut, Scanner sc) {
		
		
			
			 int indice = 1;
			 for(Materia materia : materias) { 
				 System.out.println(indice +" - "+materia.getNombre());
				 indice+=1;
			 } 
				System.out.printf("Seleccionar materia:");
				String opc = "";
				
				String q = "repetir";
			    	while(q == "repetir") {
			    		try {
			    		    opc = sc.nextLine();
			    			Integer.parseInt(opc);
			    			if(1<=Integer.parseInt(opc) && Integer.parseInt(opc)<indice) {
			    				q="fin";		    				
			    			}else {
			    			System.out.println("Opción inválida. Intente denuevo");
			    			}
			    		}
			    		catch(Exception e){
			    			System.out.println("Opción inválida. Intente denuevo");
			    		}
			    	}
			
				 int i = Integer.parseInt(opc)-1;
				 System.out.printf("Ingresa nota:");
				
				    q = "repetir";
			    	while(q == "repetir") {
			    		try {
			    			
			    		    opc = sc.nextLine();
			    		    Double.parseDouble(opc);
			    			q="fin";		    				
			    			
			    		}
			    		catch(Exception e){
			    			System.out.println("Intente nuevamente. La nota ingresada debe ser un número.\nPara añadir decimales usar un punto '.' Ej: 0.0, 6.5, 5.3");
			    		}
			    	}
				 
				
				 Double nota = Double.parseDouble(opc);
				
				 //si es que está en el mapa de alumnos listados
				 if(listaAlumnos!=null && listaAlumnos.containsKey(rut)) {
					
					 if(listaAlumnos.get(rut).getMaterias().get(i).getNotas()==null) {
					 listaAlumnos.get(rut).getMaterias().get(i).setNotas(new ArrayList<Double>(Arrays.asList(nota)));
					
					 }else{
						 listaAlumnos.get(rut).getMaterias().get(i).getNotas().add(nota);
					 }
				
				 //si es que está en la lista de alumnos cargados aún no listados	 
				 }else{ 
					 int indiceObj = Utilidad.obtenerIndiceObjeto(ArchivosServicio.alumnosACargar, rut);
					 
					 if(ArchivosServicio.alumnosACargar.get(indiceObj).getMaterias().get(i).getNotas()==null) {
						 ArchivosServicio.alumnosACargar.get(indiceObj).getMaterias().get(i).setNotas(new ArrayList<Double>(Arrays.asList(nota)));
				   
					 }else {
				    	ArchivosServicio.alumnosACargar.get(indiceObj).getMaterias().get(i).getNotas().add(nota);	
				    }
				     
			 }
		 
		  
	      
	  

	  }


	
	  
	  public void setListaAlumnos(Map<String, Alumno> listaAlumnos) {
		this.listaAlumnos = listaAlumnos;
	}



	
	  
	  
	  
}







