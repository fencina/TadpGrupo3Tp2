package tadp.buscador

import tadp.transportes.Tramo
import tadp.transportes.Transporte
import tadp.transportes.Subte
import tadp.transportes.Tren
import tadp.transportes.Colectivo
import tadp.dependencias.moduloExternoTransporte

// No es necesario pasar las combinaciones por fuera del objeto porque son intrínsecas a la lista de tramos
// (la lista de tramos es toda la información que se necesita para calcular las combinaciones)
class Viaje(val tramos: List[Tramo], val moduloExterno: moduloExternoTransporte) {

  def costo: Double = {
    //Caso excepcion Subte y Subte
    if (tramos.size > 1) {

      val primerTramo = tramos.head
      val segundoTramo = tramos.tail.head
      val primerTramoTransporte = primerTramo.transporte
      val segundoTramoTransporte = segundoTramo.transporte

      val combinacion: List[Transporte] = List(primerTramoTransporte, segundoTramoTransporte)

      combinacion match {
        case List(_: Subte, _: Subte) => primerTramo.costo
        case _                        => tramos.map(t => t.costo).sum
      }

    } else {
      tramos.map(t => t.costo).sum
    }
  }

  def duracion: Double = tramos.map(t => t.duracion).sum + this.calcularDuracionesCombinaciones()

  def calcularDuracionesCombinaciones(): Double = {
    if (tramos.size < 2) return 0

    val primerTramo = tramos.head
    val segundoTramo = tramos.tail.head
    val primerTramoTransporte = primerTramo.transporte
    val segundoTramoTransporte = segundoTramo.transporte

    val combinacion: List[Transporte] = List(primerTramoTransporte, segundoTramoTransporte)

    combinacion match {
      case List(_: Subte, _: Subte) => 4
      case List(_: Tren, _: Tren)   => 6
      case List(_: Tren, _: Subte)  => 5
      case List(_: Subte, _: Tren)  => 5
      case List(_: Colectivo, _)    => moduloExterno.distanciaEntre(primerTramo.fin, segundoTramo.inicio) / 100 * 2.5
      case List(_, _: Colectivo)    => moduloExterno.distanciaEntre(primerTramo.fin, segundoTramo.inicio) / 100 * 2.5
    }
  }

  def tengoAlgunTramoDeLaZona(zona: ZonaTrait) = !this.tramos.filter(tramo => tramo.sosDeLaZona(zona)).isEmpty

  def estacionDelUltimoTramo = this.tramos.last.fin

  def zonaEnLaQueTermina = this.estacionDelUltimoTramo.direccion.zona

  def aplicarDescuento(descuento: Descuento): DescuentoDeViaje = new DescuentoDeViaje(tramos, moduloExterno, descuento, this)

}

class DescuentoDeViaje(tramos: List[Tramo], moduloExterno: moduloExternoTransporte, val descuento: Descuento, val viajeOriginal: Viaje) extends Viaje(tramos, moduloExterno) {

  override def costo: Double = descuento.aplicar(viajeOriginal.costo)

}