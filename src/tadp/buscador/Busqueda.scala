package tadp.buscador

import tadp.transportes.Transporte
import tadp.dependencias.moduloExternoTransporte
import tadp.transportes.Tramo
import tadp.dependencias.moduloExternoTransporte
import tadp.transportes.CombinacionSubtes
import tadp.transportes.Subte
import tadp.transportes.CombinacionColectivoYOtro
import tadp.transportes.CombinacionSubtes
import tadp.transportes.CombinacionTrenes
import tadp.transportes.CombinacionTrenYSubte
import tadp.dependencias.moduloExternoTransporte
import tadp.transportes.Estacion
import tadp.transportes.Combinacion

class Busqueda {

  def comoViajo(origen: Direccion, destino: Direccion, descuento: Option[Descuento], criterio: Criterio, moduloExterno: moduloExternoTransporte): List[Viaje] = {
    val transportesOrigen: List[Transporte] = moduloExterno.getTransportesCercanos(origen)
    val transportesDestino: List[Transporte] = moduloExterno.getTransportesCercanos(destino)
    val viajes = this.obtenerViajes(transportesOrigen, transportesDestino, origen, destino, moduloExterno)

    descuento match {
      case Some(descuento) => viajes
        .map(viaje => this.aplicarDescuento(descuento, viaje))
        //.asInstanceOf[List[Viaje]] NO CASTEAR!
        .sorted(criterio)
      case None => viajes.sorted(criterio)
    }
  }

  def aplicarDescuento(descuento: Descuento, viaje: Viaje): DescuentoDeViaje = descuento match {
    case Turismo(zona, nombre) if (viaje.tengoAlgunTramoDeLaZona(zona)) =>
      viaje.aplicarDescuento(Turismo(zona, nombre))
    case Discapacitados(n) =>
      viaje.aplicarDescuento(Discapacitados(n))
    case Trabajo(n) => viaje.zonaEnLaQueTermina match {
      case ZonaTrabajo() => viaje.aplicarDescuento(Trabajo(n))
    }
  }

  def obtenerViajes(
    transportesOrigen: List[Transporte],
    transportesDestino: List[Transporte],
    origen: Direccion,
    destino: Direccion,
    moduloExterno: moduloExternoTransporte): List[Viaje] = {

    //Verificar tramos sin combinaciones
    val transportesDirectos = transportesOrigen.filter(tO => transportesDestino.contains(tO))

    val directos = for (t <- transportesDirectos) yield {
      // El m�dulo externo deber�a darme la estaci�n, no incluye una funcionalidad gen�rica de "est� cerca"!
      val estacionOrigen = t.estaciones.filter(e => moduloExterno.estanCerca(e.direccion, origen)).head
      val estacionDestino = t.estaciones.filter(e => moduloExterno.estanCerca(e.direccion, destino)).head
      new Viaje(List(new Tramo(estacionOrigen, estacionDestino, t, moduloExterno)), None)
    }

    //Verificar tramos con combinaciones
    val combinados = for {
      tOrigen <- transportesOrigen
      tDestino <- transportesDestino.filter(transporte => !transportesOrigen.contains(transporte))
      if moduloExterno.puedeCombinarse(tOrigen, tDestino, origen, destino)
    } yield {
      // no est� bien conseguir "head" de algo que est� cerca
      // el m�dulo retorna combinaciones de transportes, si necesit�s las dos estaciones podemos
      //   definir que el m�dulo me da la estaci�n donde bajo para hacer la combianci�n y la estaci�n donde
      //   me subo al pr�ximo transporte
      val estacionOrigen = tOrigen.estaciones.filter(e => moduloExterno.estanCerca(e.direccion, origen)).head
      val estacionDestino = tDestino.estaciones.filter(e => moduloExterno.estanCerca(e.direccion, destino)).head
      val estacionDondeBajar = moduloExterno.obtenerEstacionCombinacion(tOrigen, tDestino).get
      val estacionDondeSubir = moduloExterno.obtenerEstacionCombinacion(tDestino, tOrigen).get

      val tramo1 = new Tramo(estacionOrigen, estacionDondeBajar, tOrigen, moduloExterno)
      val tramo2 = new Tramo(estacionDondeSubir, estacionDestino, tDestino, moduloExterno)
      val combinacion = this.obtenerTipoCombinacion(tOrigen, tDestino, estacionDondeBajar, estacionDondeSubir, moduloExterno)
      new Viaje(List(tramo1, tramo2), Some(combinacion))
    }

    return directos ++ combinados
  }

  // Mover a viaje (ver m�s detalles en viaje)
  def obtenerTipoCombinacion(tOrigen: Transporte, tDestino: Transporte, e1: Estacion, e2: Estacion, moduloExterno: moduloExternoTransporte): Combinacion = {
    if (tOrigen.soySubte && tDestino.soySubte)
      return CombinacionSubtes(e1, e2, moduloExterno);
    else if (tOrigen.soyTren && tDestino.soyTren)
      return CombinacionTrenes(e1, e2, moduloExterno);
    else if ((tOrigen.soyTren && tDestino.soySubte) || (tOrigen.soySubte && tDestino.soyTren))
      return CombinacionTrenYSubte(e1, e2, moduloExterno);
    else
      return CombinacionColectivoYOtro(e1, e2, moduloExterno);
  }

}