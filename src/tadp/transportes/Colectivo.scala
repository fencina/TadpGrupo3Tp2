package tadp.transportes

import tadp.dependencias.moduloExternoTransporte
import tadp.dependencias.moduloExternoTransporte
import tadp.dependencias.moduloExternoTransporte

case class Colectivo(override val linea: String, override val estaciones: List[Estacion]) extends Transporte(linea, estaciones) {

  override def calcularDuracion(estacionInicio: Estacion, estacionFin: Estacion, moduloExterno: moduloExternoTransporte): Double = {
    val distancia = moduloExterno.distanciaEntre(estacionInicio, estacionFin)
    return this.distanciaAMinutos(distancia)
  }

  // aca no puede ir, solo aplicable al colectivo!
  def kilometrosahora(kilometros: Double): Double = kilometros / velocidadColectivo;
  def distanciaAMinutos(d: Double): Double = this.horastominutos(this.kilometrosahora(this.metrostokm(d)))

  // NO! solo es responsabilidad del colectivo la velocidad del colectivo!!!
  def velocidadColectivo = 15

 def duracionEstacion=0;
  
  override def costo(estacionInicio: Estacion, estacionFin: Estacion, moduloExterno: moduloExternoTransporte): Double = {
    val distancia = moduloExterno.distanciaEntre(estacionInicio, estacionFin)
    return this.obtenerCosto(this.metrostokm(distancia))
  }

  def obtenerCosto(distancia: Double): Double = distancia match {
    case distancia if (distancia < 3) => 2.5
    case distancia if (distancia > 6) => 2.85
    case _                            => 2.75
  }



}