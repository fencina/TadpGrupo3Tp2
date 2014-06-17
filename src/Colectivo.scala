class Colectivo(override val linea: String, override val estacion: Estacion, override val combinaciones: Map[String,Int]) extends Transporte(linea, estacion, combinaciones ) {
  
  override def precio(distancia: Int) :Double = distancia match {
    case distancia if (distancia < 3) => 2.5
    case distancia if (distancia > 6) => 2.85
    case _ => 2.75
  }

}