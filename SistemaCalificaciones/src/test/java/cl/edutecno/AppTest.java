package cl.edutecno;





import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.junit.jupiter.api.*;
import modelos.Alumno;
import modelos.Materia;
import modelos.MateriaEnum;
import servicios.AlumnoServicio;
import servicios.ArchivosServicio;
import servicios.PromedioServicioImp;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;



@DisplayName("Tests Clase AlumnoServicio")
public class AppTest 
{
	private static Logger logger = Logger.getLogger("cl.edutecno.AppTest");

	 AlumnoServicioImp alumnoServicio = new AlumnoServicioImp();
	 AlumnoServicioImp alumnoServicioMock = mock(AlumnoServicioImp.class);
	 Materia matematicas = new Materia();
	 Materia lenguaje = new Materia();
	 Alumno mapu = new Alumno();
	 
	 
	
	@BeforeAll
	public static void setup(){
		logger.info("Inicio clase AppTest");
    }
	
	
   
	//test para calcularPromedio se usa un Dummy que es el ArrayList 'x'. no tiene otras pruebas incorporadas como por ejemplo validación del tipo de dato del parámetro porque es un método interno (o de backend) que no interactúa con el usuario por lo que no hay posibilidad de ingresar un parámetro erróneo.
	@Test
	@DisplayName("Test calcularPromedio")
	public void calcularPromedioTest(){
		logger.info("\n\n el retorno de la llamada al método debe ser 15 para que la prueba pase");
		 ArrayList<Double> x = new ArrayList<Double> (Arrays.asList(15.0,5.0,12.0,28.0));
		  assertEquals(15.0,PromedioServicioImp.calcularPromedio(x));
	}
	
	
	
	//test que verifica que "crearAlumno" agrege el alumno creado al mapa. En este caso el alumno es: mapu.
	@DisplayName("Test crearAlumno")
	@Test
	public void crearAlumnoTest() {
		logger.info("\n\nel tamaño del ArrayList debe ser 1 para que el test pase");
		alumnoServicio.alumnoServiciox.crearAlumno(mapu);
		assertTrue(alumnoServicio.alumnoServiciox.getListaAlumnos().size()==1);
	}
	
