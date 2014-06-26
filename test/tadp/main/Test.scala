
package tadp.main

import tadp.transportes.Subte
import tadp.transportes.Tren
import tadp.transportes.Colectivo
import tadp.buscador.Busqueda
import tadp.buscador.PorTiempo
import tadp.buscador.Turismo
import tadp.transportes.Estacion
import tadp.transportes.PrecioTren
import tadp.buscador.Direccion
import tadp.buscador.ZonaTrabajo
import tadp.buscador.ZonaAbstract
import tadp.buscador.Zona
import tadp.dependencias.moduloExternoTransporte

object Test extends App {

  //Comentario: Nunca tengan un main en su aplicación dentro de los sources,
  //  cuanto mucho, si necesitan tener un main para probar algo en particular lo pueden meter
  //  en la carpeta test que no se va a deployar junto con el codigo de producción posta.

  /******** Test ********/

  val moduloExterno = new moduloExternoTransporte

  val LaBoca: ZonaAbstract = new Zona()
  val Centro: ZonaAbstract = new ZonaTrabajo()

  //Creacion direcciones
  val origen1: Direccion = new Direccion("origen1", LaBoca)
  val destino1: Direccion = new Direccion("destino1", Centro)
  val intermedia1: Direccion = new Direccion("intermedia1", Centro)

  //Creacion Estaciones
  val e1 = new Estacion(1, "e1", origen1)
  val e2 = new Estacion(10, "e2", destino1)
  val e3 = new Estacion(7, "e3", intermedia1)

  //Estaciones Trenes
  val eT1 = new Estacion(1, "Estacion A", origen1);
  val eT2 = new Estacion(2, "Estacion B", intermedia1);
  val eT3 = new Estacion(3, "Estacion C", destino1);

  //Creacion transportes

  val colectivo23 = new Colectivo("23", List(e1, e2, e3))

  val tren = new Tren("A", List(eT1, eT2, eT3), List(new PrecioTren(2, 1), new PrecioTren(4, 2)))

  val subteE = new Subte("E", List(e2, e3))

  val colectivo15 = new Colectivo("15", List(e1, e2))

  //Asignar transportes cercanos a direcciones
  origen1.transportesCercanos = List(colectivo23, colectivo15)
  destino1.transportesCercanos = List(colectivo23, subteE, colectivo15)
  intermedia1.transportesCercanos = List(colectivo23, subteE)

  //Get transportes cercanos de direcciones

  var transportesCercanosOrigen = moduloExterno.getTransportesCercanos(origen1)
  var transportesCercanosDestino = moduloExterno.getTransportesCercanos(destino1)

  val busqueda = new Busqueda

  var viajes = busqueda.comoViajo(origen1, destino1, Turismo(LaBoca), PorTiempo())

  for (v <- viajes) {
    println("Nuevo Viaje:" + v);
    for (t <- v.tramos) {
      println("Nuevo Tramo")
      println("Estacion Inicio: " + t.inicio.nombre + " Estacion fin: " + t.fin.nombre + " Linea: " + t.transporte.linea)
      println("Duracion del tramo:" + t.duracion + " minutos")
      println("Costo tramo:" + t.costo + " pesos")
    }
    println("Duracion total:" + v.duracion + " minutos")
    println("Costo total:" + v.costo + " pesos")
  }

}