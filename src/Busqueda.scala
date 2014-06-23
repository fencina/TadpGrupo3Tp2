class Busqueda {
  
  
  def comoViajo(origen: Direccion, destino: Direccion,descuento:Descuento) :List[Viaje] = {
    val moduloExterno = new moduloExternoTransporte
    
    val transportesOrigen: List[Transporte] = moduloExterno.getTransportesCercanos(origen)
    val transportesDestino = moduloExterno.getTransportesCercanos(destino)
    var viajes = this.obtenerViajes(transportesOrigen, transportesDestino, origen, destino)
    viajes = this.aplicarDescuento(descuento,viajes)
    
    return  viajes
    
   
    
  }
  
  def aplicarDescuento(descuento:Descuento,viaje:List[Viaje])  = descuento match{
    case Turismo(n) => 
      
      
  } 
  
  def obtenerViajes(transportesOrigen: List[Transporte], transportesDestino: List[Transporte], origen: Direccion, destino: Direccion) :List[Viaje] = {
    
    val moduloExterno = new moduloExternoTransporte
    var viajes: List[Viaje] = List()
    
    //Verificar tramos sin combinaciones
    val transportesDirectos = transportesOrigen.filter(tO => transportesDestino.contains(tO))
    
    for(t <- transportesDirectos){
      val estacionOrigen = t.estaciones.filter(e => e.direccion == origen).head
      val estacionDestino = t.estaciones.filter(e => e.direccion == destino).head
      
      viajes = viajes :+ new Viaje(List(new Tramo(estacionOrigen,estacionDestino,t))) 
    }
    
    //Verificar tramos con combinaciones
    for(tOrigen <- transportesOrigen){
      for(tDestino <- transportesDestino.filter(transporte => !transportesOrigen.contains(transporte))){
        if(moduloExterno.puedeCombinarse(tOrigen, tDestino, origen, destino)){
            val estacionOrigen = tOrigen.estaciones.filter(e => e.direccion == origen).head
            val estacionDestino = tDestino.estaciones.filter(e => e.direccion == destino).head
            val estacionCombinacion = moduloExterno.obtenerCombinacion(tOrigen, tDestino,origen, destino)
            val tramo1 = new Tramo(estacionOrigen,estacionCombinacion,tOrigen)
            val tramo2 = new Tramo(estacionCombinacion,estacionDestino,tDestino)
            viajes = viajes :+ new Viaje(List(tramo1,tramo2))
        }
      }
    }
    
    Estadisticas.addViajes(viajes)
    
    return viajes
  }
    

}