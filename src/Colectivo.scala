class Colectivo(override val linea: String, override val estaciones: List[Estacion]) extends Transporte(linea, estaciones) {
  
  override def precio(distancia: Int) :Double = distancia match {
    case distancia if (distancia < 3) => 2.5
    case distancia if (distancia > 6) => 2.85
    case _ => 2.75
  }

}