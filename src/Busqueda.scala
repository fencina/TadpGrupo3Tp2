class Busqueda extends App{
  
  val moduloExterno = new moduloExternoTransporte
  
  def comoViajo(origen: Direccion, destino: Direccion){
    
    val transportesOrigen = moduloExterno.getTransportesCercanos(origen)
    val transportesDestino = moduloExterno.getTransportesCercanos(destino)
    val viajes = this.calcularRecorrido(transportesOrigen, transportesDestino)
    
  }
  
  def calcularTramos(transportesOrigen: List[Transporte], transportesDestino: List[Transporte],tramos:  List[Tramo] = null){
    
    //Verificar tramos sin combinaciones
    for (tOrigen <- transportesOrigen)
      for (tDestino <- transportesDestino) 
        if(tOrigen.linea == tDestino.linea ){
          val tramo = new Tramo(tOrigen.estacion ,tDestino.estacion )
//          tramos :+ tramo
          println(tOrigen.linea )
        }
  }
  
  def calcularRecorrido(tOrigen: List[Transporte], tDestino: List[Transporte]){
    
  }
  

}