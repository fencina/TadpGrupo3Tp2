class Subte(override val linea: String, override val estaciones: List[Estacion]) extends Transporte(linea, estaciones) {
  
    override def costo(estacionInicio: Estacion, estacionFin: Estacion) :Double = 4.5

}