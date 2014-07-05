package tadp.transportes

case class Subte(override val linea: String, override val estaciones: List[Estacion]) extends Transporte(linea, estaciones) {
  
 override def duracionEstacion = 2;
  
    override def duracionCombinacionCon(t:Transporte,estacionInicio:Estacion,estacionFin:Estacion) : Double = t match{
      case Tren(linea,estaciones,listaPrecios) => return 5;  
      case Subte(linea,estaciones) => return 4;
      case Colectivo(linea,estaciones) => return moduloExterno.distanciaRecorrida(estacionInicio, estacionFin)/100*2.5;
    }
  
    override def costo(estacionInicio: Estacion, estacionFin: Estacion) :Double = 4.5

}