package tadp.buscador

abstract class Criterio extends Ordering[Viaje]

case class PorTiempo() extends Criterio {
  def compare(x: Viaje, y: Viaje): Int = (x.duracion - y.duracion).toInt
}
case class PorPrecio() extends Criterio {
  //Si el toInt lo hac�s antes de hacer la diferencia perd�s mucha precisi�n
  // ej: 4.7 .toInt = 4 => 4.7.toInt - 4.2.toInt => 0!?! 
  def compare(x: Viaje, y: Viaje): Int = (x.costo - y.costo).toInt
}

