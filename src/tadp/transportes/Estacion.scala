package tadp.transportes

import tadp.buscador.Direccion
import tadp.buscador.ZonaTrait

class Estacion( val nombre: String, val direccion: Direccion = null){
  
  def sosDeLaZona(zona:ZonaTrait) = this.direccion.zona == zona
}