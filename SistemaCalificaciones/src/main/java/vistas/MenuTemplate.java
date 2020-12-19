package vistas;
import java.util.Scanner;

public class MenuTemplate {

	Scanner menu = new Scanner(System.in);
    
	public static final Menu callMenu = new Menu();//constante estática para que al llamar denuevo a iniciarMenú no se borren los datos almacenados en variables de objeto, objetos que se crean al llamar métodos del objeto tipo Menu "callMenu". 
	
	public void cargarDatos() {}
	
	public void exportarDatos() {}
	
	public void crearAlumno(){}
	
	public void agregarMateria(){}
	
	public void agregarNotaPasoUno() {}
	
	public void listarAlumnos() {}
	
	public void terminarPrograma() {}
	
	public void iniciarMenu() {
		
		
		String seleccion;
		
		System.out.println("1 - Crear Alumnos \n2 - Listar Alumnos \n3 - Agregar Materias \n4 - Agregar Notas \n5 - Cargar Datos "
					+ "\n6 - Exportar Datos \n7 - Salir");
		System.out.printf("Seleccion: ");
		seleccion = menu.nextLine();
		switch(seleccion) {
		case "1": callMenu.crearAlumno();
			break;
		case "2": callMenu.listarAlumnos();
			break;
		case "3": callMenu.agregarMateria();
			break;
		case "4": callMenu.agregarNotaPasoUno();
			break;
		case "5": callMenu.cargarDatos();
			break;
		case"6": callMenu.exportarDatos();
			break;
		case "7": callMenu.terminarPrograma();
			break;
		default: System.out.println("Ha ingresado una opcion no valida \nVolviendo al menu\n");
				iniciarMenu();
			break;
		}
	}


		
	
}
