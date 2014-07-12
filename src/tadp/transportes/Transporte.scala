package tadp.transportes

import tadp.dependencias.moduloExternoTransporte
import tadp.dependencias.moduloExternoTransporte

abstract class Transporte(val linea: String, val estaciones: List[Estacion]) {

  def duracionEstacion: Int // si no hay implementación es abstracto, no es 0!

  def costo(estacionInicio: Estacion, estacionFin: Estacion, moduloExterno: moduloExternoTransporte): Double

  def cantidadEstacionesEntre(inicio: Estacion, fin: Estacion): Int =
    Math.abs(estaciones.indexOf(fin) - estaciones.indexOf(inicio))

  def calcularDuracion(estacionInicio: Estacion, estacionFin: Estacion, moduloExterno: moduloExternoTransporte): Double =
    this.duracionEstacion * this.cantidadEstacionesEntre(estacionInicio, estacionFin)

  def kmtometros(dist: Double): Double = dist * 1000
  def metrostokm(dist: Double): Double = dist / 1000
  def horastominutos(horas: Double): Double = horas * 60
  def minutostohoras(minutos: Double): Double = minutos / 60

  // aca no puede ir, solo aplicable al colectivo!
  def kilometrosahora(kilometros: Double): Double = kilometros / velocidadColectivo;
  def distanciaAMinutos(d: Double): Double = this.horastominutos(this.kilometrosahora(this.metrostokm(d)))

  // NO! solo es responsabilidad del colectivo la velocidad del colectivo!!!
  def velocidadColectivo = 15

  // NO, POR DIOS NO!!!
  def soyTren = false
  def soyColectivo = false
  def soySubte = false
}