package tadp.transportes

import tadp.dependencias.moduloExternoTransporte

trait Combinacion {
	def duracion() : Double;
}



case class CombinacionSubtes(val estacionInicio: Estacion,val estacionFin: Estacion,moduloExterno:moduloExternoTransporte)  extends Combinacion{
  def duracion() = 4; 
}
case class CombinacionTrenes(val estacionInicio: Estacion,val estacionFin: Estacion,moduloExterno:moduloExternoTransporte) extends Combinacion{
  def duracion() = 6; 
}
case class CombinacionTrenYSubte(val estacionInicio: Estacion,val estacionFin: Estacion,moduloExterno:moduloExternoTransporte) extends Combinacion{
  def duracion() = 5; 
}
case class CombinacionColectivoYOtro(val estacionInicio: Estacion,val estacionFin: Estacion,moduloExterno:moduloExternoTransporte) extends Combinacion{
  def duracion() = moduloExterno.distanciaEntre(estacionInicio, estacionFin)/100*2.5 
}