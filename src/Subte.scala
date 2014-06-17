class Subte(override val linea: String, override val estaciones: List[Estacion]) extends Transporte(linea, estaciones) {
  
    override def precio(distancia: Int) :Double ={
    	return 4.5
    }

}