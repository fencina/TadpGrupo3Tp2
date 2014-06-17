class moduloExternoTransporte extends App{

	  
    def getTransportesCercanos( direccion: Direccion) : List[Transporte] = direccion.descripcion  match{
      
      	case "origen1" => direccion.transportesCercanos
      
    	case "destino1" => direccion.transportesCercanos
      
    }
    
    def obtenerCombinaciones(t1: Transporte, t2: Transporte) :List[Estacion] = {
    		
      t1.estaciones.filter(est => t2.estaciones.contains(est) )
    }
    
    def distanciaRecorrida(origen: Direccion, destino: Direccion, transporte: Transporte){
      
    }
    
}

