package tadp.transportes

import tadp.dependencias.moduloExternoTransporte

case class Tren(override val linea: String, override val estaciones: List[Estacion], val listaPrecios: List[PrecioTren]) extends Transporte(linea, estaciones) {

  override def duracionEstacion = 3;

  override def costo(estacionInicio: Estacion, estacionFin: Estacion, moduloExterno: moduloExternoTransporte): Double =
    obtenerCosto(this.cantidadEstacionesEntre(estacionInicio, estacionFin));

  def obtenerCosto(cantidadEstaciones: Int): Double = {
    this.listaPrecios
      .filter {
        e => e.cantidadLimiteEstaciones >= cantidadEstaciones
      }
      .map {
        e => e.precio
      }
      .sortWith(_ < _)
      .head
  }


}

class PrecioTren(val precio: Double, val cantidadLimiteEstaciones: Int)