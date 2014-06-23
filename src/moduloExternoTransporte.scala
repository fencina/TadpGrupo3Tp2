class moduloExternoTransporte extends App{

	  
    def getTransportesCercanos( direccion: Direccion) : List[Transporte] = {
      
      	 direccion.transportesCercanos
    }
    
    def obtenerCombinacion(t1: Transporte, t2: Transporte, origen: Direccion, destino: Direccion) :Estacion = {

      val estacionOrigen = t1.estaciones.filter(e => e.direccion == origen).head
	  val estacionDestino = t2.estaciones.filter(e => e.direccion == destino).head
	  
      val combinaciones = t1.estaciones.filter(est => t2.estaciones.map{e=> e.direccion}.contains(est.direccion) && est.direccion !=origen && est.direccion != destino)
      
      if(!combinaciones.isEmpty)
        return combinaciones.head
       
       return null
      
    }
    
    def puedeCombinarse(t1: Transporte, t2: Transporte, origen: Direccion, destino: Direccion) :Boolean = {
      
      return this.obtenerCombinacion(t1, t2, origen, destino) != null
      
    }
    
    def distanciaRecorrida(estacionInicio: Estacion, estacionFin: Estacion) :Int ={
      return 1;
    }
    
}

