case class Subte(override val linea: String, override val estaciones: List[Estacion]) extends Transporte(linea, estaciones) {
  
 override def duracionEstacion = 2;
  
    override def duracionCombinacionCon(t:Transporte) : Double = t match{
      case Tren(linea,estaciones,listaPrecios) => return 5;  
      case Subte(linea,estaciones) => return 4;  
    }
  
    override def costo(estacionInicio: Estacion, estacionFin: Estacion) :Double = 4.5

}