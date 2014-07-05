package tadp.buscador

class Descuento {
  def aplicar(d: Double) = d
}

case class Turismo(val zona: ZonaTrait, val nombre: String = "Tarjeta Turismo") extends Descuento {
  override def aplicar(d: Double) = d * 0.9
}

case class Discapacitados(val nombre: String = "Tarjeta Discapacitados") extends Descuento {
  override def aplicar(d: Double) = 0
}

case class Trabajo(val nombre: String = "Tarjeta Trabajo") extends Descuento {
  override def aplicar(d: Double) = d - 1.5
}

case class SinDescuento() extends Descuento