package comoViajo

import org.junit.Assert._
import org.junit.Test
import org.junit.Before
import tadp.buscador._
import tadp.transportes._
import tadp.dependencias.moduloExternoTransporte

class TestDeEjemplo {

  val busqueda = new Busqueda

  val ParqueChacabuco: ZonaTrait = new Zona()
  val Caballito: ZonaTrait = new Zona()
  val Barracas: ZonaTrait = new Zona()
  val Flores: ZonaTrait = new Zona()
  val Palermo: ZonaTrait = new Zona()
  val SanTelmo: ZonaTrait = new Zona()
  val Centro: ZonaTrait = new ZonaTrabajo()

  //Creacion direcciones
  val Puan1228: Direccion = new Direccion("Puan 1228", ParqueChacabuco)
  val Florida100: Direccion = new Direccion("Florida 100", Centro)
  val MontesDeOca700: Direccion = new Direccion("Montes de oca 700", Barracas)
  val Yerbal434: Direccion = new Direccion("Yerbal 434", Flores)
  val Araoz2424: Direccion = new Direccion("Araoz 2424", Palermo)
  val EscalabriniOrtiz1500: Direccion = new Direccion("Escalabrini ortiz 1500", Palermo)
  val Asamblea564: Direccion = new Direccion("Asamblea 564", ParqueChacabuco)
  val JoseMariaMoreno1500: Direccion = new Direccion("Jose Maria Moreno 1500 ", ParqueChacabuco)
  val Azara567: Direccion = new Direccion("Azara 567", Barracas)
  val Nazca596: Direccion = new Direccion("Nazca 596", Flores)
  val Florida900: Direccion = new Direccion("Florida 900", Centro)
  val Callao242: Direccion = new Direccion("Callao 242", Centro)
  val Piedras485: Direccion = new Direccion("Piedras 485", SanTelmo)

  //Creacion Estaciones
  val Estacion01SubteE = new Estacion(1, "e1", Puan1228)
  val Estacion02SubteE = new Estacion(2, "e2", Florida100)
  val Estacion03SubteE = new Estacion(3, "e3", MontesDeOca700)
  val Estacion04SubteE = new Estacion(4, "e4", Nazca596)

  val Estacion01Colectivo4 = new Estacion(1, "estacion 1", Piedras485);
  val Estacion02Colectivo4 = new Estacion(2, "estacion 2", Florida100);
  val Estacion03Colectivo4 = new Estacion(3, "estacion 3", Florida900);
  val Estacion04Colectivo4 = new Estacion(4, "estacion 4", Azara567);
  val Estacion05Colectivo4 = new Estacion(5, "estacion 5", Yerbal434);

  val Estacion01Tren = new Estacion(1, "e1", Puan1228)
  val Estacion02Tren = new Estacion(2, "e2", Nazca596)

  //Modulo Externo Mock
  val ModuloMock = new moduloExternoTransporte() { override def distanciaRecorrida(estacionInicio: Estacion, estacionFin: Estacion) = 3100 }

  //Creacion transportes
  val subteE = new Subte("E", List(Estacion01SubteE, Estacion02SubteE, Estacion03SubteE, Estacion04SubteE))
  val colectivo04 = new Colectivo("04", List(Estacion01Colectivo4, Estacion02Colectivo4, Estacion03Colectivo4, Estacion04Colectivo4, Estacion05Colectivo4))

  val tren = new Tren("Tren", List(Estacion01Tren, Estacion02Tren), List(new PrecioTren(5, 3), new PrecioTren(10, 6)))
  subteE.setModuloExterno(ModuloMock)
  colectivo04.setModuloExterno(ModuloMock)

  //Asignar transportes cercanos a direcciones
  Puan1228.transportesCercanos = List(subteE, tren)
  MontesDeOca700.transportesCercanos = List(subteE)
  Piedras485.transportesCercanos = List(colectivo04)
  Yerbal434.transportesCercanos = List(colectivo04)
  Florida100.transportesCercanos = List(colectivo04, subteE)
  Florida900.transportesCercanos = List(colectivo04)
  Nazca596.transportesCercanos = List(subteE, tren)

  @Test
  def viajoDePuanAMontesDeOcaYMeDaUnViajeConElE = {

    val viajes = busqueda.comoViajo(Puan1228, MontesDeOca700)

    assertEquals(1, viajes.length)
    assertEquals(1, viajes.head.tramos.length)
    assertEquals(subteE, viajes.head.tramos.head.transporte)

    //Chequeo que el costo sea 4,50
    assertEquals(4.50, viajes.head.costo, 0)

    //Chequeo que el viaje dure 4 minutos (recorre 2 estaciones E01->E02->E03)
    assertEquals(4, viajes.head.duracion, 0)
  }

  @Test
  def viajoDePuanAFlorida100YMeDaUnViajeConElEConTarjetaYendoAlTrabajoYMeAplicaElDescuento = {

    val viajes = busqueda.comoViajo(Puan1228, Florida100, Trabajo())

    assertEquals(1, viajes.length)
    assertEquals(1, viajes.head.tramos.length)
    assertEquals(subteE, viajes.head.tramos.head.transporte)

    //Chequeo que el costo sea 4,50 - 1.5 = 3
    assertEquals(3, viajes.head.costo, 0)

    //Chequeo que el viaje dure 2 minutos (recorre 1 estacion E01->E02)
    assertEquals(2, viajes.head.duracion, 0)
  }

