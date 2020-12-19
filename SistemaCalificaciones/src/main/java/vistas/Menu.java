package vistas;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import modelos.Alumno;
import modelos.Materia;
import modelos.MateriaEnum;
import servicios.AlumnoServicio;
import servicios.ArchivosServicio;
import utilidades.Utilidad;

public class Menu extends MenuTemplate {
	private AlumnoServicio alumnoServicio;
	private ArchivosServicio archivoServicio;
	private Scanner menu = new Scanner(System.in);
	
	
	@Override	
	public void cargarDatos()  {
		String o = "ruta incorrecta, intente ingresando \"notas.csv\"";
		if(archivoServicio==null) {
			archivoServicio = new ArchivosServicio();
			
		}
		while(o == "ruta incorrecta, intente ingresando \"notas.csv\"") {
		try {
			System.out.println("ingrese la ruta del archivo \"notas.csv\""); 
		    archivoServicio.cargarDatos(menu.nextLine());
			System.out.println("Datos cargados correctamente\n" + "------------------------------");
			o = "s";
			
		 } catch (Exception e) {
			System.out.println("ruta incorrecta, intente ingresando \"notas.csv\"");
			
		} 
		
	}	
		super.iniciarMenu();
	}

	@Override
	public void exportarDatos()  {
		
	if(alumnoServicio==null && archivoServicio==null) {
		System.out.println("No hay alumnos cuyos promedios exportar.Cree un alumno o cargue los datos primero\nVolviendo al Menú Principal"); 
		super.iniciarMenu();
		
	}else {
		
		
		String x = "repetir";
		
		System.out.println("--------------------------------------------- ExportarDatos\n" + 
						   "Ingresa la ruta en donde quieres crear el archivo con los promedios\n" +
				           "La ruta por defecto es la carpeta Raíz del proyecto\n" +
						   "(debe incluir nombre, puede agregarle extensión(ej: notas.Archivo, asdas.-xnotasx-)):");
		
		
		
		
		if(archivoServicio==null) {
			archivoServicio = new ArchivosServicio();
			while(x == "repetir") {
				try {
					 
					archivoServicio.exportarDatos(menu.nextLine(), alumnoServicio.getListaAlumnos());
					x = "fin";
				} catch (Exception e) {
					System.out.println("Error al crear el archivo. Intente denuevo en otra ruta distinta");
					
					
				}
			}
			super.iniciarMenu();
		
		}else{
			if(alumnoServicio==null) alumnoServicio = new AlumnoServicio();
			alumnoServicio.listarAlumnos();
			
			while(x == "repetir") {
				
					try {
						
						archivoServicio.exportarDatos(menu.nextLine(), alumnoServicio.getListaAlumnos());
						x = "fin";
					} catch (Exception e) {
						System.out.println("Error al crear el archivo. Intente denuevo en otra ruta distinta");
						//e.printStackTrace();
					}
					
			
			}
		super.iniciarMenu();
		}
	}
	}

	@Override
	public void crearAlumno() {
		
		
		Alumno alumno = new Alumno(); 
		System.out.println("----------------- Crear Alumno");
		System.out.printf("Ingresa RUT: ");
		alumno.setRut(menu.nextLine());
		System.out.printf("Ingresa Nombre: ");
		alumno.setNombre(menu.nextLine());
		System.out.printf("Ingresa Apellido: ");
		alumno.setApellido(menu.nextLine());
		System.out.printf("Ingresa Direccion: ");
		alumno.setDireccion(menu.nextLine());
		
		
		if(alumnoServicio==null) {alumnoServicio = new AlumnoServicio();}
		
		alumnoServicio.crearAlumno(alumno); 
		
		System.out.println("------------------------------");
		super.iniciarMenu();
	}

