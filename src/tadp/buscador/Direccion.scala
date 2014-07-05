package tadp.buscador

import tadp.transportes.Transporte

class Direccion(
  val descripcion: String = null,
  val zona: ZonaTrait,
  var transportesCercanos: List[Transporte] = null)