  @Test
  def viajoDePuanANazcaYMeDaDosViajesConCriterioPorPrecio = {

    val viajesConCriterioPrecio = busqueda.comoViajo(Puan1228, Nazca596, SinDescuento(), PorPrecio())

    assertEquals(2, viajesConCriterioPrecio.length)

    //Chequeo que primero tenga al subte e (mas barato)
    assertEquals(subteE, viajesConCriterioPrecio.head.tramos.head.transporte)
    //Luego al tren
    assertEquals(tren, viajesConCriterioPrecio.tail.head.tramos.head.transporte)

    //Chequeo que el costo del viaje en subteE sea 4,50 
    assertEquals(4.5, viajesConCriterioPrecio.head.costo, 0)

    //Chequeo que el costo del viaje en tren sea 5
    assertEquals(5, viajesConCriterioPrecio.tail.head.costo, 0)

  }

  @Test
  def viajoDePuanANazcaYMeDaDosViajesConCriterioPorTiempo = {

    val viajesConCriterioTiempo = busqueda.comoViajo(Puan1228, Nazca596, SinDescuento(), PorTiempo())

    assertEquals(2, viajesConCriterioTiempo.length)

    //Chequeo que primero tenga al tren (mas rapido)
    assertEquals(tren, viajesConCriterioTiempo.head.tramos.head.transporte)
    //Luego al subte
    assertEquals(subteE, viajesConCriterioTiempo.tail.head.tramos.head.transporte)

    //Chequeo que la duracion sea 3 min (una estacion)
    assertEquals(3, viajesConCriterioTiempo.head.duracion, 0)

    //Chequeo que la duracion sea 6 min (tres estaciones)
    assertEquals(6, viajesConCriterioTiempo.tail.head.duracion, 0)

  }

  @Test
  def viajoDePiedrasAYerbalYMeDaUnViajeConElColectivo04 = {

    val viajes = busqueda.comoViajo(Piedras485, Yerbal434)

    assertEquals(1, viajes.length)
    assertEquals(1, viajes.head.tramos.length)
    assertEquals(colectivo04, viajes.head.tramos.head.transporte)

    //Chequeo que el costo sea 2,75
    assertEquals(2.75, viajes.head.costo, 0)

    //Chequeo que el viaje dure 12.4 minutos 
    assertEquals(12.4, viajes.head.duracion, 0)
  }

  @Test
  def viajoDePiedrasAYerbalYMeDaUnViajeConElColectivo04ConTarjetaTurismoYMeAplicaDescuento = {

    val viajes = busqueda.comoViajo(Piedras485, Yerbal434, Turismo(Flores))

    assertEquals(1, viajes.length)
    assertEquals(1, viajes.head.tramos.length)
    assertEquals(colectivo04, viajes.head.tramos.head.transporte)

    //Chequeo que el costo sea 2,75*0.9 = 2.475 (Aplica 10%)
    assertEquals(2.475, viajes.head.costo, 0)

    //Chequeo que el viaje dure 12.4 minutos 
    assertEquals(12.4, viajes.head.duracion, 0)
  }

  @Test
  def viajoDePiedrasAYerbalYMeDaUnViajeConElColectivo04ConTarjetaDiscapacitadosYMeAplicaDescuento = {

    val viajes = busqueda.comoViajo(Piedras485, Yerbal434, Discapacitados())

    assertEquals(1, viajes.length)
    assertEquals(1, viajes.head.tramos.length)
    assertEquals(colectivo04, viajes.head.tramos.head.transporte)

    //Chequeo que el costo sea 0 (viaje gratis)
    assertEquals(0, viajes.head.costo, 0)

    //Chequeo que el viaje dure 12.4 minutos 
    assertEquals(12.4, viajes.head.duracion, 0)
  }

  @Test
  def viajoDePuanAFlorida900YMeDaUnViajeConUnaCombinacionEntreEY04 = {

    val ModuloMock = new moduloExternoTransporte() {
      override def distanciaRecorrida(estacionInicio: Estacion, estacionFin: Estacion): Int = {
        return 100
      }
    }

    colectivo04.setModuloExterno(ModuloMock)
    subteE.setModuloExterno(ModuloMock)

    val viajes = busqueda.comoViajo(Puan1228, Florida900)

    assertEquals(1, viajes.length)
    assertEquals(2, viajes.head.tramos.length)
    assertTrue(viajes.head.tramos.map { (e) => e.transporte }.contains(colectivo04))
    assertTrue(viajes.head.tramos.map { (e) => e.transporte }.contains(subteE))

    //Chequeo que el costo sea 2,5 + 4.5 = 7
    assertEquals(7, viajes.head.costo, 0)

    //Chequeo que el viaje dure 4.9 minutos 
    assertEquals(4.9, viajes.head.duracion, 0)
  }

}