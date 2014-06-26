package tadp.estadisticas

import tadp.buscador.Viaje

object Estadisticas {

  var viajes: List[Viaje] = List()

  def addViajes(v: List[Viaje]) = {
    viajes = viajes ++ v
  }

}
