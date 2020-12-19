package servicios;


import java.util.Iterator;
import java.util.List;

public class PromedioServicioImp {

	public PromedioServicioImp() {}
		
		public static Double calcularPromedio(List<Double> a) {//el cuál recibe una lista de valores y retorna el promedio.
			Double SumatoriadeNotas = 0.0;
			a.iterator();
			Iterator<Double> iterator = a.iterator(); 
			
			while (iterator.hasNext()) { 
				SumatoriadeNotas +=  iterator.next();
			}
			  
			double Promedio =  SumatoriadeNotas / a.size(); 
	        return Promedio;
	   }
		
		
		
		 
	

}
