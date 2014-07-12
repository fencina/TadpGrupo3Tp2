package tadp.buscador

import tadp.transportes.Tramo
import tadp.transportes.Combinacion

// No es necesario pasar las combinaciones por fuera del objeto porque son intrínsecas a la lista de tramos
// (la lista de tramos es toda la información que se necesita para calcular las combinaciones)
class Viaje(val tramos: List[Tramo], val combinacion: Option[Combinacion]) {

  def costo: Double = {

    //Caso excepcion Subte y Subte
    if (tramos.size > 1)
      /*
       * tramos.head.transporte..... mismo problema que antes: responsabilidad del tramo, le pregunto
       * al tramo, no le saco todos los datos y hago el cálculo afuera
       * 
       * Además, este if solo aplica al subte, debería ser responsabilidad del modelado de combinaciones
       * o de otra forma, que sea un patternmatching fuera de ellos seleccionando el calculo del costo
       * por cada tipo de combinación de transportes
       */
      if (tramos.head.transporte.soySubte && tramos.tail.head.transporte.soySubte)
        return tramos.head.costo

    return tramos.map(t => t.costo).sum
  }

  // hacer patternmatching con los tramos para conseguir los tiempos de combinación
  // cuidado, List es ordenado por lo que List(1,2) != List(2,1)
  // Set NO es ordenado por lo que Set(1,2) == Set(2,1)
  val ejemplo = List(1, 2, 3) match {
    case List()                       => "nada"
    case List(unSoloNumero)           => unSoloNumero.toString
    case List(2, 3)                   => "dos y tres"
    case List(x, y) if x > 1 && y < 5 => "otra cosa?"
    case List(_: Int, _: Int)         => "lista con dos ints"
  }

  def duracion: Double = tramos.map(t => t.duracion).sum + this.calcularDuracionesCombinaciones()

  def calcularDuracionesCombinaciones(): Double = combinacion.map(_.duracion).getOrElse(0)
  /* Usar combinadores funcionales!!!
    combinacion match {
    case Some(comb) => comb.duracion()
    case None       => 0
  }*/

  def tengoAlgunTramoDeLaZona(zona: ZonaTrait) = !this.tramos.filter(tramo => tramo.sosDeLaZona(zona)).isEmpty

  def estacionDelUltimoTramo = this.tramos.last.fin

  def zonaEnLaQueTermina = this.estacionDelUltimoTramo.direccion.zona

  def aplicarDescuento(descuento: Descuento): DescuentoDeViaje = new DescuentoDeViaje(tramos, combinacion, descuento, this)

}

class DescuentoDeViaje(tramos: List[Tramo], combinacion: Option[Combinacion], val descuento: Descuento, val viajeOriginal: Viaje) extends Viaje(tramos, combinacion) {

  override def costo: Double = descuento.aplicar(viajeOriginal.costo)

}