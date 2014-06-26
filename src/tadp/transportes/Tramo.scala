package tadp.transportes

class Tramo(val inicio: Estacion, val fin: Estacion, val transporte: Transporte) {

  def costo: Double = transporte.costo(inicio, fin)

  def duracion: Double = this.transporte.calcularDuracion(inicio, fin)

}