package tadp.transportes

import tadp.dependencias.moduloExternoTransporte

case class Subte(override val linea: String, override val estaciones: List[Estacion]) extends Transporte(linea, estaciones) {

  override def duracionEstacion = 2;

  override def costo(estacionInicio: Estacion, estacionFin: Estacion, moduloExterno: moduloExternoTransporte): Double = 4.5



}