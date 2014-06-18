class Viaje(val tramos: List[Tramo]) extends App{
  
  def costo() :Double = tramos.map(t => t.costo).sum
  
  
}