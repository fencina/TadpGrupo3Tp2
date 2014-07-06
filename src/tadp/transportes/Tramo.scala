package tadp.transportes

import tadp.buscador.ZonaTrait
import tadp.dependencias.moduloExternoTransporte

class Tramo(val inicio: Estacion, val fin: Estacion, val transporte: Transporte, val moduloExterno: moduloExternoTransporte) {

  def costo: Double = transporte.costo(inicio, fin, moduloExterno)

  def duracion: Double = this.transporte.calcularDuracion(inicio, fin, moduloExterno)

  def sosDeLaZona(zona: ZonaTrait) = this.inicio.sosDeLaZona(zona) || this.fin.sosDeLaZona(zona)

}