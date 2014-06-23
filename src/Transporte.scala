
abstract class Transporte(val linea: String, val estaciones: List[Estacion]) extends App  {
  
  def costo(estacionInicio: Estacion, estacionFin: Estacion) : Double;
  
  def cantidadEstacionesEntre(inicio:Estacion,fin:Estacion) = fin.numero - inicio.numero;
  

}