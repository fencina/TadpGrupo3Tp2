package tadp.buscador

abstract class Criterio extends Ordering[Viaje] 

case class PorTiempo() extends Criterio {
  def compare(x: Viaje, y: Viaje): Int = x.duracion.toInt-y.duracion.toInt
}
case class PorPrecio() extends Criterio{
   def compare(x: Viaje, y: Viaje): Int = x.costo.toInt-y.costo.toInt
}

