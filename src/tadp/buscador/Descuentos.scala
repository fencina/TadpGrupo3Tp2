package tadp.buscador

trait Descuento { def aplicar(d:Double) : Double}

case class Turismo(val zona: ZonaTrait, val nombre: String = "Tarjeta Turismo") extends Descuento {
   def aplicar(d: Double) = d * 0.9
}

case class Discapacitados(val nombre: String = "Tarjeta Discapacitados") extends Descuento {
   def aplicar(d: Double) = 0
}

case class Trabajo(val nombre: String = "Tarjeta Trabajo") extends Descuento {
   def aplicar(d: Double) = d - 1.5
}

