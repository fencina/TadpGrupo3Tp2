
abstract class Transporte(val linea: String, val estaciones: List[Estacion])  {
  
  def duracionEstacion : Double = 0;

  def duracionCombinacionCon(transporte:Transporte):Double
  
  def costo(estacionInicio: Estacion, estacionFin: Estacion) : Double;
  
  def cantidadEstacionesEntre(inicio:Estacion,fin:Estacion):Int = fin.numero - inicio.numero;

  def calcularDuracion(estacionInicio:Estacion, estacionFin:Estacion) = this.duracionEstacion * this.cantidadEstacionesEntre(estacionInicio, estacionFin)

  def kmtometros(dist:Double): Double = dist*1000 
  def metrostokm(dist:Double): Double = dist/1000
  def horastominutos(horas:Double): Double = horas*60
  def minutostohoras(minutos:Double): Double = minutos/60
  def kilometrosahora(kilometros:Double): Double = kilometros/velocidadColectivo;
  def distanciaAMinutos(d:Double): Double =  this.horastominutos(this.kilometrosahora(this.metrostokm(d)) )
  
  def velocidadColectivo = 15
}