	//'agregarMateria' funciona según dos conjuntos dependiendo de donde se encuentre el alumno al que se le quiere agregar la materia.
	// Por lo que se testean los dos casos posibles, el primero es que el alumno se encuentre en 'listaAlumnos' de la clase 'AlumnoServicio'
	// y el segundo es que el alumno se encuentre en alumnosACargar de la clase 'ArchivosServicio'.
	@DisplayName("Test agregrarMateria")
	@Test
	public void agregrarMateriaTest() {
		logger.info("\n\n los alumnos 'mapu' y 'mapache' deben contener las materias añididas para que el test pase");
		
		
		//se asigna un rut a 'mapu' y se agrega al mapa 'listaAlumnos'
		lenguaje.setNombre(MateriaEnum.LENGUAJE);
		mapu.setRut("123");
		alumnoServicio.alumnoServiciox.crearAlumno(mapu);

		
		//se crea 'mapache' y se agrega al arrayList 'alumnosACargar'
		//el método agrega la materia al alumno perteneciente a 'alumnosACargar' sólo si éste ya poseía una materia antes de llamar al método
		//(se requiere que ya exista una materia en el alumno de 'alumnosACargar' porque el funcionamiento del método considera para 'alumnosACargar' sólo a los alumnos importados desde 'notas.csv'
		//y todos ellos poseen por lo menos 1 materia. En específico es para que no se salte el ciclo for de la línea 64 de AlumnoServicio). 
		Alumno mapache = new Alumno();
		mapache.setRut("321");
		List<Materia> listamaterias = new ArrayList<Materia>();
		mapache.setMaterias(listamaterias);
		mapache.getMaterias().add(lenguaje);
		matematicas.setNombre(MateriaEnum.MATEMATICAS);
		ArchivosServicio.alumnosACargar.add(mapache);
		
		
		//llamada al método que queremos probar
		alumnoServicio.alumnoServiciox.agregarMateria(mapu.getRut(), lenguaje);
		alumnoServicio.alumnoServiciox.agregarMateria(mapache.getRut(), matematicas);
		
		
		/*testea que se haya agregado correctamente la materia, comprobando que 'lenguaje' está en 'mapu'*/
		assertEquals(lenguaje , mapu.getMaterias().get(0));
		
		/*testea que se haya agregado correctamente la materia, comprobando que 'matematicas' está en 'mapache'.*/
		assertEquals(matematicas, mapache.getMaterias().get(1));
				
 		
	}
	
	
	//test que prueba que materiasPorAlumnos retorne la lista de materias de un alumno según 3 casos: 
	//1.que no esté el alumno del que se buscan materias; 2. Que esté en listaAlumnos o; 3. Que esté en alumnosACargar.
	@DisplayName("Test materiasPorAlumnos")
	@Test
	public void materiasPorAlumnosTest() {
	
		logger.info("\n\n el método debe retornar las materias correspondientes a 'mapu' y 'mapache' para pasar el test.\n"
				+ " El método a testear para los dos casos distintos es:\n"
				+ " alumnoServicio.alumnoServiciox.materiasPorAlumnos(mapu.getRut());\n"
				+ " alumnoServicio.alumnoServiciox.materiasPorAlumnos(mapache.getRut());" 
				);
		
		//Se le añade una lista de materias a mapu y éste es agregado al mapa 'listaAlumnos' 
		List<Materia> materias = new ArrayList<Materia>(Arrays.asList(lenguaje));
		mapu.setMaterias(materias);
		mapu.setRut("123");
		alumnoServicio.alumnoServiciox.crearAlumno(mapu);
	
	
	    
	    //se crea y añade 'mapache' a 'alumnosACargar'.
		Alumno mapache = new Alumno();
		mapache.setRut("321");
		matematicas.setNombre(MateriaEnum.MATEMATICAS);
		List<Materia> listamaterias = new ArrayList<Materia>(Arrays.asList(lenguaje,matematicas));
		mapache.setMaterias(listamaterias);
		ArchivosServicio.alumnosACargar.add(mapache);
		
		
		
	    //se crea un objeto AlumnoServicio 'al' y se le setea un mapa de Alumnos a su atributo correspondiente; 
		//Se establece que el retorno de 'alumnoServicioMock.getAlumnoServicio()' es 'al'.
		AlumnoServicio al = new AlumnoServicio();
		Map<String,Alumno>mapa = new LinkedHashMap<String,Alumno>();
		mapa.put(mapu.getRut(), mapu);
		mapa.put(mapache.getRut(), mapache);
		al.setListaAlumnos(mapa);
		when(alumnoServicioMock.getAlumnoServicio()).thenReturn(al);
		
		
		
		

	    //para cada caso corrobora que el valor de retorno del método 'materiasPorAlumnos' sea igual al de la lista de materias del alumno correspondiente, alumno almacenado en el atributo tipo Map 'listaAlumnos' de 'al'.
		assertEquals(alumnoServicioMock.getAlumnoServicio().getListaAlumnos().get(mapu.getRut()).getMaterias(),alumnoServicio.alumnoServiciox.materiasPorAlumnos(mapu.getRut()));
		assertEquals(alumnoServicioMock.getAlumnoServicio().getListaAlumnos().get(mapache.getRut()).getMaterias(),alumnoServicio.alumnoServiciox.materiasPorAlumnos(mapache.getRut()));
		
		
	
	}
	
	
	
	//el método listarAlumnos retorna un mapa y añade los alumnos del arraylist 'alumnosACargar' si es que no se han añadido.
	@DisplayName("Test listarAlumnos")
	@Test
	public void listarAlumnosTest() {
		logger.info("\n\n el método debe retornar el mapa 'listaAlumnos' no nulo, y 'listaAlumnos' debe contener el alumno añadido proveniente desde alumnosACargar para pasar el test");
		
		ArchivosServicio.alumnosACargar.add(mapu);
		alumnoServicio.alumnoServiciox.listarAlumnos();
		assertNotNull(alumnoServicio.alumnoServiciox.getListaAlumnos());
		assertEquals(mapu,alumnoServicio.alumnoServiciox.getListaAlumnos().get(mapu.getRut()));
	}
	
		
		
		
	

	
	

}
