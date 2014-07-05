package tadp.buscador

import tadp.transportes.Transporte
import tadp.dependencias.moduloExternoTransporte
import tadp.transportes.Tramo
import tadp.estadisticas.Estadisticas

class Busqueda {

  def comoViajo(origen: Direccion, destino: Direccion, descuento: Descuento = SinDescuento(), criterio: Criterio = SinCriterio()): List[Viaje] = {
    val moduloExterno = new moduloExternoTransporte

    val transportesOrigen: List[Transporte] = moduloExterno.getTransportesCercanos(origen)
    val transportesDestino = moduloExterno.getTransportesCercanos(destino)
    var viajes = this.obtenerViajes(transportesOrigen, transportesDestino, origen, destino)
    this.chequearDescuento(descuento, viajes)
    viajes = this.ordenarPorCriterio(criterio, viajes)

    Estadisticas.addViajes(viajes)

    return viajes

  }

  def ordenarPorCriterio(criterio: Criterio, viajes: List[Viaje]) = criterio match {
    case PorTiempo()   => viajes.sortWith((v1, v2) => v1.duracion < v2.duracion)
    case PorPrecio()   => viajes.sortWith((v1, v2) => v1.costo < v2.costo)
    case SinCriterio() => viajes
  }

  def chequearDescuento(descuento: Descuento, viajes: List[Viaje]) = descuento match {
    case Turismo(zona, nombre) =>
      for (viaje <- viajes)
        if (!viaje.tramos.filter(tramo => tramo.inicio.direccion.zona == zona || tramo.fin.direccion.zona == zona).isEmpty)
          viaje.descuento = Turismo(zona, nombre)
    case Discapacitados(n) =>
      for (viaje <- viajes)
        viaje.descuento = Discapacitados(n)
    case Trabajo(n) => for (viaje <- viajes) viaje.tramos.last.fin.direccion.zona match {
      case ZonaTrabajo() => viaje.descuento = Trabajo(n)
    }
    case SinDescuento() => for (viaje <- viajes)viaje.descuento = SinDescuento()

  }

  def obtenerViajes(transportesOrigen: List[Transporte], transportesDestino: List[Transporte], origen: Direccion, destino: Direccion): List[Viaje] = {

    val moduloExterno = new moduloExternoTransporte
    var viajes: List[Viaje] = List()

    //Verificar tramos sin combinaciones
    val transportesDirectos = transportesOrigen.filter(tO => transportesDestino.contains(tO))

    for (t <- transportesDirectos) {
      val estacionOrigen = t.estaciones.filter(e => e.direccion == origen).head
      val estacionDestino = t.estaciones.filter(e => e.direccion == destino).head

      viajes = viajes :+ new Viaje(List(new Tramo(estacionOrigen, estacionDestino, t)))
    }

    //Verificar tramos con combinaciones
    for (tOrigen <- transportesOrigen) {
      for (tDestino <- transportesDestino.filter(transporte => !transportesOrigen.contains(transporte))) {
        if (moduloExterno.puedeCombinarse(tOrigen, tDestino, origen, destino)) {
          val estacionOrigen = tOrigen.estaciones.filter(e => e.direccion == origen).head
          val estacionDestino = tDestino.estaciones.filter(e => e.direccion == destino).head
          val estacionCombinacion = moduloExterno.obtenerCombinacion(tOrigen, tDestino, origen, destino).get
          val tramo1 = new Tramo(estacionOrigen, estacionCombinacion, tOrigen)
          val tramo2 = new Tramo(estacionCombinacion, estacionDestino, tDestino)
          viajes = viajes :+ new Viaje(List(tramo1, tramo2))
        }
      }
    }

    return viajes
  }

}