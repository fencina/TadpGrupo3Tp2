package tadp.buscador

import tadp.transportes.Transporte

class Direccion(
  val descripcion: String = null,
  val zona: ZonaAbstract,
  var transportesCercanos: List[Transporte] = null)
