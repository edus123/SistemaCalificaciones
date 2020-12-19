package cl.edutecno;

import servicios.AlumnoServicio;
import servicios.ArchivosServicio;

public class AlumnoServicioImp {
AlumnoServicio alumnoServiciox = new AlumnoServicio();
ArchivosServicio archivoServicio = new ArchivosServicio();

public AlumnoServicio getAlumnoServicio() {
	return alumnoServiciox;
}

public void setAlumnoServicio(AlumnoServicio alumnoServicio) {
	this.alumnoServiciox = alumnoServicio;
}

public ArchivosServicio getArchivoServicio() {
	return archivoServicio;
}

public void setArchivoServicio(ArchivosServicio archivoServicio) {
	this.archivoServicio = archivoServicio;
}



}
