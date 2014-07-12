package tadp.transportes

import tadp.dependencias.moduloExternoTransporte
import tadp.dependencias.moduloExternoTransporte
import tadp.dependencias.moduloExternoTransporte

case class Colectivo(override val linea: String, override val estaciones: List[Estacion]) extends Transporte(linea, estaciones) {

  override def calcularDuracion(estacionInicio: Estacion, estacionFin: Estacion, moduloExterno: moduloExternoTransporte): Double = {
    val distancia = moduloExterno.distanciaEntre(estacionInicio, estacionFin)
    return this.distanciaAMinutos(distancia)
  }

  //no tiene duración estación?
  override def duracionEstacion = 0 //???????????

  override def costo(estacionInicio: Estacion, estacionFin: Estacion, moduloExterno: moduloExternoTransporte): Double = {
    val distancia = moduloExterno.distanciaEntre(estacionInicio, estacionFin)
    return this.obtenerCosto(this.metrostokm(distancia))
  }

  def obtenerCosto(distancia: Double): Double = distancia match {
    case distancia if (distancia < 3) => 2.5
    case distancia if (distancia > 6) => 2.85
    case _                            => 2.75
  }

  // NO!
  override def soyColectivo = true

}