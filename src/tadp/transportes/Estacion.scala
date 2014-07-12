package tadp.transportes

import tadp.buscador.Direccion
import tadp.buscador.ZonaTrait

// NO USAR NULL!
class Estacion(val nombre: String, val direccion: Direccion) {
  def sosDeLaZona(zona: ZonaTrait) = this.direccion.zona == zona
}