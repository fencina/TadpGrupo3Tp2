package tadp.buscador

import tadp.transportes.Tramo
import tadp.transportes.Combinacion

class Viaje(val tramos: List[Tramo], val combinacion: Option[Combinacion]) {

  
  def costo: Double = {
    
    //Caso excepcion Subte y Subte
    if (tramos.size>1)
    if (tramos.head.transporte.soySubte && tramos.tail.head.transporte.soySubte)
      return tramos.head.costo
      
    return tramos.map(t => t.costo).sum }

  def duracion: Double = tramos.map(t => t.duracion).sum + this.calcularDuracionesCombinaciones()

  def calcularDuracionesCombinaciones(): Double = combinacion match  {
      case Some(comb) => comb.duracion()
      case None => 0
  }
  
  def tengoAlgunTramoDeLaZona(zona:ZonaTrait) = !this.tramos.filter(tramo => tramo.sosDeLaZona(zona)).isEmpty
  
  def estacionDelUltimoTramo = this.tramos.last.fin
  
  def zonaEnLaQueTermina = this.estacionDelUltimoTramo.direccion.zona
  
  def aplicarDescuento(descuento : Descuento) = new DescuentoDeViaje(tramos,combinacion,descuento,this)
  
  

}


class DescuentoDeViaje( tramos: List[Tramo], combinacion: Option[Combinacion],val descuento: Descuento, val viajeOriginal: Viaje) extends Viaje(tramos,combinacion){
  
 override def costo : Double = descuento.aplicar(viajeOriginal.costo)
  
}