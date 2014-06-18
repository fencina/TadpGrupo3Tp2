class Tramo(val inicio: Estacion, val fin: Estacion, val transporte: Transporte) extends App{
  
  def costo() :Double = transporte.costo(inicio,fin)
  
}