	@Override
	public void agregarMateria()  {
		
		if(this.alumnoServicio==null && this.archivoServicio==null) { 
			System.out.println("no hay alumnos a quienes agregar materias. \nVolviendo al menu principal"); 
			super.iniciarMenu();
	   
		}else{
	    
			if(alumnoServicio==null)alumnoServicio = new AlumnoServicio();
	    	System.out.println("----------------- Agregar Materia");
	    	System.out.printf("Ingresa el RUT del Alumno: ");
	   		String rutAlumno =  menu.nextLine();
	   		
	        Boolean a = alumnoServicio.getListaAlumnos()!=null && alumnoServicio.getListaAlumnos().containsKey(rutAlumno);
		    Boolean b = false;
		    if(!ArchivosServicio.alumnosACargar.isEmpty()){
				  Iterator<Alumno> lit = ArchivosServicio.alumnosACargar.iterator();
				  while(lit.hasNext()) {
				  	  if(lit.next().getRut().equals(rutAlumno)) {
				  		b = true;  
				  		break;
				  	  } 	  
				  }
				  
		    }
		    
		    if(!a && !b) {
		    	System.out.println("no hay ningún alumno con ese rut \nVolviendo al Menu principal");
		    	super.iniciarMenu();
		    }else {
			
		    	System.out.println("1 - MATEMATICA \n2 - LENGUAJE \n3 - CIENCIA \n4 - HISTORIA");
		    	System.out.printf("Selecciona una Materia: ");
		    	Materia currentMate = new Materia();
		    	String aux="";
			    String q = "repetir";
		    	while(q == "repetir") {
		    		try {
		    		    aux = menu.nextLine();
		    			Integer.parseInt(aux);
		    			if(1<=Integer.parseInt(aux) && Integer.parseInt(aux)<=4) {
		    				q="fin";		    				
		    			}else {
		    			System.out.println("la opción ingresada debe ser un número entre 1 y 4");
		    			}
		    		}
		    		catch(Exception e){
		    			System.out.println("la opción ingresada debe ser un número entre 1 y 4");
		    		}
		    	}
		    
				
		    	int indice =  Integer.parseInt(aux)-1;
		    	for(MateriaEnum materiaNombre : MateriaEnum.values()) {
					
		    		if(materiaNombre.ordinal()==indice) {
		    			currentMate.setNombre(materiaNombre);
		    			break;
		    		} 
		    	}
		    
			
			alumnoServicio.agregarMateria(rutAlumno, currentMate);
			super.iniciarMenu();
		    }	
	   }
	}

	@Override
	public void agregarNotaPasoUno() {
		
		List<Materia> materiasDelAlumno;
		if(alumnoServicio==null && archivoServicio==null) {
			System.out.println("No hay alumnos a quienes agregar notas.\nVolviendo al Menú Principal"); 
			super.iniciarMenu();
		
		
		}else {
			if(alumnoServicio==null)alumnoServicio= new AlumnoServicio();
			String rutStudent;
			System.out.println("-----------------------\n Agregar Nota");
			System.out.printf("Ingresa el RUT del Alumno:");
			rutStudent = menu.nextLine();
			
			materiasDelAlumno = alumnoServicio.materiasPorAlumnos(rutStudent);
			if(materiasDelAlumno.isEmpty())super.iniciarMenu();
			else {
				alumnoServicio.agregarNota(materiasDelAlumno,rutStudent,menu);
			
				
				System.out.println("Nota agregada ");
				System.out.println("----------------------------------------------");
				super.iniciarMenu();
		}
		}
	}

	@Override
	public void listarAlumnos() {
		if(alumnoServicio==null && archivoServicio==null) { 
		 System.out.println("No hay alumnos que listar.\nVolviendo al Menú Principal"); 
		 super.iniciarMenu();
		}else if(alumnoServicio==null) {
		 alumnoServicio = new AlumnoServicio();	
		 Utilidad.formatoListarAlumnos(alumnoServicio.listarAlumnos());
		 super.iniciarMenu();
		}else {
		 Utilidad.formatoListarAlumnos(alumnoServicio.listarAlumnos());
		 super.iniciarMenu();
		}
	}

	@Override
	public void terminarPrograma() {
		System.out.println("Saliendo del programa \nQue tenga un buen dia");
		System.exit(0);
	}

}
