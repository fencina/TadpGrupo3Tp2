class Busqueda extends App{
  
  
  def comoViajo(origen: Direccion, destino: Direccion) :List[Viaje] = {
    val moduloExterno = new moduloExternoTransporte
    
    val transportesOrigen: List[Transporte] = moduloExterno.getTransportesCercanos(origen)
    val transportesDestino = moduloExterno.getTransportesCercanos(destino)
    val viajes = this.obtenerViajes(transportesOrigen, transportesDestino, origen, destino)
    return viajes
    
  }
  
  def obtenerViajes(transportesOrigen: List[Transporte], transportesDestino: List[Transporte], origen: Direccion, destino: Direccion) :List[Viaje] = {
    
    var viajes: List[Viaje] = List()
    
    //Verificar tramos sin combinaciones
    val transportesDirectos = transportesOrigen.filter(tO => transportesDestino.contains(tO))
    
    for(t <- transportesDirectos){
      val estacionOrigen = t.estaciones.filter(e => e.direccion == origen).head
      val estacionDestino = t.estaciones.filter(e => e.direccion == destino).head
      
      viajes = viajes :+ new Viaje(List(new Tramo(estacionOrigen,estacionDestino,t))) 
    }
    
    return viajes
  }
    
  def calcularRecorrido(tOrigen: List[Transporte], tDestino: List[Transporte]){
    
  }
  

}