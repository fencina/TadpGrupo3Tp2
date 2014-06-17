class moduloExternoTransporte extends App{

	  
    def getTransportesCercanos( direccion: Direccion) : List[Transporte] = direccion.descripcion  match{
      
      	case "origen1" => direccion.transportesCercanos
      
    	case "destino1" => direccion.transportesCercanos
      
    }
    
    def puedeCombinarse(t1: Transporte, t2: Transporte) :Integer = {
      
      if(!t1.combinaciones.isEmpty){
    	  if (t1.combinaciones.contains(t2.linea)){
    		  return t1.combinaciones(t2.linea) 
    	  }
    	  else{
    	    return null
    	  }
      }
      else{
        return null
      }
      
    }
    
    def distanciaRecorrida(origen: Direccion, destino: Direccion, transporte: Transporte){
      
    }
    
}

