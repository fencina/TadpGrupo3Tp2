package tadp.dependencias

import tadp.transportes.Transporte
import tadp.transportes.Estacion
import tadp.buscador.Direccion

class moduloExternoTransporte {

  //TODO: definir la def

  def getTransportesCercanos(direccion: Direccion): List[Transporte] = direccion.transportesCercanos

  def obtenerCombinacion(t1: Transporte, t2: Transporte, origen: Direccion, destino: Direccion): Option[Estacion] = {
    val estacionOrigen = t1.estaciones.filter(e => e.direccion == origen).head
    val estacionDestino = t2.estaciones.filter(e => e.direccion == destino).head

    val combinaciones = t1
      .estaciones
      .filter { est =>
        t2.estaciones.map {
          e => e.direccion
        }.contains(est.direccion) &&
          est.direccion != origen &&
          est.direccion != destino
      }

    if (!combinaciones.isEmpty)
      return Some(combinaciones.head)

    return None// TODO! no retornar null!!!
  }

  def puedeCombinarse(t1: Transporte, t2: Transporte, origen: Direccion, destino: Direccion): Boolean = !this.obtenerCombinacion(t1, t2, origen, destino).isEmpty

  

  def distanciaRecorrida(estacionInicio: Estacion, estacionFin: Estacion): Int = {
    return 400;
  }

}

