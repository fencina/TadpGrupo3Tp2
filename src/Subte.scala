class Subte(override val linea: String, override val estacion: Estacion, override val combinaciones: Map[String,Int]) extends Transporte(linea, estacion, combinaciones ) {
  
    override def precio(distancia: Int) :Double ={
    	return 4.5
    }

}