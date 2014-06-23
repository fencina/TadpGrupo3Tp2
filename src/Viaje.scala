
class Viaje(val tramos: List[Tramo]){
  
  def costo() :Double = tramos.map(t => t.costo).sum
  
  def duracion : Double = tramos.map(t=> t.duracion ).sum + this.calcularDuracionesCombinaciones()
  
  
  def calcularDuracionesCombinaciones() : Double =
  {  
    
    if (this.tramos.size>1) 
    	return this.tramos.head.transporte.duracionCombinacionCon(this.tramos.tail.head.transporte)
    return 0;
    
  }
}