class Tren(override val linea: String, override val estaciones: List[Estacion], val listaPrecios: List[PrecioTren]) extends Transporte(linea, estaciones) {
  
    override def costo(estacionInicio: Estacion, estacionFin: Estacion) :Double = obtenerPrecio(this.cantidadEstacionesEntre(estacionInicio,estacionFin));
    
    
    def obtenerPrecio(cantidadEstaciones : Int):Double = this.listaPrecios.filter(e => e.cantidadLimiteEstaciones >= cantidadEstaciones).map(e=> e.precio).sortWith(_ < _).head 

}