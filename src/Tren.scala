case class Tren(override val linea: String, override val estaciones: List[Estacion], val listaPrecios: List[PrecioTren]) extends Transporte(linea, estaciones) {
  
  override def duracionEstacion = 3;
  
    override def duracionCombinacionCon(t:Transporte) : Double = t match{
      case Tren(linea,estaciones,listaPrecios) => return 6;  
      case Subte(linea,estaciones) => return 5;  
    }
    
  def duracionCombinacionCon(tren:Tren) = 6
  def duracionCombinacionCon(subte:Subte) = 5
  
    override def costo(estacionInicio: Estacion, estacionFin: Estacion) :Double = obtenerCosto(this.cantidadEstacionesEntre(estacionInicio,estacionFin));
    
    
    def obtenerCosto(cantidadEstaciones : Int):Double = this.listaPrecios.filter(e => e.cantidadLimiteEstaciones >= cantidadEstaciones).map(e=> e.precio).sortWith(_ < _).head 

}